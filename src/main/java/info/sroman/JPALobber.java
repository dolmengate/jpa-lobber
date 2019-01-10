package info.sroman;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Table;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;

// todo add Spring? -- it has good integration with Hibernate for cool stuff like this
// https://stackoverflow.com/questions/11257598/how-to-scan-packages-for-hibernate-entities-instead-of-using-hbm-xml
// https://stackoverflow.com/questions/1413190/hibernate-mapping-package

public class JPALobber
{

    private static Logger _logger = LogManager.getLogger();

    private static String BASE_PROPERTIES_PATH = "./base-hibernate.properties";
    private static String SOURCE_PROPERTIES_PATH = "./src-db.properties";
    private static String DESTINATION_PROPERTIES_PATH = "./dest-db.properties";
//    private static Properties BASE_PROPERTIES = new Properties();
    private static Properties SOURCE_PROPERTIES = new Properties();
//    private static Properties DESTINATION_PROPERTIES;

    private static EntityManagerFactory srcSessionFactory;
    private static EntityManagerFactory destSessionFactory;
    static {
        try {
//            BASE_PROPERTIES.load(new FileInputStream(new File(BASE_PROPERTIES_PATH)));
//            SOURCE_PROPERTIES  = overlayProperties(BASE_PROPERTIES_PATH, SOURCE_PROPERTIES_PATH);
            SOURCE_PROPERTIES.load(new FileInputStream(new File((SOURCE_PROPERTIES_PATH))));
//            DESTINATION_PROPERTIES = overlayProperties(BASE_PROPERTIES_PATH, DESTINATION_PROPERTIES_PATH);
            srcSessionFactory = configSourcePersistenceUnit();
            destSessionFactory = configDestPersistenceUnit();
        } catch (IOException e) {
            _logger.error(e);
        }
    }

    public static void main( String[] args )
    {
        EntityManager srcEntityManager = srcSessionFactory.createEntityManager();
        EntityManager destEntityManager = destSessionFactory.createEntityManager();

        srcEntityManager.getTransaction().begin();
        destEntityManager.getTransaction().begin();

        String[] tables = SOURCE_PROPERTIES.getProperty("lobber.tables").split(",");

        for (String table : tables) {
            try {
                Class<?> entityClazz = Class.forName("info.sroman.entity." + findEntityClassNameForTableName(table));
                List srcRecords = selectAllFromTable(srcEntityManager, entityClazz);
                List destRecords = selectAllFromTable(destEntityManager, entityClazz);

                // todo this just pulls and prints the table contents - need to add pull from src persist to dest

                _logger.info("------ DESTINATION CONTENTS FOR TABLE " + table + " ------");
                for (Object rpt : destRecords) {
                    _logger.info(rpt.toString());
                }

                _logger.info("------ SOURCE CONTENTS FOR TABLE " + table + " ------");
                for (Object rpt : srcRecords) {
                    _logger.info(rpt.toString());
//                        destEntityManager.persist(rpt);
//                    destEntityManager.getTransaction().commit();
                }

                // todo add confirmation by stdin -- add console window (ProcessBuilder??)

            } catch (ClassNotFoundException e) {
                _logger.error("Error occurred while attempting to find class for table name " + table
                        + ". Ensure you entered valid tables names in properties file", e);
                srcEntityManager.getTransaction().rollback();
                destEntityManager.getTransaction().rollback();
            }
        }

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

    private static <T> List<T> selectAllFromTable(EntityManager entityManager, Class<T> clazz) {
        return entityManager.createQuery("select x from " + clazz.getSimpleName() + " x", clazz).getResultList();
    }

    private static EntityManagerFactory configSourcePersistenceUnit() {
        return Persistence.createEntityManagerFactory("src-db");
    }

    private static EntityManagerFactory configDestPersistenceUnit() {
        return Persistence.createEntityManagerFactory("dest-db");
    }
}
