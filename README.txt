1. download the repository 

2. Setting up MySQL:
    - Download MySQL https://dev.mysql.com/downloads/installer/
    - Install MySQL
    - Username put as root
    - Password as whatever u want

3. Setting up Database
    - go to com.ft.flight/backend/src/main/java/resources/application.properties
    - Modify spring.datasource.password=[your MySQL password]
    - open MySQL Command Line
    - run 'Create schema ftbs'

4. Running the application
    - open the project folder inside ur ide (vsc/intellij)(netbeans cannot)
    - open terminal inside ur ide
    - `cd com.ft.flight`
    - mvn clean install (this command only need to execute once. after that when u wanna run the application u can just cd to com.ft.flight first, then run mvn spring0boot:run)
    - mvn spring-boot:run

5. Seeing the website
    open ur browser and go to localhost:8080/login
