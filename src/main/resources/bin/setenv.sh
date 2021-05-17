#!/bin/sh
MEMORY=`free -m | awk '/Mem/{print $2}'`
if [ $MEMORY -gt 15000 ];then
    JVM_HEAP="6144"
elif [ $MEMORY -gt 7500 ];then
    JVM_HEAP="5120"
elif [ $MEMORY -gt 3500 ];then
    JVM_HEAP="2048"
elif [ $MEMORY -gt 1800 ];then
    JVM_HEAP="768"
else
    JVM_HEAP=$((MEMORY/7*4))
fi
JVM_EDEN=$((JVM_HEAP/8*3))
JVM_META=$((JVM_HEAP/4))
JVM_MAX_META=$((JVM_HEAP/2))
ACTIVE_PROFILE=$ARG_ENV
JAVA_OPTS="-server -Xms${JVM_HEAP}m -Xmx${JVM_HEAP}m -Xmn${JVM_EDEN}m -XX:MetaspaceSize=${JVM_META}m -XX:MaxMetaspaceSize=${JVM_MAX_META}m -XX:SurvivorRatio=8 -Xss256k -XX:-UseAdaptiveSizePolicy -XX:+PrintPromotionFailure -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$APP_LOGS_DIR/oom.hprof -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:CMSInitiatingOccupancyFraction=70 -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:GCLogFileSize=100M -Xloggc:$APP_LOGS_DIR/gc.log"

JAVA_MONITOR_OPTS="-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=7777 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"
JAVA_OPTS_BASE="-Djava.awt.headless=true -Djava.net.preferIPv4Stack=true -Dfile.encoding=UTF-8 -DAppBaseDir=$APP_BASE"

if [[ $WCloud_Env != "Product" ]]; then
    JAVA_OPTS=" $JAVA_OPTS $JAVA_MONITOR_OPTS $JAVA_OPTS_BASE -Xrunjdwp:transport=dt_socket,address=18848,server=y,suspend=n -Xdebug -Xnoagent -Djava.compiler=NONE"
    else
    JAVA_OPTS=" $JAVA_OPTS $JAVA_OPTS_BASE"
fi

#if [[ "@environment@" == "dev" ]]; then
#    JACOCO_ROOT=`pwd`
#    IP=`/sbin/ifconfig -a|grep inet|grep -v 127.0.0.1|grep -v inet6|awk '{print $2}'|tr -d "addr:"`
#    JAVA_OPTS=" $JAVA_OPTS -javaagent:$JACOCO_ROOT/../jacocoagent.jar=output=tcpserver,address=$IP"
#fi

JAVA_OPTS=" $JAVA_OPTS -Dspring.profiles.active=$ACTIVE_PROFILE"
export CATALINA_OPTS=" $JAVA_OPTS "
