#! /bin/env bash
top -n 1 -o %MEM |grep -A20 PID|sed -n '2,11p' >/tmp/process
mid=0
while read line
do
  let "mid+=1"
  echo ${line/(B[m/""} |awk '{print "dprocess_mem,pid=" $1 ",rowid=" p  " user=\"" $2 "\",pr=" $3 ",ni=" $4 ",virt=" $5 ",res=" $6 ",shr=" $7 ",s=\"" $8 "\",cpu_percent=" $9 ",mem_percent=" $10 ",time+=\"" $11 "\",command=\"" $12 "\""}' p="$mid"
done < /tmp/process