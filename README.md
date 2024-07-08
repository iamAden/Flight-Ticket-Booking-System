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
- both classes are inside com.ft.flight>src>main>java>com>ft>flight>entity
  
#### Tech Stack:
  - Backend: Java Spring-boot
  - Frontend: HTML, Javascript, CSS
  - Database: phpMyAdmin MySQL (freemysqldatabase.com)

**Pre-requisites** (download and install if you haven't)
1. [Maven](https://maven.apache.org/download.cgi) (make sure maven is inside window's PATH, [here's a tutorial to do that](https://www.youtube.com/watch?v=88FB8MDgScA&t=1s)) 
2. JDK 17 and above
3. Java Extension Pack (if you're using VSC)

## Try it yourself ⭐
### 1. Download the repository
- download the repository
- extract and open folder inside IDE (vsc or intellij)

### 2. Running the application
- open the project folder inside ur IDE (vsc/intellij)(netbeans cannot)
- open terminal inside ur IDE
- run ```cd com.ft.flight```
- run ```mvn clean install```
  - (this command only need to execute once. after that when u wanna run the application u can just cd to com.ft.flight first, then run mvn spring-boot:run)
- finally, run the app ```mvn spring-boot:run```

**ERROR FIX 1**: if mvn not executable error, its because maven is not in your laptop PATH. Follow this [tutorial](https://www.youtube.com/watch?v=88FB8MDgScA&t=1s) to solve
**ERROR FIX 2**: if ur getting surefire error, it's because of your jdk version, go [update your jdk](https://www.oracle.com/java/technologies/downloads/)
**TIP**: must ```cd com.ft.flight``` first, and then ```mvn spring-boot:run```

### 3. Seeing the website
- open ur browser and go to localhost:8080/login anddd DONE!! 🥳
- Flightsprins are only available for February 2024, Alor Setar➡️KLIA & KLIA2➡️Penang

**ERROR FIX**: if you are getting error localhost:8080 port is taken, there are two ways to solve this
- Kill any running terminal
    OR
- if you killed the terminal but still facing the same problem, can change the server port
    - open application.properties file under com.ft.flight>src>main>resources>application.properties
    - ```server.port=8081``` can change to whatever u like, eg 8081 or 8082 or 8088, etc
    - so when u go to check the website go according to your server port, eg localhost:8081/login
    
**TIP**: If you can't send message inside contact, turn off your antivirus program or change to another browser



## Gallery🎨
Register Page
![Login Page](/screenshots/screenshot10.png)
Login Page
![Login Page](/screenshots/screenshot1.png)
Home Page
![Home Page](/screenshots/screenshot2.png)
About Us
![About Us Page](/screenshots/screenshot3.png)
Contact
![Contact Page](/screenshots/screenshot4.png)
Search Flight
![Booking Page](/screenshots/screenshot5.png)
Search Results
![Search Results Page](/screenshots/screenshot6.png)
Booking Form
![Booking Form](/screenshots/screenshot7.png)
Booking History
![Booking History](/screenshots/screenshot8.png)
Edit Booking
![Edit Booking](/screenshots/screenshot9.png)
