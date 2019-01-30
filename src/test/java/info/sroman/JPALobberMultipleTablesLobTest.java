package info.sroman;

import info.sroman.entity.XmlpRptCfgPrmt;
import info.sroman.entity.XmlpRptDtTmpl;
import info.sroman.entity.XmlpRptLyt;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class JPALobberMultipleTablesLobTest extends JPAHibernateTestSetup{

    @Test
    public void lobber_lobTwoTables_sameNumberOfRecordsInSourceAndDest() {
        addLobberConfig(
                "lobber.tables",
                Utils.tableClassesToString(XmlpRptLyt.class, XmlpRptDtTmpl.class));

        testLobber.beginTransactions();
        testLobber.lob();

        List lytSrc = testLobber.selectAllForEntityClass( testLobber.getSrcEntityManager(), XmlpRptLyt.class);
        List lytDest = testLobber.selectAllForEntityClass( testLobber.getDestEntityManager(), XmlpRptLyt.class);

        List tmplSrc = testLobber.selectAllForEntityClass( testLobber.getSrcEntityManager(), XmlpRptDtTmpl.class);
        List tmplDest = testLobber.selectAllForEntityClass( testLobber.getDestEntityManager(), XmlpRptDtTmpl.class);

        assertEquals(lytSrc.size(), lytDest.size());
        assertEquals(tmplSrc.size(), tmplDest.size());
    }

    @Test
    public void lobber_lobTwoTablesReversed_sameNumberOfRecordsInSourceAndDest() {
        addLobberConfig(
                "lobber.tables",
                Utils.tableClassesToString(XmlpRptDtTmpl.class, XmlpRptLyt.class));

        testLobber.beginTransactions();
        testLobber.lob();

        List lytSrc = testLobber.selectAllForEntityClass( testLobber.getSrcEntityManager(), XmlpRptLyt.class);
        List lytDest = testLobber.selectAllForEntityClass( testLobber.getDestEntityManager(), XmlpRptLyt.class);

        List tmplSrc = testLobber.selectAllForEntityClass( testLobber.getSrcEntityManager(), XmlpRptDtTmpl.class);
        List tmplDest = testLobber.selectAllForEntityClass( testLobber.getDestEntityManager(), XmlpRptDtTmpl.class);

        assertEquals(lytSrc.size(), lytDest.size());
        assertEquals(tmplSrc.size(), tmplDest.size());
    }

    @Test
    public void lobber_lobThreeTables_sameNumberOfRecordsInSourceAndDest() {
        addLobberConfig(
                "lobber.tables",
                Utils.tableClassesToString(XmlpRptLyt.class, XmlpRptDtTmpl.class, XmlpRptCfgPrmt.class));

        testLobber.beginTransactions();
        testLobber.lob();

        List lytSrc = testLobber.selectAllForEntityClass( testLobber.getSrcEntityManager(), XmlpRptLyt.class);
        List lytDest = testLobber.selectAllForEntityClass( testLobber.getDestEntityManager(), XmlpRptLyt.class);

        List tmplSrc = testLobber.selectAllForEntityClass( testLobber.getSrcEntityManager(), XmlpRptDtTmpl.class);
        List tmplDest = testLobber.selectAllForEntityClass( testLobber.getDestEntityManager(), XmlpRptDtTmpl.class);

        List cfgSrc = testLobber.selectAllForEntityClass( testLobber.getSrcEntityManager(), XmlpRptCfgPrmt.class);
        List cfgDest = testLobber.selectAllForEntityClass( testLobber.getDestEntityManager(), XmlpRptCfgPrmt.class);

        assertEquals(lytSrc.size(), lytDest.size());
        assertEquals(tmplSrc.size(), tmplDest.size());
        assertEquals(cfgSrc.size(), cfgDest.size());
    }

    @Test
    public void lobber_lobThreeTablesReversed_sameNumberOfRecordsInSourceAndDest() {
        addLobberConfig(
                "lobber.tables",
                Utils.tableClassesToString( XmlpRptCfgPrmt.class, XmlpRptDtTmpl.class, XmlpRptLyt.class));

        testLobber.beginTransactions();
        testLobber.lob();

        List lytSrc = testLobber.selectAllForEntityClass( testLobber.getSrcEntityManager(), XmlpRptLyt.class);
        List lytDest = testLobber.selectAllForEntityClass( testLobber.getDestEntityManager(), XmlpRptLyt.class);

        List tmplSrc = testLobber.selectAllForEntityClass( testLobber.getSrcEntityManager(), XmlpRptDtTmpl.class);
        List tmplDest = testLobber.selectAllForEntityClass( testLobber.getDestEntityManager(), XmlpRptDtTmpl.class);

        List cfgSrc = testLobber.selectAllForEntityClass( testLobber.getSrcEntityManager(), XmlpRptCfgPrmt.class);
        List cfgDest = testLobber.selectAllForEntityClass( testLobber.getDestEntityManager(), XmlpRptCfgPrmt.class);

        assertEquals(lytSrc.size(), lytDest.size());
        assertEquals(tmplSrc.size(), tmplDest.size());
        assertEquals(cfgSrc.size(), cfgDest.size());
    }

    @Test
    public void lobber_lobThreeRandomTables_sameNumberOfRecordsInSourceAndDest() {
        String tables = Utils.getUniqueRandomTableNamesString(3);
        addLobberConfig( "lobber.tables", tables);

        testLobber.beginTransactions();
        testLobber.lob();

        for (String table : tables.split(",")) {
            Class<?> clazz = Utils.findEntityClassForTableName(table);
            List src = testLobber.selectAllForEntityClass(testLobber.getSrcEntityManager(), clazz);
            List dest = testLobber.selectAllForEntityClass(testLobber.getDestEntityManager(), clazz);

            assertEquals(src.size(), dest.size());
        }
    }
}
