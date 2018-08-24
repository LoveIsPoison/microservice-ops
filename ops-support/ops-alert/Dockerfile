FROM 10.180.4.226/yonyou-dev/jdk-pinpoint:v0.2

RUN mkdir -p /usr/src/myapp

COPY ./target/ops-alert.jar /usr/src/myapp/ops-alert.jar

COPY env.sh /agent/env.sh

RUN chmod -R 777 /agent

WORKDIR /usr/src/myapp/

EXPOSE 9008

CMD /agent/env.sh run
