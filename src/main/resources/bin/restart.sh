#!/bin/sh
PRGDIR=$(cd "$(dirname "$0")";pwd)

source $PRGDIR/cloudConfig.sh
$PRGDIR/run.sh $CLOUD_ARG_CLUSTER $CLOUD_ARG_ENV $CLOUD_ARG_PORT "restart"
