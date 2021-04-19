#!/bin/sh
# ./run.sh sec_ctf_private_file online 8080 "checkApp"
PRGDIR=$(cd "$(dirname "$0")";pwd)

source $PRGDIR/cloudConfig.sh
$PRGDIR/run.sh $CLOUD_ARG_CLUSTER $CLOUD_ARG_ENV $CLOUD_ARG_PORT "checkApp"
