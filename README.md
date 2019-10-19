# dms
repo for Device Management System 

# Install Postgres in Docker
(Run the all of the following in console)
docker run -d -p 5432:5432 --name dms-db -e POSTGRES_PASSWORD=secret postgres

## Create DB in Docker

### Navigate into the Postgres console from your local console
docker exec -it dms-db psql -U postgres

### Create DB with psql
 CREATE DATABASE dmsdb;

