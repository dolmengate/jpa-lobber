package info.sroman;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class JPAHibernateTest {

    private static EntityManagerFactory srcEntityManagerFactory;
    private static EntityManagerFactory destEntityManagerFactory;
    protected static EntityManager srcEntityManager;
    protected static EntityManager destEntityManager;
    protected static volatile int exitCode;

    @BeforeClass
    public static void init() {
        srcEntityManagerFactory = Persistence.createEntityManagerFactory("test-src");
        srcEntityManager = srcEntityManagerFactory.createEntityManager();
        destEntityManagerFactory = Persistence.createEntityManagerFactory("test-dest");
        destEntityManager = srcEntityManagerFactory.createEntityManager();
    }

    @Before
    public void initializeDatabase() throws IOException, InterruptedException {
        // run ant targets to init DB
        File log = new File("test-database-init.log");
        if (log.exists()) log.delete();
        exitCode = doProcessAndWait(new ProcessBuilder(
                new File("src/test/resources/installer/ORPOS-13.4.1/ant/bin").getAbsolutePath() + "\\ant.bat",
                "-f",
                "./product/server/bin/db.xml",
                "create_minimum_db",
                "-d",
                "-lib",
                "C:/Users/stephen/repos/jpa-lobber/src/test/resources/installer/ORPOS-13.4.1/product/server/common/db/lib/retail-public-security-api.jar",
                "-lib",
                "C:/Users/stephen/repos/jpa-lobber/target/test-classes/installer/ORPOS-13.4.1/ant-ext/commons-logging-1.1.1.jar",
                "-lib",
                "C:/Users/stephen/repos/jpa-lobber/target/test-classes/installer/ORPOS-13.4.1/ant-ext/commons-codec-1.3.jar"
                ), log);
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

    private static int doProcessAndWait(ProcessBuilder processBuilder, File logFile) throws IOException, InterruptedException {
        System.out.println(Arrays.toString(processBuilder.command().toArray()));
        processBuilder.directory(new File("src/test/resources/installer/ORPOS-13.4.1/"));
        processBuilder.redirectErrorStream(true);
        processBuilder.redirectOutput(ProcessBuilder.Redirect.appendTo(logFile));
        return processBuilder.start().waitFor();
    }
}
