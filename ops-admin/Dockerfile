FROM 10.180.4.226/yonyou-dev/jdk-skagent:0.3

RUN mkdir -p /usr/src/myapp

COPY ./target/ops-admin.jar /usr/src/myapp/ops-admin.jar

WORKDIR /usr/src/myapp/

EXPOSE 9002

ENTRYPOINT java -javaagent:/agent/skywalking-agent.jar  -jar ops-admin.jar
