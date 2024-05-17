#!/bin/bash

ROOT_PATH="/home/ec2-user/spring"
JAR="$ROOT_PATH/build/libs/be-3rd-project-0.0.1-SNAPSHOT.jar"
STOP_LOG="$ROOT_PATH/stop.log"
SERVICE_PID=$(pgrep -f $JAR) # 실행 중인 Spring 서버의 PID

echo "SERVICE_PID: $SERVICE_PID" >> $STOP_LOG

if [ -z "$SERVICE_PID" ]; then
  echo "[$(date +%c)] 서비스 NotFound" >> $STOP_LOG
else
  echo "[$(date +%c)] 서비스 PID: $SERVICE_PID" >> $STOP_LOG
  echo "[$(date +%c)] 서비스 종료 " >> $STOP_LOG
  kill "$SERVICE_PID"
  if [ $? -eq 0 ]; then
    echo "[$(date +%c)] 서비스가 정상적으로 종료되었습니다." >> $STOP_LOG
  else
    echo "[$(date +%c)] 서비스 종료 실패" >> $STOP_LOG
  fi
fi
