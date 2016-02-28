# DVDLibrary #
A small project I made to get to know the Spring framework - it's a DVD library web-app that has CRUD operations for all entities in the database. I followed known best practices for RESTful web services.

## The Task ##
* Create, Read, Update, Delete (CRUD) operations for each entity in the DB
* Read/Delete DVD’s for movies with a given rating (allows sorting by genre, movie title and ISBN)
* Read/Delete all DVD’s with language/audio
* Output in both JSON and XML
* Exception handling
* Unit tests

Note: I saw the ER diagram online and just followed it

## Tools ##
* JDK 1.8
* Gradle 2.11
* Spring 4 - Core, JDBC, MVC, 
* Hibernate 5
* HSQLDB 2.3.3
* Tomcat 9

## Configuration ##
* Enter the proper name and password for the database in the dataSource bean - src\main\webapp\WEB-INF\rest-servlet.xml

If deploying via the Tomcat7 Maven plugin:
* Define a server in the Maven's settings.xml file  - In this case I named it "Tomcat8"

        <server>
            <id>Tomcat8</id>
            <username>maven</username>
            <password>maven</password>
	    </server>
	
* Make sure a valid user with manager roles is given - defined in tomcat-users.xml in the Tomcat's conf folder

    	<role rolename="manager-gui"/>
    	<role rolename="manager-script"/>
    	<role rolename="manager-text"/>
    
    	<user username="maven" password="maven" roles="manager-gui,manager-text,manager-script" />