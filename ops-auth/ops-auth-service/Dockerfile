FROM 10.180.4.226/yonyou-dev/jdk-pinpoint:v0.2

RUN mkdir -p /usr/src/myapp

COPY ./target/ops-auth.jar /usr/src/myapp/ops-auth.jar

COPY env.sh /agent/env.sh

RUN chmod -R 777 /agent

WORKDIR /usr/src/myapp/

EXPOSE 9001

CMD /agent/env.sh run
