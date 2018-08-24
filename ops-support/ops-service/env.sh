#!/bin/sh
sed -i 's/profiler.collector.ip=127.0.0.1/profiler.collector.ip='$(echo $profiler_collector_ip)'/g' /agent/pinpoint.config
sed -i 's/profiler.sampling.rate=20/profiler.sampling.rate='$(echo $profiler_sampling_rate)'/g' /agent/pinpoint.config
export JAVA_OPTS=$JAVA_OPTS$RANDOM
java -javaagent:/agent/pinpoint-bootstrap-1.7.2.jar $JAVA_OPTS  -jar ops-service.jar