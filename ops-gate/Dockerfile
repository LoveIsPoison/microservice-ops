FROM 172.16.199.203/daocloud/java:latest

COPY target/ops-gate.jar /usr/src/myapp/ops-gate.jar

WORKDIR /usr/src/myapp/

EXPOSE 9000

ENV JAVA_OPTS ""

ENTRYPOINT java $JAVA_OPTS -jar ops-gate.jar
