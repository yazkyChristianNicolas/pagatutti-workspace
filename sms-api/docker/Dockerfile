FROM openjdk:8-jdk-alpine
EXPOSE 8080
ARG JAR_FILE=sms-api-1.0.0.jar
ADD application.properties /etc/pagatutti/sms-api/application.properties
ADD logback.xml /etc/pagatutti/sms-api/logback.xml
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]