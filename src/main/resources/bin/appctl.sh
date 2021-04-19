#!/bin/sh

# -----------------------------------------------------------------------------
# Control Script for the Server
#
# Environment Variable Prerequisites
#
#   Do not set the variables in this script. Instead put them into a script
#   setenv.sh in bin to keep your customizations separate.
#
#   directory structure
#   --- $app.tar.gz(root)
#   --- $app(root)
#       --- bin
#           --- start.sh
#           --- stop.sh
#           --- preload.sh
#           --- online.sh
#           --- offline.sh
#           --- setenv.sh
#       --- logs
#           --- $app.pid
#       --- $app-xxx.jar
#

APP_NAME=private-file-system
APP_PORT=$ARG_PORT
export JDK_VERSION=jdk1.8.0_144

ROOT_PATH=$(dirname "$0")
cd $ROOT_PATH
BIN_PATH=$(pwd)
cd ${BIN_PATH}/..
export APP_BASE=$(pwd)
APP_TMP_DIR=$APP_BASE/tmp
export PID_FILE=$APP_TMP_DIR/$APP_NAME.pid
export APP_LOGS_DIR=$APP_BASE/logs/$APP_NAME
JAR_FILE=$(ls $APP_BASE/*.jar)

mkdir -p $APP_TMP_DIR
mkdir -p $APP_LOGS_DIR

if [ -r "$APP_BASE/bin/setenv.sh" ]; then
  . "$APP_BASE/bin/setenv.sh"
fi

grepAppPID() {
  ps -ef | grep "$JAR_FILE" | grep -v grep | awk '{print $2}'
}

checkApp() {
  result=$(grepAppPID)
  echo "checkApp pid result: $result"
  if [[ $result > 0 ]]; then
    echo "success!"
  else
    echo "fail!"
    exit 1
  fi
}

syncJDK() {
  echo "begin change JDK version to: $JDK_VERSION"
  . JDK $JDK_VERSION
}

restart() {
  stop
  sleep 5
  start
}

start() {
  PID=$(grepAppPID)
  if [ $PID ] >0; then
    echo "$APP_NAME has already started, it's pid is $PID"
  else
    syncJDK
    nohup java $CATALINA_OPTS -jar $JAR_FILE > /dev/null 2>&1 &
    PID=$!
    echo "$PID" >$PID_FILE
    sleep 5
    checkApp
  fi
}

stop() {
  if [ -e $PID_FILE ]; then
    PID=$(cat $PID_FILE)
    if [ "$PID" == "" ]; then
      echo "can not find process pid in file $PID_FILE. remove it."
      rm $PID_FILE
    else
      if [ $PID ] >0; then
        echo "$(hostname): stopping $APP_NAME $PID ... "
        kill $PID
        i=0
        max_retry=3
        while [ 1 ]; do
          if [ ! -d /proc/$PID ]; then
            echo "$(hostname): $APP_NAME $PID Stop Success."
            rm $PID_FILE
            break
          else
            kill $PID
          fi
          sleep 5s
          i=$(expr $i + 1)
          if [ $i -ge $max_retry ]; then
            echo "$(hostname): $APP_NAME $PID Stop Failed. Starting force kill"
            kill -9 $PID
              rm $PID_FILE
            echo "$(hostname): $APP_NAME $PID Stop Success With Force Kill."
          fi
        done

      else
        echo "pid not found!"
      fi
    fi

  else

    echo "pid file not exists, use pkill"
    pkill -f $JAR_FILE
    if [ $? -eq 0 ]; then
      echo "stop success!"
    fi
  fi
}
