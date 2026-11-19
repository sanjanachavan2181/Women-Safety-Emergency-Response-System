# Women Safety and Emergency Response System
JSP + Servlet + JDBC + MySQL | Tomcat 10.1 | Java 17 | Eclipse IDE for Enterprise Java

## Setup
1. MySQL Command Line Client:  `source <path>/database/schema.sql;`
2. Edit `util/DBConnection.java` -> set your MySQL USER / PASSWORD.
3. Eclipse: File > Import > Maven > Existing Maven Projects (pom.xml downloads the JSTL + MySQL jars).
   Add Apache Tomcat 10.1 as the server runtime (Project > Properties > Targeted Runtimes).
4. Right-click project > Run As > Run on Server. Open http://localhost:8080/WomenSafety/
5. Admin login: `admin` / `admin123` at /admin/login (change this password).

## Structure (layers)
- model/    plain Java beans (User, Contact, Alert, Tip)
- dao/      JDBC + PreparedStatement only
- servlet/  request handling and validation
- filter/   AuthFilter protects /user/* and /admin/*
- util/     DBConnection, PasswordUtil (PBKDF2 salted hash), Validator, NotificationService
- webapp/WEB-INF/views/  JSP pages (only reachable through servlets)

## Important limitation (as stated in the report)
NotificationService is a placeholder: it only prints to the Tomcat console and the alert status shows
"Pending - notification service not configured". Integrate an SMS/email provider there for real delivery.
