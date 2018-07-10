FROM 10.180.8.226/yonyou-dev/jdk:1.8

RUN mkdir -p /usr/src/myapp

COPY target/ops-auth.jar /usr/src/myapp/ops-auth.jar

WORKDIR /usr/src/myapp/

EXPOSE 9001

ENTRYPOINT java -jar ops-auth.jar
