#!/bin/sh
mvn clean package && docker build -t com.mycompany/database .
docker rm -f database || true && docker run -d -p 8080:8080 -p 4848:4848 --name database com.mycompany/database 
