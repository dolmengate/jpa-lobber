package info.sroman;

import info.sroman.entity.XmlpRpt;
import info.sroman.entity.XmlpRptDtTmpl;
import info.sroman.entity.XmlpRptLyt;
import org.junit.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JPALobberSelectAllFromTableTest extends JPAHibernateTestSetup {

    @BeforeClass
    public static void insertDataForTestSuite_JPALobberTest() {
        XmlpRptDtTmpl xmlpRptDtTmpl = new XmlpRptDtTmpl();
        xmlpRptDtTmpl.setIdDtTmplRpt(new BigDecimal(99));

        XmlpRptLyt xmlpRptLyt = new XmlpRptLyt();
        xmlpRptLyt.setIdLytRpt(new BigDecimal(99));

        XmlpRpt xmlpRpt = new XmlpRpt();
        xmlpRpt.setIdRptXmlp(new BigDecimal(99));
        xmlpRpt.setXmlpRptDtTmpl(xmlpRptDtTmpl);
        xmlpRpt.setXmlpRptLyt(xmlpRptLyt);

        testLobber.getDestEntityManager().getTransaction().begin();

        testLobber.getDestEntityManager().persist(xmlpRptDtTmpl);
        testLobber.getDestEntityManager().persist(xmlpRptLyt);
        testLobber.getDestEntityManager().persist(xmlpRpt);
    }

    @Test
    public void querySourceDB_selectAllFromTable_allRecordsReturned() {
        List records = testLobber.selectAllForEntityClass( testLobber.getDestEntityManager(), XmlpRpt.class);
        assertEquals(records.size(), 1);
    }
}
