[![Build Status](https://raw.githubusercontent.com/aiceflower/assets/master/img/build-passing.png)](https://www.kinsanity.cn/view)

# Humiture LightweightGateway Demonstration Demo
  This project is a temperature and humidity display demo.

## architecture
  [![Build Status](https://raw.githubusercontent.com/aiceflower/assets/master/img/lwg/ht_arch.png)](https://www.kinsanity.cn/view)
## project structure
  -- lwg-client: temperature and humidity data collection client.
  
  -- lwg-server: temperature and humidity data collection server.
  
  -- html: temperature and humidity display page.
  
## env prepare
  - java8
  - mqtt server
  - mysql 
  - nginx

## run client
1. git clone https://github.com/aiceflower/LightweightGateway.git
2. modify shell/lwg-client.sh config
```shell
  #client port
  LWG_SERVER_PORT=9700
  #mqtt server url
  LWG_MQTT_BROKER=tcp://192.168.13.223:11883
  #mqtt username
  LWG_MQTT_USERNAME=lwg
  #mqtt password
  LWG_MQTT_PASSWORD=lwgscnu2021
  #mqtt topic
  LWG_MQTT_TOPICS="WifiDHT/+/DHT11,LWG/MQTT/TOPIC"
  #lwg-server ip:port
  LWG_MQTT_URL=https://www.kinsanity.cn

 
  #log path
  LOG_HOME=/home/pi/logs
  # war file path
  PACKAGE_HOME=/path/to/package
```
3. sh lwg-client.sh start


## run server
1. git clone https://github.com/aiceflower/LightweightGateway.git
2. modify shell/lwg-server.sh config
```shell
  LWG_SERVER_PORT=9900

  #mysql url user/pwd
  LWG_MYSQL_URL=jdbc:mysql://39.96.89.236:3306/lwg?characterEncoding=utf8
  LWG_MYSQL_USERNAME=lwgtest
  LWG_MYSQL_PASSWORD=!@#Lwgtest2021

  #web user/pwd
  LWG_USERS_APPID=lwg
  LWG_USERS_APPSECRET=lwgscnu2021

  #log path
  LOG_HOME=/home/pi/logs
  # war file path
  PACKAGE_HOME=/path/to/package
```
3. sh lwg-server.sh start

## config html
1. modify nginx.conf
```
  location /view {
      alias  /path/to/LightweightGateway/html;
      index  index.html index.htm;
  }
  location /lwg/ {
      proxy_pass  http://127.0.0.1:9900; 
      proxy_set_header Host $proxy_host; 
      proxy_set_header X-Real-IP $remote_addr;
      proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
  }
```
2. modify /path/to/LightweightGateway/html/js/apiUrl.js 
```
  let my_api_server_url = 'http://nginx_ip:nginx_port'
```
4. reboot nginx
5. visit http://nginx_ip:nginx_port/view

  
