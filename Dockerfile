# --- ΣΤΑΔΙΟ 1: Build Stage ---
# Χρησιμοποιούμε μια επίσημη εικόνα του Maven για να χτίσουμε το project
FROM maven:3.8.5-openjdk-17 AS build

# Ορίζουμε τον φάκελο εργασίας μέσα στο container
WORKDIR /app

# Αντιγράφουμε πρώτα το pom.xml για να εκμεταλλευτούμε το Docker caching
COPY pom.xml .

# Κατεβάζουμε τις εξαρτήσεις (dependencies)
RUN mvn dependency:go-offline

# Αντιγράφουμε τον υπόλοιπο κώδικα της εφαρμογής
COPY src ./src

# Εκτελούμε την εντολή του Maven για να δημιουργήσουμε το .war αρχείο
RUN mvn package -DskipTests


# --- ΣΤΑΔΙΟ 2: Final Stage ---
# Ξεκινάμε από μια ΣΥΓΚΕΚΡΙΜΕΝΗ, ΜΟΝΤΕΡΝΑ έκδοση του Wildfly ΜΕ ΤΗ ΣΩΣΤΗ JAVA
FROM jboss/wildfly:31.0.0.Final-jdk17

# Αντιγράφουμε το .war αρχείο και το μετονομάζουμε σε ROOT.war
COPY --from=build /app/target/gymmanagement.war /opt/jboss/wildfly/standalone/deployments/ROOT.war