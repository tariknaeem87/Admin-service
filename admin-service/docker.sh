#!/usr/bin/env bash
port=8080
docker stop admin-service
docker rm admin-service
docker rmi admin-service
docker run -i -d -p 8080:8080 --expose 8080 --name admin-service -t demo/admin-service
