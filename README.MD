To build:
 - create the ``./lib`` folder with your ojdbc7.jar
 - run ``mvn clean package assembly:single`` or ``build.ps1`` in PowerShell

To generate entity classes
 - todo

Testing:
    - Place the ORPOS installer in ``src/main/test/resources/installer/``
        - don't replace ``{installer_dir}/product/server/bin/db.xml``
    - Have 2 separate 11g listeners up and running
    - Give the connection details to ``src/main/test/resources/META-INF/persistence.xml``
    - Supply passwords for test databases setup by running the following ONCE
        - run ``ant -f .\db.xml encrypt-pos-passwords -lib C:/Users/stephen/repos/jpa-lobber/src/test/resources/installer/ORPOS-13.4.1/prod
                uct/server/common/db/lib/oracle.stores.secure.utility.jar -lib C:/Users/stephen/repos/jpa-lobber/src/test/resources/installer/ORPOS-13.4.1/ant-ext/oracle.stores.encryptionclient.jar -lib C:/Users/stephen/repos/jpa-lobber/s
                rc/test/resources/installer/ORPOS-13.4.1/product/paypal/lib/paypal/log4j-1.2.12.jar -lib C:/Users/stephen/repos/jpa-lobber/src/test/resources/installer/ORPOS-13.4.1/product/shared/common/lib/oracle.stores.utility.jar -lib
                C:/Users/stephen/repos/jpa-lobber/target/test-classes/installer/ORPOS-13.4.1/ant-ext/commons-codec-1.3.jar -lib C:/Users/stephen/Vagrant/boxes/winserv/lillii_installer/stage/ORPOS-13.4.1/ant-ext/ossa/retail-public-security
                -api.jar -lib C:/Users/stephen/Vagrant/boxes/winserv/lillii_installer/stage/ORPOS-13.4.1/product/paypal/lib/paypal/commons-logging-api-1.1.jar``
    - run ``mvn clean test``
    - database test data population log is test-database-init.log
