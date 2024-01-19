1. Download the repository
    - download the repository
    - extract and open folder inside IDE (vsc or intellij)

2. NO NEED Set up database. Our database is remote now. If you wanna see our database, can:
    - go to https://www.phpmyadmin.co/
    - login using
        server: sql12.freesqldatabase.com
        username: sql12677284
        password: bHImi3TK9w
    - Note: Cannot login because max connection user is normal.
            Can troubleshoot by closing localhost:8080 and login again

3. Running the application
    - open the project folder inside ur IDE (vsc/intellij)(netbeans cannot)
    - open terminal inside ur IDE
    - run ```cd com.ft.flight```
    - run ```mvn clean install``` (this command only need to execute once. after that when u wanna run the application u can just cd to com.ft.flight first, then run mvn spring0boot:run)
    - finally, run the app ```mvn spring-boot:run```

    NOTE: if mvn not executable, follow this tutorial to download maven https://www.youtube.com/watch?v=88FB8MDgScA&t=1s
    TIP: must ```cd com.ft.flight``` first, and then ```mvn spring-boot:run```

5. Seeing the website
    open ur browser and go to localhost:8080/login
