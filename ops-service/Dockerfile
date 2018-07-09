FROM 172.16.199.203/daocloud/java:latest

COPY target/ops-service.jar /usr/src/myapp/ops-service.jar

WORKDIR /usr/src/myapp/

EXPOSE 9004

ENV JAVA_OPTS ""

ENTRYPOINT java $JAVA_OPTS -jar ops-service.jar
