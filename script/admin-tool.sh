#!/bin/bash

# path related variables that should be changed only if necessary
CURRENT_PATH="$(readlink -f "$0")" # get the current path of this file, with path and file name
CURRENT_DIR="$(dirname "${CURRENT_PATH}")" # get the current path of this file, without the file name
BASE_PATH="$(dirname "${CURRENT_DIR}")" # get the parent path of this file, which will be used as base for everything else

# loads the common environment settings
# shellcheck disable=SC1090
ENV_FILE=${BASE_PATH}/config/env.properties
if [ ! -f "${ENV_FILE}" ]; then
  echo "Erro. Arquivo $ENV_FILE nao existe ou nao esta disponivel."
  echo "Por favor coloque o arquivo $ENV_FILE conforme o ambiente."
  exit 1
fi
source "${ENV_FILE}"

# local variables based on common environment settings
if [[ "$OSTYPE" == "msys" ]]; then
  CURR_JAVA=java
else
  CURR_JAVA="${JAVA_HOME_8}"/bin/java
fi
CURR_JAR_CLIENT="${BASE_PATH}/bin/${JAR_CLIENT}"
CURR_JAVA_OPTS="${GENERIC_JAVA_OPTS}"

PID_FILE=$BASE_PATH/script/pid.file
RUNNING=N

if [ -f $PID_FILE ]; then
  PID=$(cat $PID_FILE)
  if [ -n "$PID" ] && kill -0 "$PID" 2>/dev/null; then
    RUNNING=Y
  fi
fi

start() {
  if [ $RUNNING == "Y" ]; then
    echo "Application already started"
  else
    if [ -z "$CURR_JAR_CLIENT" ]; then
      echo "ERROR: jar file not found"
    else
      nohup "${CURR_JAVA}" ${CURR_JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar "${CURR_JAR_CLIENT}" --spring.config.location=file:/apps/ipn/tar/output-data/admin-tool/config/ > $BASE_PATH/script/nohup.out 2>&1  &
      echo $! > $PID_FILE
      echo "Application $CURR_JAR_CLIENT starting..."
      sleep 10
      tail -n 20 $BASE_PATH/script/nohup.out
    fi
  fi
}

stop() {
  if [ $RUNNING == "Y" ]; then
    if [[ "$OSTYPE" == "msys" ]]; then
      /bin/kill.exe -f "${PID}"
    else
      curl -m 3 -X POST 127.0.0.1:8587/admin-tool/actuator/shutdown || true
      sleep 2
      for pid in $(ps -ef | grep admin-tool-standalone.jar | grep -v grep | awk '{print $2}'); do
        echo "Killing $pid"
        kill -9 $pid
      done;            
    fi
    rm -f $PID_FILE
    RUNNING=N
    echo "Application stopped"
  else
    echo "Application not running"
  fi
}

restart() {
  stop
  start
}

case "$1" in
  'start') start ;;
  'stop') stop ;;
  'restart') restart ;;
  *)
    echo "Usage: $0 {  start | stop | restart  }"
    exit 1
    ;;
esac
exit 0
