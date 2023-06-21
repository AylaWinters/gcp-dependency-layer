FROM openjdk:8 AS dependency-build
RUN mkdir /app
COPY . /app
WORKDIR /app
RUN apt-get update && apt-get install -y maven
RUN mvn clean install -DskipTests
