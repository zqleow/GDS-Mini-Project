# GDS-Mini-Project

This submission is for the GDS Mini Project. The web application developed using Spring Boot framework includes 2 endpoints:  
- GET /users
- POST /upload

The project was generated using Spring Initializr (https://start.spring.io/) and the dependencies added are Spring Web and Spring Data MongoDB.

All other dependencies can be found at pom.xml.

## Connection to Database

Connection details to MongoDB Atlas can be found at application.properties file. 

More details to be added or can be shared during demo/walkthrough session

## Start Web Application

After downloading the source code, launch Eclipse IDE.
- Click on File -> Import
- <img width="425" alt="image" src="https://user-images.githubusercontent.com/59584252/169192574-6678926d-f769-45b1-9d15-62e57ac5d9c5.png">
- Select Existing Maven Projects
- Click on Browse
- Navigate to and Choose the folder which contains the source code
- <img width="359" alt="image" src="https://user-images.githubusercontent.com/59584252/169192960-95822acb-cf4d-454e-aef3-ca69341e56d2.png">
- /pom.xml should appear. Click on checkbox to select it
- Click on Finish
- After project has been successfully imported, open src\main\java\com\gds\miniproject\MemberRestService\MemberRestServiceApplication.java
- Right click, Run As -> Spring Boot App
- Apache Tomcat server should start to run and URL would be http://localhost:8080
