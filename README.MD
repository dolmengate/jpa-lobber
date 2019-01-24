To build:
 - create the ``./lib`` folder with your ojdbc7.jar
 - run ``mvn clean package assembly:single`` or ``build.ps1`` in PowerShell

To generate entity classes
 - todo

Testing:
    - Place the ORPOS 13.4 installer in ``src/main/test/resources/installer/``. Don't replace ``db.properties``, ``db-reports.properties``,
    ``db.xml``, or ``orig-db.properties``
    - Have 2 separate 11g db instances and running
    - Give the connection details to ``src/main/test/resources/META-INF/persistence.xml``
    - Supply passwords for test databases setup by running the following once
        - run ``
        ant -f .\db.xml encrypt-pos-passwords -lib C:/Users/stephen/repos/jpa-lobber/src/test/resources/installer/ORPOS-13.4.1/product/server/common/db/lib/oracle.stores.secure.utility.jar -lib C:/Users/stephen/repos/jpa-lobber/src/test/resources/installer/ORPOS-13.4.1/ant-ext/oracle.stores.encryptionclient.jar -lib C:/Users/stephen/repos/jpa-lobber/src/test/resources/installer/ORPOS-13.4.1/product/paypal/lib/paypal/log4j-1.2.12.jar -lib C:/Users/stephen/repos/jpa-lobber/src/test/resources/installer/ORPOS-13.4.1/product/shared/common/lib/oracle.stores.utility.jar -libC:/Users/stephen/repos/jpa-lobber/target/test-classes/installer/ORPOS-13.4.1/ant-ext/commons-codec-1.3.jar -lib C:/Users/stephen/Vagrant/boxes/winserv/lillii_installer/stage/ORPOS-13.4.1/ant-ext/ossa/retail-public-security-api.jar -lib C:/Users/stephen/Vagrant/boxes/winserv/lillii_installer/stage/ORPOS-13.4.1/product/paypal/lib/paypal/commons-logging-api-1.1.jar
        ``
        - replace the ``C:Users\...`` etc with your own directory structure
    - db.properties and db-reports.properties must each have the proper hashed passwords
    - populate src-test and src-dest DBs by:
        - putting correct JDBC connection strings for src-test in your db.properties and db-reports.properties files
        - uncomment JPAHibernateTestSetup.initializeDatabase
        - run ``mvn clean test``
        - database test data population log is test-database-init.log
        - after doing this once successfully for your source database, do it once more for your test database
        - recomment JPAHibernateTestSetup.initializeDatabase
        - you're ready to start writing tests
