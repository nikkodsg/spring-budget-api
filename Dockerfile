# base image containing java runtime
FROM adoptopenjdk/openjdk11:alpine-jre

## make the port available outside of this container
#EXPOSE 8080

ARG JAR_FILE=target/*.jar

# copy jar file from target to container directory
COPY ${JAR_FILE} spring-budget-api.jar

# java -jar spring-budget-api.jar
ENTRYPOINT ["java", "-jar", "spring-budget-api.jar"]