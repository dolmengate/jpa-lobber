package info.sroman;

import info.sroman.entity.XmlpRptLyt;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class JPALobberDefaultLobTest extends JPAHibernateTestSetup{

    @BeforeClass
    public static void configLobberForTest() {
        testProps.clear();
        testProps.setProperty("lobber.tables", "XMLP_RPT");
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
