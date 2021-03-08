# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine


RUN apk add tzdata
RUN cp /usr/share/zoneinfo/Asia/Seoul /etc/localtime
RUN echo "Asia/Seoul" >  /etc/timezone
RUN apk del tzdata


#  Add Maintainer Info
LABEL maintainer="ryuhon@devbox.kr"

VOLUME /app

EXPOSE 8080

ARG JAR_FILE=build/libs/ad-admin.jar

ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
