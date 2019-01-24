package info.sroman;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class JPAHibernateTest {

    private static EntityManagerFactory srcEntityManagerFactory;
    private static EntityManagerFactory destEntityManagerFactory;
    protected static EntityManager srcEntityManager;
    protected static EntityManager destEntityManager;
    private static volatile int exitCode;
    private static Properties dbProperties = new Properties();
    private static Properties dbReportsProperties = new Properties();
    private static final String HERE = new File(".").getAbsolutePath();


    @BeforeClass
    public static void initHibernate() {
        srcEntityManagerFactory = Persistence.createEntityManagerFactory("test-src");
        srcEntityManager = srcEntityManagerFactory.createEntityManager();
        destEntityManagerFactory = Persistence.createEntityManagerFactory("test-dest");
        destEntityManager = srcEntityManagerFactory.createEntityManager();
    }

    @Before
    public void initializeDatabase() throws IOException, InterruptedException {
        dbProperties.load(new FileInputStream(new File("src/test/resources/installer/ORPOS-13.4.1/product/server/bin/db.properties")));
        dbReportsProperties.load(new FileInputStream(new File("src/test/resources/installer/ORPOS-13.4.1/product/server/bin/db-reports.properties")));

        // todo edit dbProperties file to populate test-dest DB

        File log = new File("test-database-init.log");
        if (log.exists()) log.delete();

        // run ant targets to init DB
        // fixme hardcoded paths
        exitCode = doProcessAndWait(new ProcessBuilder(
                new File("src/test/resources/installer/ORPOS-13.4.1/ant/bin").getAbsolutePath() + "\\ant.bat",
                "-f",
                "./product/server/bin/db.xml",
                "create_sample_db",
                "-d",
                "-lib",
                HERE + "/src/test/resources/installer/ORPOS-13.4.1/product/server/common/db/lib/retail-public-security-api.jar",
                "-lib",
                HERE + "/src/test/resources/installer/ORPOS-13.4.1/ant-ext/commons-logging-1.1.1.jar",
                "-lib",
                HERE + "/src/test/resources/installer/ORPOS-13.4.1/ant-ext/commons-codec-1.3.jar"
                ),
                log,
                "Populating test SOURCE database at: " +
                        srcEntityManagerFactory.getProperties().get("hibernate.ejb.persistenceUnitName").toString());

        System.out.println("Exit code: " + exitCode);
        assert exitCode == 0;
    }

    @AfterClass
    public static void tearDown(){
        srcEntityManager.clear();
        srcEntityManager.close();
        destEntityManager.clear();
        destEntityManager.close();
        srcEntityManagerFactory.close();
        destEntityManagerFactory.close();
    }

    private static int doProcessAndWait(ProcessBuilder processBuilder, File logFile, String logMsg) throws IOException, InterruptedException {
        System.out.println(logMsg);
        System.out.println(Arrays.toString(processBuilder.command().toArray()));
        processBuilder.directory(new File("src/test/resources/installer/ORPOS-13.4.1/"));
        processBuilder.redirectErrorStream(true);
        processBuilder.redirectOutput(ProcessBuilder.Redirect.appendTo(logFile));
        return processBuilder.start().waitFor();
    }
}
