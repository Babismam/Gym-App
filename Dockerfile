# --- ΣΤΑΔΙΟ 1: Build Stage ---
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

# --- ΣΤΑΔΙΟ 2: Final Stage ---
# Χρησιμοποιούμε την επίσημη, σωστή ετικέτα για το Wildfly 31
FROM jboss/wildfly:31.0.0.Final

# Αντιγράφουμε το .war αρχείο και το μετονομάζουμε σε ROOT.war
COPY --from=build /app/target/gymmanagement.war /opt/jboss/wildfly/standalone/deployments/ROOT.war