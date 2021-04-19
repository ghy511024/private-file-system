#!/bin/sh

export CLOUD_ARG_CLUSTER="sec_ctf_private_file"
export CLOUD_ARG_PORT=8080

case "$WCloud_Env" in
"Product")
  export CLOUD_ARG_ENV="online"
  ;;
"SandBox")
  export CLOUD_ARG_ENV="sandbox"
  ;;
"Test" | "Stable")
  export CLOUD_ARG_ENV="offline"
  ;;
*)
  export CLOUD_ARG_ENV="offline"
  ;;
esac
