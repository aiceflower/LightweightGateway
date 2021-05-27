#!/bin/bash
#config
LWG_SERVER_PORT=9700
LWG_MQTT_BROKER=tcp://192.168.13.223:11883
LWG_MQTT_USERNAME=lwg
LWG_MQTT_PASSWORD=lwgscnu2021
LWG_MQTT_TOPICS="WifiDHT/+/DHT11,LWG/MQTT/TOPIC"
LWG_MQTT_URL=https://www.kinsanity.cn
LWG_MQTT_START=true

export LWG_SERVER_PORT
export LWG_MQTT_BROKER
export LWG_MQTT_USERNAME
export LWG_MQTT_PASSWORD
export LWG_MQTT_TOPICS
export LWG_MQTT_URL
export LWG_MQTT_START

#env
LOG_HOME=/home/pi/Desktop/lwg/logs
#这里可替换为你自己的执行程序，其他代码无需更改
MODULE_NAME=lwg-client
APP_NAME=${MODULE_NAME}.war
PACKAGE_HOME=/home/pi/Desktop/lwg/java/package

#使用说明，用来提示输入参数
usage() {
    echo "Usage: sh lwg.sh [start|monitor|stop|restart|status]"
    exit 1
}

#检查程序是否在运行
is_exist(){
  pid=`ps -ef|grep $APP_NAME|grep -v grep|awk '{print $2}' `
  #如果不存在返回1，存在返回0     
  if [ -z "${pid}" ]; then
   return 1
  else
    return 0
  fi
}

#启动方法
start(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is already running. pid=${pid} ."
  else
    nohup java -Xms128m -Xmx256m -jar $PACKAGE_HOME/$APP_NAME | /usr/bin/cronolog $LOG_HOME/$MODULE_NAME/$MODULE_NAME-%Y-%m-%d.out  &
  fi
}

#停止方法
stop(){
  is_exist
  if [ $? -eq "0" ]; then
    kill -9 $pid
  else
    echo "${APP_NAME} is not running"
  fi  
}

#输出运行状态
status(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is running. Pid is ${pid}"
  else
    echo "${APP_NAME} is NOT running."
  fi
}

#重启
restart(){
  stop
  start
}
#监控
monitor(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is running. Pid is ${pid}"
  else
    start
  fi
}

#根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
  "start")
    start
    ;;
  "monitor")
    monitor
    ;;
  "stop")
    stop
    ;;
  "status")
    status
    ;;
  "restart")
    restart
    ;;
  *)
    usage
    ;;
esac

