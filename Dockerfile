
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

FROM jboss/wildfly:latest

COPY --from=build /app/target/gymmanagement.war /opt/jboss/wildfly/standalone/deployments/ROOT.war