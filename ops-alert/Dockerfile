FROM 172.16.199.203/daocloud/java:latest

COPY target/ops-alert.jar /usr/src/myapp/ops-alert.jar

WORKDIR /usr/src/myapp/

EXPOSE 9008

ENTRYPOINT java -jar ops-alert.jar
