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
        - run ``ant -f .\db.xml encrypt-pos-passwords`` adding jars as necessary with ``lib``

    - ``mvn clean test``
    - database test data population log: test-database-init.log
