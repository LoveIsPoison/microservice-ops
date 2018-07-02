FROM 172.16.199.203/daocloud/java:latest

COPY target/ops-admin.jar /usr/src/myapp/ops-admin.jar

WORKDIR /usr/src/myapp/

EXPOSE 9002

ENTRYPOINT java -jar ops-admin.jar

