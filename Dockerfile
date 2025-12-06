
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

FROM wildfly/wildfly:31.0.0.Final

COPY --from=build /app/target/gymmanagement.war /opt/wildfly/standalone/deployments/ROOT.war