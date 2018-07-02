FROM 172.16.199.203/daocloud/java:latest

COPY target/ops-gate.jar /usr/src/myapp/ops-gate.jar

WORKDIR /usr/src/myapp/

EXPOSE 9000

ENTRYPOINT java -jar ops-gate.jar
