FROM 172.16.199.203/daocloud/java:latest

COPY target/ops-mq.jar /usr/src/myapp/ops-mq.jar

WORKDIR /usr/src/myapp/

EXPOSE 9003

ENV JAVA_OPTS ""

ENTRYPOINT java $JAVA_OPTS -jar ops-mq.jar
