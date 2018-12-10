package info.sroman;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.reflections.Reflections;

import javax.persistence.*;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class JPALobber
{

    private static Logger _logger = LogManager.getLogger();

    private static String BASE_PROPERTIES_PATH = "./base-hibernate.properties";
    private static String SOURCE_PROPERTIES_PATH = "./src-db.properties";
    private static String DESTINATION_PROPERTIES_PATH = "./dest-db.properties";
    private static Properties BASE_PROPERTIES = new Properties();
    private static Properties SOURCE_PROPERTIES;
    private static Properties DESTINATION_PROPERTIES;

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

    private static EntityManagerFactory srcSessionFactory;
    private static EntityManagerFactory destSessionFactory;
    static {
        try {
            BASE_PROPERTIES.load(new FileInputStream(new File(BASE_PROPERTIES_PATH)));
            SOURCE_PROPERTIES  = overlayProperties(BASE_PROPERTIES_PATH, SOURCE_PROPERTIES_PATH);
            DESTINATION_PROPERTIES = overlayProperties(BASE_PROPERTIES_PATH, DESTINATION_PROPERTIES_PATH);
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

        String[] tables = SOURCE_PROPERTIES.getProperty("lobber.tables").split(",");

        for (String table : tables) {
            try {
                Class<?> entityClazz = Class.forName(findEntityClassNameForTableName(table));
                List srcRecords = selectAllFromTable(srcEntityManager, entityClazz);
                List destRecords = selectAllFromTable(destEntityManager, entityClazz);

                // todo this just pulls and prints the table contents - need to add pull from src persist to dest

                _logger.info("------ DESTINATION CONTENTS FOR TABLE " + table + " ------");
                for (Object rpt : destRecords) {
                    _logger.info(rpt.toString());
                }

                _logger.info("------ SOURCE CONTENTS FOR TABLE " + table + " ------");
                for (Object rpt : srcRecords) {
                    _logger.info(rpt.toString());
//                        destEntityManager.persist(rpt);
//                    destEntityManager.getTransaction().commit();
                }

            } catch (ClassNotFoundException e) {
                _logger.error("Error occurred while attempting to find class for table name " + table
                        + ". Ensure you entered valid tables names in properties file", e);
                srcEntityManager.getTransaction().rollback();
                destEntityManager.getTransaction().rollback();
            }
        }

        destEntityManager.close();
        srcEntityManager.close();

        System.exit(0);
    }

    private static String findEntityClassNameForTableName(String tableName) {
        Reflections reflections = new Reflections("info.sroman.entity");
        Set<Class<?>> clazzes = reflections.getTypesAnnotatedWith(javax.persistence.Table.class);
        return clazzes.stream()
                .filter( c -> c.getAnnotationsByType(javax.persistence.Table.class)[0].name().equals(tableName))
                .collect(Collectors.toSet()).iterator().next().getName();
    }

    private static <T> List<T> selectAllFromTable(EntityManager entityManager, Class<T> clazz) {
        return entityManager.createQuery("select x from " + clazz.getSimpleName() + " x", clazz).getResultList();
    }

    private static EntityManagerFactory configSourcePersistenceUnit() throws IOException {
        return new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(srcInfo,
                        overlayProperties(BASE_PROPERTIES_PATH, SOURCE_PROPERTIES_PATH));
    }

    private static EntityManagerFactory configDestPersistenceUnit() throws IOException {
        return new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(destInfo,
                        overlayProperties(BASE_PROPERTIES_PATH, DESTINATION_PROPERTIES_PATH));
    }

    private static Properties overlayProperties(String baseFile, String... propFiles) throws IOException {
        Properties baseProps = new Properties();
        baseProps.load(new FileInputStream(new File(baseFile)));
        _logger.info("Applying property overlay. Base properties at " + baseFile + " follow: ");
        for (String bp : baseProps.stringPropertyNames())
            _logger.info(bp + ": " + baseProps.getProperty(bp));

        Properties props = new Properties();
        for (String f : propFiles) {
            props.load(new FileInputStream(new File(f)));
            _logger.info("Overlay of base properties " + baseFile + " with properties for file " + f +
                    ". Overlay properties follow: " );
            for (String p : props.stringPropertyNames())
                _logger.info( p + ": " + props.getProperty(p));
            baseProps.putAll(props);
        }
        return baseProps;
    }
}
