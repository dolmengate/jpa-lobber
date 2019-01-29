package info.sroman;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class JPAHibernateTestSetup {

    private static volatile int exitCode;
    private static Properties dbProperties = new Properties();
    private static Properties dbReportsProperties = new Properties();
    private static final String HERE = new File(".").getAbsolutePath();

    protected static JPALobber testLobber = null;

    // todo use reflection in place of getters used for thest to access private JPALobber members

    private static boolean setupComplete = false;

    static {
        if (!setupComplete) {
            try {
                lobberInitialization_setupInitialLobberConfig_successfullyConstructs();
                setupComplete = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void lobberInitialization_setupInitialLobberConfig_successfullyConstructs() throws IOException {
        String[] initArgs = {"--no-confirm", "--transfer"};
        testLobber = new JPALobber(new Properties(), initArgs);
    }

    @BeforeClass
    public static void lobberInitialization_setupInitialLobberConfig_optionsCorrect() {
        assertEquals(testLobber.isConfirmationEnabledSetting(), false);
        assertEquals(testLobber.isDryRunEnabledSetting(), false);
    }

    @After
    public void rollbackTransactions_JPALobberTest() {

        if (testLobber.getDestEntityManager().getTransaction().isActive())
            testLobber.getDestEntityManager().getTransaction().rollback();

        if (testLobber.getSrcEntityManager().getTransaction().isActive())
            testLobber.getSrcEntityManager().getTransaction().rollback();
    }

    /**
     * Once this method has successfully executed for both src-test and dest-test DBs you can disable it
     * by commenting it out. The '@Ignore' annotation seems not to work.
     * @throws IOException
     * @throws InterruptedException
     */
//    @BeforeClass
//    public void initializeDatabase() throws IOException, InterruptedException {
//        dbProperties.load(new FileInputStream(new File("src/test/resources/installer/ORPOS-13.4.1/product/server/bin/db.properties")));
//        dbReportsProperties.load(new FileInputStream(new File("src/test/resources/installer/ORPOS-13.4.1/product/server/bin/db-reports.properties")));
//
//        // todo programmatically edit dbProperties file to point to src/dest test DBs and run then this twice
//        // todo redirect subprocess input output to remove the need for testing developer to run the 'encrypt-pos-passwords' Ant task
//        // the database being populated here is the one described by the JDBC url within db.properties and db-reports.properties
//
//        File srcLog = new File("test-database-init.log");
//        if (srcLog.exists()) srcLog.delete();
//
//        // run ant targets to init DB
//        // fixme hardcoded paths and ugly syntax, make me a method
//        exitCode = doProcessAndWait(new ProcessBuilder(
//            new File(
//            "src/test/resources/installer/ORPOS-13.4.1/ant/bin").getAbsolutePath() + "\\ant.bat",
//            "-f",
//            "./product/server/bin/db.xml",
//            "create_sample_db",
//            "-d",
//            "-lib",
//            HERE + "/src/test/resources/installer/ORPOS-13.4.1/product/server/common/db/lib/retail-public-security-api.jar",
//            "-lib",
//            HERE + "/src/test/resources/installer/ORPOS-13.4.1/ant-ext/commons-logging-1.1.1.jar",
//            "-lib",
//            HERE + "/src/test/resources/installer/ORPOS-13.4.1/ant-ext/commons-codec-1.3.jar"
//            ),
//            srcLog,
//            "Populating test database at ???? ",
//            "src/test/resources/installer/ORPOS-13.4.1/"
//            );
//        System.out.println("Exit code: " + exitCode);
//    }

    private static int doProcessAndWait(ProcessBuilder processBuilder, File logFile, String logMsg, String runAtPath) throws IOException, InterruptedException {
        System.out.println(logMsg);
        System.out.println(Arrays.toString(processBuilder.command().toArray()));
        processBuilder.directory(new File(runAtPath));
        processBuilder.redirectErrorStream(true);
        processBuilder.redirectOutput(ProcessBuilder.Redirect.appendTo(logFile));
        return processBuilder.start().waitFor();
    }
}
