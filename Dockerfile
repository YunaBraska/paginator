#Private repo
#FROM harbor.scene.to/library/chrome:latest
#Public repo
FROM markhobson/maven-chrome:jdk-11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]