package info.sroman;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Table;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

// todo add Spring? -- it has good integration with Hibernate for cool stuff like this
// https://stackoverflow.com/questions/11257598/how-to-scan-packages-for-hibernate-entities-instead-of-using-hbm-xml
// https://stackoverflow.com/questions/1413190/hibernate-mapping-package

public class JPALobber
{

//    private static Logger _logger = LogManager.getLogger();

    private static String SOURCE_PROPERTIES_PATH = "./src-db.properties";
    private static Properties SOURCE_PROPERTIES = new Properties();
    private static boolean testMode = true;

    private static EntityManager srcEntityManager;
    private static EntityManager destEntityManager;

    static {
        try {
            SOURCE_PROPERTIES.load(new FileInputStream(new File((SOURCE_PROPERTIES_PATH))));
            EntityManagerFactory srcSessionFactory = configSourcePersistenceUnit();
            EntityManagerFactory destSessionFactory = configDestPersistenceUnit();
            srcEntityManager = srcSessionFactory.createEntityManager();
            destEntityManager = destSessionFactory.createEntityManager();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main( String[] args )
    {
        if (args.length > 0)
            if (args[0].equals("copy"))
                testMode = false;

        srcEntityManager.getTransaction().begin();
        destEntityManager.getTransaction().begin();

        Scanner in = new Scanner(System.in);

        // todo add confirmation by stdin
        for (String table : SOURCE_PROPERTIES.getProperty("lobber.tables").split(",")) {
            try {
                Class<?> entityClazz = Class.forName("info.sroman.entity." + findEntityClassNameForTableName(table));
                List srcRecords = selectAllFromTable(srcEntityManager, entityClazz);
                List destRecords = selectAllFromTable(destEntityManager, entityClazz);

                // todo this just pulls and prints the table contents - need to add pull from src persist to dest
                printTableRecords(entityClazz.getSimpleName(), srcRecords, "source");
                printTableRecords(entityClazz.getSimpleName(), destRecords, "destination");

                if (!testMode) {
                    System.out.println("Test mode not detected, copy the above records from the Source DB to Destination DB? (y/N)");
                    System.out.println( in.next() );
                    System.exit(0);
                    for (Object r : srcRecords) {
                        persistRecordToDestDB(r);
                    }
                }
            } catch (ClassNotFoundException e) {
                System.err.println("Error occurred while attempting to find class for table name " + table
                        + ". Ensure you entered valid tables names in properties file.");
                System.err.println("This transaction will not be committed.");
                srcEntityManager.getTransaction().rollback();
                destEntityManager.getTransaction().rollback();
            }
        }

        destEntityManager.getTransaction().commit();
        destEntityManager.close();
        srcEntityManager.close();

        System.exit(0);
    }

    private static String findEntityClassNameForTableName(String tableName) {
        Reflections reflections = new Reflections("info.sroman.entity");
        Set<Class<?>> clazzes = reflections.getTypesAnnotatedWith(Table.class);
        for (Class<?> c : clazzes) {
            String tName = c.getAnnotation(Table.class).name();
            if (tName.equals(tableName)) {
                return c.getSimpleName();
            }
        }
        return null;
    }

    private static void printTableRecords(String tableName, List records, String targetOrSource) {
        System.out.println("------ " + targetOrSource + " CONTENTS FOR TABLE " + tableName + " ------");
        for (Object rpt : records) {
            System.out.println(rpt.toString());
        }
    }

    private static <T> List<T> selectAllFromTable(EntityManager entityManager, Class<T> clazz) {
        return entityManager.createQuery("select x from " + clazz.getSimpleName() + " x", clazz).getResultList();
    }

    private static void persistRecordToDestDB(Object record) {
        destEntityManager.persist(record);
    }

    private static EntityManagerFactory configSourcePersistenceUnit() {
        return Persistence.createEntityManagerFactory("src-db");
    }

    private static EntityManagerFactory configDestPersistenceUnit() {
        return Persistence.createEntityManagerFactory("dest-db");
    }
}
