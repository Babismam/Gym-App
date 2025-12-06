# --- ΣΤΑΔΙΟ 1: Build Stage ---
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

# --- ΣΤΑΔΙΟ 2: Final Stage ---
# Χρησιμοποιούμε την πλήρη, σωστή διεύθυνση από το αποθετήριο quay.io
FROM quay.io/wildfly/wildfly:31.0.0.Final

# Αντιγράφουμε το .war αρχείο με το αρχικό του όνομα
COPY --from=build /app/target/gymmanagement.war /opt/wildfly/standalone/deployments/