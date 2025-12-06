# --- ΣΤΑΔΙΟ 1: Build Stage ---
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

# --- ΣΤΑΔΙΟ 2: Final Stage ---
# Χρησιμοποιούμε μια επιβεβαιωμένη, σταθερή έκδοση του Wildfly από το Docker Hub
FROM wildfly/wildfly:27.0.0.Final

# Η διαδρομή αντιγραφής για αυτή την έκδοση
COPY --from=build /app/target/gymmanagement.war /opt/jboss/wildfly/standalone/deployments/ROOT.war