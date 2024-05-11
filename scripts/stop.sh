#!/bin/bash

ROOT_PATH="/home/ec2-user/spring"
JAR="$ROOT_PATH/be-3rd-project/be-3rd-project/build/libs/be-3rd-project-0.0.1-SNAPSHOT.jar" # 내 ec2 파일 위치
STOP_LOG="$ROOT_PATH/stop.log"
SERVICE_PID=$(pgrep -f $JAR) # 실행중인 Spring 서버의 PID

if [ -z "$SERVICE_PID" ]; then
  echo "서비스 NotFound" >> $STOP_LOG
else
  echo "서비스 종료 " >> $STOP_LOG
  kill "$SERVICE_PID"
  # kill -9 $SERVICE_PID # 강제 종료를 하고 싶다면 이 명령어 사용
fi