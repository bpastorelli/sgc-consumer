FROM ubuntu:latest
ENV DEBIAN_FRONTEND=noninteractive
RUN apt-get update && apt-get install -y tzdata

FROM adoptopenjdk/openjdk11:alpine
RUN addgroup -S spring && adduser -S spring -G spring  
MAINTAINER Bruno Pastorelli
COPY target/sgc_consumer.jar sgc_consumer.jar
ENTRYPOINT ["java","-jar","/sgc_consumer.jar"]
EXPOSE 9191