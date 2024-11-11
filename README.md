# backend

### ACME mysql maven server Startup Instructions
- Install MySQL server
- Using sqlWorkbench or other means, Create a localhost MYSQL database schema called "ACME", with user "root" and no password
- Confirm mysql server is running
- In eclipse, install "Spring Boot 4" plugin.
- In eclipse, "open project from folder contents" and navigate to this folder "ACME".  Will open up the Maven project in Eclipse.
- RUN project.  Should start up a server on localhost:8080.
- Open localhost:8080/testusers, should show empty list.
- curl the following command to add an item to testUsers:
    curl -X POST http://localhost:8080/testusers \
     -H "Content-Type: application/json" \
     -d '{"name": "John Doe", "email": “john@example.com"}'
- Reload localhost:8080/testusers to confirm the user has been input into the database.
- CRUD interface for testusers has been implemented and is operational.
