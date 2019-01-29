package info.sroman;

import info.sroman.entity.XmlpRptLyt;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class JPALobberDefaultLobTest extends JPAHibernateTestSetup{

    private static Properties testProps = new Properties();

    @BeforeClass
    public static void configLobber_JPALobberTest() {
        testProps.setProperty("lobber.tables", "XMLP_RPT_LYT");
        testLobber.setRunOptionsProperties(testProps);
    }

    @Test
    public void lobber_doLob_sameNumberOfSrcAndDestRecords() {
        testLobber.beginTransactions();
        testLobber.lob();

        List srcRecords = testLobber.selectAllForEntityClass( testLobber.getSrcEntityManager(), XmlpRptLyt.class);
        List destRecords = testLobber.selectAllForEntityClass( testLobber.getDestEntityManager(), XmlpRptLyt.class);

        assertEquals(srcRecords.size(), destRecords.size());
    }
}
