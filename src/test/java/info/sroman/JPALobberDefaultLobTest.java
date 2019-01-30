package info.sroman;

import info.sroman.entity.XmlpRpt;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class JPALobberDefaultLobTest extends JPAHibernateTestSetup{

    @Test
    public void lobber_lobOneRandomTable_sameNumberOfSrcAndDestRecords() {
        addLobberConfig("lobber.tables", Utils.getUniqueRandomTableNamesString(1));

        testLobber.beginTransactions();
        testLobber.lob();

        List srcRecords = testLobber.selectAllForEntityClass( testLobber.getSrcEntityManager(), XmlpRpt.class);
        List destRecords = testLobber.selectAllForEntityClass( testLobber.getDestEntityManager(), XmlpRpt.class);

        assertEquals(srcRecords.size(), destRecords.size());
    }
}
