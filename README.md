# FlightHub (DS Group 40) ✈️
## Question 3 - Flight Ticket Booking System
### Features:
  1. Register and Login
  2. Search for flights based on date, source and destination
  3. View the cheapest flights starting from date searched
  4. Book flights
  5. View booking history
  6. Edit booking information
  7. Cancel bookings
#### Data Structure Implemented: 
- custom MyLinkedList & MyQueue class
#### Tech Stack
  - Backend: Java Spring-boot
  - Frontend: HTML, Javascript, CSS
  - Database: phpMyAdmin MySQL

## Try it yourself ⭐
1. Download the repository
- download the repository
- extract and open folder inside IDE (vsc or intellij)

2. NO NEED Set up database. Our database is remote now. If you wanna see our database, can:
- go to https://www.phpmyadmin.co/
- login using
  - server: sql12.freesqldatabase.com
  - username: sql12678174
  - password: rDKfS1TlXm
- Note: Cannot login because max connection user is normal. Can troubleshoot by closing localhost:8080 and login again or wait for a while n login again.

3. Running the application
- open the project folder inside ur IDE (vsc/intellij)(netbeans cannot)
- open terminal inside ur IDE
- run ```cd com.ft.flight```
- run ```mvn clean install``` (this command only need to execute once. after that when u wanna run the application u can just cd to com.ft.flight first, then run mvn spring0boot:run)
- finally, run the app ```mvn spring-boot:run```

- NOTE: if mvn not executable, follow this tutorial to download maven https://www.youtube.com/watch?v=88FB8MDgScA&t=1s
- TIP: must ```cd com.ft.flight``` first, and then ```mvn spring-boot:run```

5. Seeing the website
- open ur browser and go to localhost:8080/login
- NOTE: if your localhost:8080 port is taken, can change the server port to 8081
  - open application.properties file under com.ft.flight>src>main>resources>application.properties
  - ```server.port=8081``` can change to whatever u like, eg 8081 or 8082, etc
  - so when u go to check the website go according to your server port, eg localhost:8081/login
