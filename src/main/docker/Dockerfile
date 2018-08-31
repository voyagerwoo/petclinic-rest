FROM openjdk:8-jdk-alpine
RUN mkdir /root/app
COPY target/petclinic-rest-0.0.1.jar /root/app/app.jar
WORKDIR /root/app
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]