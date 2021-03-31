# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine

# Copy war file
COPY mail-api-0.0.1.jar /mail-api.war
EXPOSE 8080
# run the app
CMD ["/usr/bin/java", "-jar", "/mail-api.war"]