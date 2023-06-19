FROM openjdk:8 AS dependency-build
RUN mkdir /app
COPY . /app
WORKDIR /app
RUN chown -R root:root /app
RUN ls -al /app
RUN chmod +x /app
RUN /app/ mvn clean install -DskipTests