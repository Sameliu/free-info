#!/bin/sh
mvn clean package
mv -f target/free-info-1.0-SNAPSHOT-assembly.tar.gz   /Users/sijinzhang/study/app/freeInfo/
ps -ef | grep com.sijin.free.main.Fe | awk  '{print $2}'| sed -n '1p' | xargs kill -9
tar -xvf /Users/sijinzhang/study/app/freeInfo/free-info-1.0-SNAPSHOT-assembly.tar.gz -C /Users/sijinzhang/study/app/freeInfo/
cd /Users/sijinzhang/study/app/freeInfo/free-info-1.0-SNAPSHOT
sh bin/start.sh
pid=`ps -ef | grep com.sijin.free.main.Fe | awk  '{print $2}'| sed -n '1p'`
echo $pid







