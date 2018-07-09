FROM 172.16.199.203/daocloud/java:latest

COPY target/ops-alert.jar /usr/src/myapp/ops-alert.jar

ENV JAVA_OPTS ""

WORKDIR /usr/src/myapp/

EXPOSE 9008

ENTRYPOINT java $JAVA_OPTS -jar ops-alert.jar
