FROM 172.16.199.203/daocloud/java:latest

COPY target/ops-auth.jar /usr/src/myapp/ops-auth.jar

WORKDIR /usr/src/myapp/

EXPOSE 9001

ENTRYPOINT java -jar ops-auth.jar
