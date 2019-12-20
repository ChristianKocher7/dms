# dms (Device Management System)
This application is a tool used by an administrator to view all of the devices which
employees are using. Including the specs, last login dates and maintenance details of a device.

The application consists of 3 major components:

1. The Backend - A Java based application built on Spring Boot (built with maven) where all processing of the devices 
    is done.
2. The Frontend - An Angular web application for viewing the devices.
3. The Database - A PostgreSql relational database.

In order to use the application on a local machine the following steps need to taken:

1. Start a docker container with a PostgreSQL DB
2. Start the java application as a Spring Boot application
3. Start the angular we application
4. Import data

## Requirements

- Java 8
- Docker Desktop
- Node.js
- Node Package Manager (npm)

For details on how to do this see below.

# Set Up
## Install Postgres in Docker
Docker Desktop is required to run the application

(Run the following in a console)
docker run -d -p 5432:5432 --name dms-db -e POSTGRES_PASSWORD=secret postgres

## Create DB in Docker
1. Enter the Postgre CLI in the container

        docker exec -it dms-db psql -U postgres
        
2. Create the database

        CREATE DATABASE dmsdb;

## Backend

Open the java application in an IDE of your choice that supports maven. Make sure to select the pom.xml file as your maven configuration.
Run the "main" method inside the class DmsApplication

##Frontend

Navigate inside the dms-gui folder from a console and run

        npm start

The web app can now be found at localhost:4200

## Import

In order to fill the database with data navigate to:

    http://localhost:8080/api/import
    
If you see the word "imported" displayed the import was successful.

## Log in

Log in to the web app using 

user: admin
password: 1234
