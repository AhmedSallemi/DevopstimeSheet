FROM openjdk:8-jdk-alpine
EXPOSE 8082
ADD target/DevopsTimeSheet-1.0.war DevopsTimeSheet-1.0.war
ENTRYPOINT ["java","-jar","/DevopsTimeSheet-1.0.war"]