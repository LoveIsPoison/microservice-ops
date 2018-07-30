FROM 10.180.4.226/yonyou-dev/jdk-pinpoint:beta1

MAINTAINER qianjs163@163.com

#RUN mkdir -p /

COPY ./target/ops-task.jar /ops-task.jar

COPY env.sh /agent/env.sh

RUN chmod -R 777 /agent

WORKDIR /

EXPOSE 9009

CMD /agent/env.sh run


