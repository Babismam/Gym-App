# --- ΣΤΑΔΙΟ 1: Build (Χτίσιμο της εφαρμογής) ---
# Χρησιμοποιούμε μια εικόνα που έχει Maven και Java 17
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Ορίζουμε φάκελο εργασίας
WORKDIR /app

# Αντιγράφουμε το pom.xml και κατεβάζουμε τα dependencies
COPY pom.xml .
# (Αυτό το βήμα βοηθάει να κρατάει cache τα dependencies αν δεν αλλάξει το pom)
# Αν θες πιο γρήγορο build στο μέλλον, τρέξε: RUN mvn dependency:go-offline

# Αντιγράφουμε τον πηγαίο κώδικα
COPY src ./src

# Χτίζουμε το WAR (παραλείπουμε τα tests για ταχύτητα στο cloud)
RUN mvn clean package -DskipTests

# --- ΣΤΑΔΙΟ 2: Run (Εκτέλεση στο Wildfly) ---
FROM quay.io/wildfly/wildfly:30.0.0.Final-jdk17

# Δημιουργία χρήστη admin
RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#123 --silent

# Εντολή εκκίνησης
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]

# ΑΝΤΙΓΡΑΦΗ του WAR από το ΣΤΑΔΙΟ 1 (build) στο Wildfly
# Προσέχουμε να πάρουμε το σωστό όνομα αρχείου που ορίσαμε στο pom.xml
COPY --from=build /app/target/gymmanagement.war /opt/jboss/wildfly/standalone/deployments/gymmanagement.war