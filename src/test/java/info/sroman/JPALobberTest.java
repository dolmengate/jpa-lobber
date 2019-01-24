package info.sroman;

import info.sroman.entity.XmlpRpt;
import info.sroman.entity.XmlpRptDtTmpl;
import info.sroman.entity.XmlpRptLyt;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class JPALobberTest extends JPAHibernateTestSetup {

    private JPALobber lobber = null;
    private Properties testProps = new Properties();

    private EntityManager srcEntityManager = null;
    private EntityManager destEntityManager = null;

    @Before
    public void configLobber_JPALobberTest() throws IOException {
        testProps.setProperty("lobber.tables", "XMLP_RPT_LYT,XMLP_RPT_DT_TMPL");
        String[] args = new String[2];
        args[0] = "--no-confirm";
        args[1] = "--transfer";
        lobber = new JPALobber(testProps, args);

        srcEntityManager = lobber.getSrcEntityManager();
        destEntityManager = lobber.getDestEntityManager();
    }

    @Before
    public void insertDataForTestSuite_JPALobberTest() {
        XmlpRptDtTmpl xmlpRptDtTmpl = new XmlpRptDtTmpl();
        xmlpRptDtTmpl.setIdDtTmplRpt(new BigDecimal(99));

        XmlpRptLyt xmlpRptLyt = new XmlpRptLyt();
        xmlpRptLyt.setIdLytRpt(new BigDecimal(99));

        XmlpRpt xmlpRpt = new XmlpRpt();
        xmlpRpt.setIdRptXmlp(new BigDecimal(99));
        xmlpRpt.setXmlpRptDtTmpl(xmlpRptDtTmpl);
        xmlpRpt.setXmlpRptLyt(xmlpRptLyt);

        srcEntityManager.getTransaction().begin();
        srcEntityManager.persist(xmlpRptDtTmpl);
        srcEntityManager.persist(xmlpRptLyt);
        srcEntityManager.persist(xmlpRpt);
    }

    @Test
    public void lobberInitialization_readCommandLineOptions_optionsCorrect() {
        assertEquals(lobber.isConfirmationEnabledSetting(), false);
        assertEquals(lobber.isRecordTransferEnabledSetting(), true);
    }

    @Test
    public void querySourceDB_selectAllFromTable_allRecordsReturned() {
        List records = lobber.selectAllForEntityClass(srcEntityManager, XmlpRpt.class);
        assertEquals(records.size(), 34);
    }

    @After
    public void rollbackTransactions_JPALobberTest() {
        lobber.getSrcEntityManager().getTransaction().rollback();
        lobber.getSrcEntityManager().close();
    }
}
