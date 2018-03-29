# projectx

At project root:
$./mvnw install dockerfile:build

Create docker network
docker network create my-docker-local

Run server instance:
docker run -p 8761:8761 --net=my-docker-local server-docker/server2

Run client instance:
docker run -p 9090:9090 --net=my-docker-local client-docker/client2