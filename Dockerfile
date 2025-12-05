
FROM jboss/wildfly:latest

COPY target/gymmanagement.war /opt/jboss/wildfly/standalone/deployments/