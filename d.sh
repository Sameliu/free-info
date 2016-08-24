#!/bin/sh
mvn clean package
mv target/free-info-1.0-SNAPSHOT-assembly.jar  ~/study/free-info/
ps -ef | grep Fe | awk  '{print $2}'| sed -n '1p' | xargs kill -9
sh /Users/sijinzhang/study/free-info/bin/fe.sh
pid=`ps -ef | grep Fe | awk  '{print $2}'| sed -n '1p'`
echo $pid

