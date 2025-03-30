FROM amazoncorretto:21-alpine-jdk
RUN apk add curl && addgroup -S spring && adduser -S spring -G spring
USER spring:spring
WORKDIR /usr/src/app
RUN mkdir /usr/src/app/logs
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Xmx2048M","-jar","/usr/src/app/app.jar"]