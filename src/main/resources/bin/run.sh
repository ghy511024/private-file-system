#!/bin/sh
# sh ./bin/run.sh "private-file-system" "offline" "8080" "start"

# $0
PRGDIR=$(cd "$(dirname "$0")";pwd)
EXECUTABLE=appctl.sh
if [ ! -x "$PRGDIR"/"$EXECUTABLE" ]; then
  echo "Cannot find $PRGDIR/$EXECUTABLE"
  echo "The file is absent or does not have execute permission"
  echo "This file is needed to run this program"
  exit 1
fi
# $1
if [ -n "$1" ]; then
  ARG_CLUSTER_NAME="$1"
else
  ARG_CLUSTER_NAME="sec_ctf_private_file"
fi
# $2
case "$2" in
"offline" | "online" | "sandbox")
  export ARG_ENV="$2"
  ;;
"stable")
  export ARG_ENV="offline"
  ;;
*)
  export ARG_ENV="online"
  ;;
esac
# $3
if [ -n "$3" ]; then
  ARG_PORT=$3
else
  ARG_PORT=8080
fi
# $4
case "$4" in
"start" | "stop" | "restart" | "checkApp")
  source "$PRGDIR"/"$EXECUTABLE"
  $4
  ;;
*)
  echo "wrong command arg"
  ;;
esac
