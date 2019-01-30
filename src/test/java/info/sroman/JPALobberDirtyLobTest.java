package info.sroman;

import info.sroman.entity.XmlpRpt;
import info.sroman.entity.XmlpRptDtTmpl;
import info.sroman.entity.XmlpRptLyt;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JPALobberDirtyLobTest extends JPAHibernateTestSetup {

    @BeforeClass
    public static void configLobberForTest() {
    }

    @Test
    public void lobber_doLob_sameNumberOfSrcAdnDestRecords() {
        addLobberConfig("lobber.tables", Utils.findTableNameForEntityClass(XmlpRpt.class));

        XmlpRptDtTmpl xmlpRptDtTmpl = new XmlpRptDtTmpl();
        xmlpRptDtTmpl.setIdDtTmplRpt(new BigDecimal(1));

        XmlpRptLyt xmlpRptLyt = new XmlpRptLyt();
        xmlpRptLyt.setIdLytRpt(new BigDecimal(1));

        XmlpRpt xmlpRpt = new XmlpRpt();
        xmlpRpt.setIdRptXmlp(new BigDecimal(1));
        xmlpRpt.setXmlpRptDtTmpl(xmlpRptDtTmpl);
        xmlpRpt.setXmlpRptLyt(xmlpRptLyt);

        testLobber.getDestEntityManager().getTransaction().begin();

        testLobber.getDestEntityManager().persist(xmlpRptDtTmpl);
        testLobber.getDestEntityManager().persist(xmlpRptLyt);
        testLobber.getDestEntityManager().persist(xmlpRpt);

        testLobber.beginTransactions();
        testLobber.lob();

        List srcRecords = testLobber.selectAllForEntityClass( testLobber.getSrcEntityManager(), XmlpRpt.class);
        List destRecords = testLobber.selectAllForEntityClass( testLobber.getDestEntityManager(), XmlpRpt.class);

        assertEquals(srcRecords.size(), destRecords.size());
    }
}
