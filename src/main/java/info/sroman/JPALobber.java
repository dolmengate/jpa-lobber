package info.sroman;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
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

    private final String SOURCE_PROPERTIES_PATH = "./src-db.properties";
    private Properties runOptionsProperties = new Properties();

    // for the purposes of executing the runnable jar as a "dry run" to test for connectivity, etc.
    private boolean dryRunEnabledSetting = true;
    private boolean confirmationEnabledSetting = true;

    public static EntityManagerFactory srcSessionFactory;
    public static EntityManagerFactory destSessionFactory;

    // these are reused in testing since EntityManagerFactorys are so resource intensive to create
    private EntityManager srcEntityManager;
    private EntityManager destEntityManager;

    /**
     * Constructor for normal runnable jar operation within Application.
     */
    JPALobber(String[] args) throws IOException {
        configurePersistence("src-db", "dest-db");
        configureCommandLineOpts(args);
    }

    /**
     * Constructor for testing only.
     */
    JPALobber(Properties testProperties, String[] args) throws IllegalArgumentException, IOException {
        runOptionsProperties = testProperties;
        configurePersistence("test-src", "test-dest");
        configureCommandLineOpts(args);
    }

    public void lob() {
        // todo add confirmation by stdin
        for (String table : runOptionsProperties.getProperty("lobber.tables").split(",")) {
            try {
                Class<?> entityClazz = Class.forName("info.sroman.entity." + findEntityClassNameForTableName(table));

                List srcRecords = selectAllForEntityClass(srcEntityManager, entityClazz);
                // evict all entities from persistence context to prevent:
                // HibernateException: Illegal attempt to associate a collection with two open sessions.
                srcEntityManager.clear();

                List destRecords = selectAllForEntityClass(destEntityManager, entityClazz);
                destEntityManager.clear();

                printTableRecords(entityClazz.getSimpleName(), srcRecords, "source");
                printTableRecords(entityClazz.getSimpleName(), destRecords, "destination");

                if (!dryRunEnabledSetting) {
                    if (confirmationEnabledSetting) {
                        Scanner in = new Scanner(System.in);
                        System.out.println("Test mode not detected, copy the above records from the Source DB to Destination DB? (y/N)");
                        // todo
                        System.out.println(in.next());
                        System.exit(0);
                    }
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
    }

    private String findEntityClassNameForTableName(String tableName) {
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

    private void printTableRecords(String tableName, List records, String sourceOrDest) {
        System.out.println("------ " + sourceOrDest + " CONTENTS FOR TABLE " + tableName + " ------");
        for (Object rpt : records) {
            System.out.println(rpt.toString());
        }
    }

    public <T> List<T> selectAllForEntityClass(EntityManager entityManager, Class<T> clazz) {
        return entityManager.createQuery("select x from " + clazz.getSimpleName() + " x", clazz).getResultList();
    }

    public void persistRecordToDestDB(Object record) {
        System.out.println("Persisting record to destination DB " + record);
        Session destSession = destEntityManager.unwrap(Session.class);
        destSession.replicate(record, ReplicationMode.IGNORE);
    }

    public void configureCommandLineOpts(String[] args) throws IllegalArgumentException {
        if (args == null) throw new IllegalArgumentException("Command line arguments not supplied.");
        if (args.length == 0) return;
        for (String arg : args) {
            switch(arg) {
                case "--transfer":
                    dryRunEnabledSetting = false;
                    break;
                case "--no-confirm":
                    confirmationEnabledSetting = false;
                    break;
            }
        }
    }

    private void configurePersistence(String srcPersistenceUnitName, String destPersistenceUnitName) throws IOException {
        System.out.println("Configuring persistence for units: [" + srcPersistenceUnitName + ", " + destPersistenceUnitName + "]");
        runOptionsProperties.load(new FileInputStream(new File((SOURCE_PROPERTIES_PATH))));
        srcSessionFactory = Persistence.createEntityManagerFactory(srcPersistenceUnitName);
        destSessionFactory = Persistence.createEntityManagerFactory(destPersistenceUnitName);
        srcEntityManager = srcSessionFactory.createEntityManager();
        destEntityManager = destSessionFactory.createEntityManager();
    }

    public void beginTransactions() {
        srcEntityManager.getTransaction().begin();
        destEntityManager.getTransaction().begin();
    }

    /**
     * Don't use this for testing
     */
    public void commitTransactionAndClose() {
        destEntityManager.getTransaction().commit();
        destEntityManager.close();
        srcEntityManager.close();
    }

    boolean isDryRunEnabledSetting() {
        return dryRunEnabledSetting;
    }

    boolean isConfirmationEnabledSetting() {
        return confirmationEnabledSetting;
    }

    public EntityManager getSrcEntityManager() {
        return srcEntityManager;
    }

    public EntityManager getDestEntityManager() {
        return destEntityManager;
    }

    public void setRunOptionsProperties(Properties runOptionsProperties) {
        this.runOptionsProperties = runOptionsProperties;
    }
}
