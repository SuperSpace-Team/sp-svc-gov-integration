#!/bin/bash
APP_HOME=/service/webapps/staging/svc-gov-sdk-demo1
JAR_NAME=demo-0.0.1-SNAPSHOT.jar
log_base=/service/webapps/staging/svc-gov-sdk-demo1/logs
ACTIVE_PROFILE=st
DEBUG_MODE=false

PID=$(ps -ef | grep $APP_HOME | grep $JAR_NAME | grep -v grep | awk '{ print $2 }')
if [ ! -z "$PID" ]; then
    echo "java 已经启动，进程号: $PID"
else
    echo -n -e "启动 java ... \n"
    nohup java -Dlogging.config=$APP_HOME/bin/log4j2.xml -jar $APP_HOME/lib/$JAR_NAME --spring.profiles.active=$ACTIVE_PROFILE --debug=$DEBUG_MODE > $log_base/stdout.log  2>&1 &

    if [ "$?"="0" ]; then
        echo "启动完成，请查看日志确保成功"
        echo "日志查看为：tail -f $log_base/stdout.log"
    else
        echo "启动失败"
    fi
fi