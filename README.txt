1. Download the repository
    - download the repository
    - open cmd to test if 'mvn' is a recognized command (or else cant run application)
    - test by typing 'mvn --version' inside cmd
    - if not, can follow this tutorial https://bobbyhadz.com/blog/mvn-is-not-recognized-as-internal-or-external-command

2. preparing the database:
    - go to https://www.phpmyadmin.co/
    - login using
        server: sql12.freesqldatabase.com
        username: sql12677284
        password: bHImi3TK9w

3. Running the application
    - open the project folder inside ur ide (vsc/intellij)(netbeans cannot)
    - open terminal inside ur ide
    - `cd com.ft.flight`
    - mvn clean install (this command only need to execute once. after that when u wanna run the application u can just cd to com.ft.flight first, then run mvn spring0boot:run)
    - mvn spring-boot:run

5. Seeing the website
    open ur browser and go to localhost:8080/login
