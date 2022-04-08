# build package
FROM maven:3.6.0-jdk-11-slim AS build
WORKDIR /usr/src/app
COPY src src
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2,rw mvn clean package -DskipTests

# create image
FROM build
ENTRYPOINT ["java","-jar","target/payment-0.0.1-SNAPSHOT.jar"]