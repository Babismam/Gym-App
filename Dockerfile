FROM quay.io/wildfly/wildfly:30.0.0.Final-jdk17

RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#123 --silent

# Αντιγραφή του WAR (βεβαιώσου ότι έχεις κάνει mvn clean package)
COPY target/gymmanagement.war /opt/jboss/wildfly/standalone/deployments/ROOT.war

# Εδώ γίνεται η μαγεία: Περνάμε τα ENV vars του Render μέσα στο Wildfly
CMD ["/bin/sh", "-c", "/opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0 -DDB_URL=$DB_URL -DDB_USER=$DB_USER -DDB_PASSWORD=$DB_PASSWORD"]