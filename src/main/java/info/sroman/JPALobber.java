package info.sroman;


import info.sroman.entity.XmlpRpt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.jpa.HibernatePersistenceProvider;

import javax.persistence.*;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class JPALobber
{

    private static Logger _logger = LogManager.getLogger(JPALobber.class);

    private static PersistenceUnitInfo srcInfo = new PersistenceUnitInfo() {

        @Override
        public String getPersistenceUnitName() {
            return "src-db";
        }

        @Override
        public String getPersistenceProviderClassName() {
            return "org.hibernate.jpa.HibernatePersistenceProvider";
        }

        @Override
        public PersistenceUnitTransactionType getTransactionType() {
            return PersistenceUnitTransactionType.RESOURCE_LOCAL;
        }

        @Override
        public DataSource getJtaDataSource() {
            return null;
        }

        @Override
        public DataSource getNonJtaDataSource() {
            return null;
        }

        @Override
        public List<String> getMappingFileNames() {
            return Collections.emptyList();
        }

        @Override
        public List<URL> getJarFileUrls() {
            return null;
        }

        @Override
        public URL getPersistenceUnitRootUrl() {
            return null;
        }

        @Override
        public List<String> getManagedClassNames() {
            List<String> classes = new ArrayList<>();
            // todo get class names by reflection
            classes.add("info.sroman.entity.XmlpRpt");
            classes.add("info.sroman.entity.XmlpRptCfgPrmt");
            classes.add("info.sroman.entity.XmlpRptCfgPrmtI8");
            classes.add("info.sroman.entity.XmlpRptCfgPrmtI8Id");
            classes.add("info.sroman.entity.XmlpRptCfgPrmtId");
            classes.add("info.sroman.entity.XmlpRptDtTmpl");
            classes.add("info.sroman.entity.XmlpRptI8");
            classes.add("info.sroman.entity.XmlpRptI8Id");
            classes.add("info.sroman.entity.XmlpRptLyt");
            classes.add("info.sroman.entity.XmlpRptLytI8");
            classes.add("info.sroman.entity.XmlpRptLytI8Id");
            return classes;
        }

        @Override
        public boolean excludeUnlistedClasses() {
            return false;
        }

        @Override
        public SharedCacheMode getSharedCacheMode() {
            return null;
        }

        @Override
        public ValidationMode getValidationMode() {
            return null;
        }

        @Override
        public Properties getProperties() {
            return new Properties();
        }

        @Override
        public String getPersistenceXMLSchemaVersion() {
            return null;
        }

        @Override
        public ClassLoader getClassLoader() {
            return null;
        }

        @Override
        public void addTransformer(ClassTransformer classTransformer) {

        }

        @Override
        public ClassLoader getNewTempClassLoader() {
            return null;
        }
    };

    private static PersistenceUnitInfo destInfo = new PersistenceUnitInfo() {
        @Override
        public String getPersistenceUnitName() {
            return "dest-db";
        }

        @Override
        public String getPersistenceProviderClassName() {
            return "org.hibernate.jpa.HibernatePersistenceProvider";
        }

        @Override
        public PersistenceUnitTransactionType getTransactionType() {
            return PersistenceUnitTransactionType.RESOURCE_LOCAL;
        }

        @Override
        public DataSource getJtaDataSource() {
            return null;
        }

        @Override
        public DataSource getNonJtaDataSource() {
            return null;
        }

        @Override
        public List<String> getMappingFileNames() {
            return Collections.emptyList();
        }

        @Override
        public List<URL> getJarFileUrls() {
            return null;
        }

        @Override
        public URL getPersistenceUnitRootUrl() {
            return null;
        }

        @Override
        public List<String> getManagedClassNames() {
            List<String> classes = new ArrayList<>();
            // todo get class names by reflection
            classes.add("info.sroman.entity.XmlpRpt");
            classes.add("info.sroman.entity.XmlpRptCfgPrmt");
            classes.add("info.sroman.entity.XmlpRptCfgPrmtI8");
            classes.add("info.sroman.entity.XmlpRptCfgPrmtI8Id");
            classes.add("info.sroman.entity.XmlpRptCfgPrmtId");
            classes.add("info.sroman.entity.XmlpRptDtTmpl");
            classes.add("info.sroman.entity.XmlpRptI8");
            classes.add("info.sroman.entity.XmlpRptI8Id");
            classes.add("info.sroman.entity.XmlpRptLyt");
            classes.add("info.sroman.entity.XmlpRptLytI8");
            classes.add("info.sroman.entity.XmlpRptLytI8Id");
            return classes;
        }

        @Override
        public boolean excludeUnlistedClasses() {
            return false;
        }

        @Override
        public SharedCacheMode getSharedCacheMode() {
            return null;
        }

        @Override
        public ValidationMode getValidationMode() {
            return null;
        }

        @Override
        public Properties getProperties() {
            return new Properties();
        }

        @Override
        public String getPersistenceXMLSchemaVersion() {
            return null;
        }

        @Override
        public ClassLoader getClassLoader() {
            return null;
        }

        @Override
        public void addTransformer(ClassTransformer classTransformer) {

        }

        @Override
        public ClassLoader getNewTempClassLoader() {
            return null;
        }
    };

    private static Properties properties = new Properties();
    private static EntityManagerFactory srcSessionFactory;
    private static EntityManagerFactory destSessionFactory;
    static {
        try {
            properties.load(new FileInputStream(new File("./base-hibernate.properties")));
            srcSessionFactory = configSourcePersistenceUnit();
            destSessionFactory = configDestPersistenceUnit();
        } catch (IOException e) {
            _logger.error(e);
        }
    }

    public static void main( String[] args )
    {
        EntityManager srcEntityManager = srcSessionFactory.createEntityManager();
        EntityManager destEntityManager = destSessionFactory.createEntityManager();

        srcEntityManager.getTransaction().begin();
        destEntityManager.getTransaction().begin();

        List<XmlpRpt> srcReports = srcEntityManager.createQuery("select x from XmlpRpt x", XmlpRpt.class).getResultList();
        List<XmlpRpt> destReports = destEntityManager.createQuery("select x from XmlpRpt x", XmlpRpt.class).getResultList();

        _logger.info("----- SOURCE DB REPORTS --------");
        for (XmlpRpt rpt : srcReports) {
//            destEntityManager.persist(rpt);
            _logger.info(rpt.toString());
        }

        _logger.info("----- DEST DB REPORTS --------");
        for (XmlpRpt rpt : destReports) {
            _logger.info(rpt.toString());
        }

//        destEntityManager.getTransaction().commit();
        destEntityManager.close();
        srcEntityManager.close();

        System.exit(0);
    }

    private static EntityManagerFactory configSourcePersistenceUnit() throws IOException {
        return new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(srcInfo, overlayProperties(properties, "./src-db.properties"));
    }

    private static EntityManagerFactory configDestPersistenceUnit() throws IOException {
        return new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(destInfo, overlayProperties(properties, "./dest-db.properties"));
    }

    private static Properties overlayProperties(Properties properties, String... files) throws IOException {
        Properties props = new Properties();
        props.putAll(properties);
        for (String f : files) {
            properties.load(new FileInputStream(new File(f)));
            props.putAll(properties);
        }
        return props;
    }
}

