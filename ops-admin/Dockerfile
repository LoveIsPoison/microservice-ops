FROM 172.16.199.203/daocloud/java:latest

COPY target/ops-admin.jar /usr/src/myapp/ops-admin.jar

WORKDIR /usr/src/myapp/

ENV JAVA_OPTS ""

EXPOSE 9002

ENTRYPOINT java $JAVA_OPTS -jar ops-admin.jar

