FROM 172.16.199.203/daocloud/java:latest

COPY target/ops-mq.jar /usr/src/myapp/ops-mq.jar

WORKDIR /usr/src/myapp/

EXPOSE 9003

ENTRYPOINT java -jar ops-mq.jar
