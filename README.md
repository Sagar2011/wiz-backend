# wiz-backend

# Running Application:
 -> Dependency Required:
    1) maven
    2) java
    3) docker
    
# Commands to run:
  ```
   ----optional-----------
  -> docker-compose up -d
  -> docker ps
  -> docker exec -it <dockerId> bash
  -> mysql -u root -p
  -> example
  -> create database studentDB;
  -> exit
  ----optional-----------
  -> cd wiz-backend
  -> mvn clean install 
  -> mvn spring-boot:run
  ```
  # port started at http://localhost:9000
  
 - note: for mysql db you can run mysql server without docker but remember that its running on 3306 port due to configuration in the spring boot project.
