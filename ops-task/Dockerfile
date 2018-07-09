FROM 172.16.199.203/daocloud/java:latest

COPY target/ops-task.jar /usr/src/myapp/ops-task.jar

WORKDIR /usr/src/myapp/

EXPOSE 9009

ENV JAVA_OPTS ""

ENTRYPOINT java $JAVA_OPTS -jar ops-task.jar

