package com.scnu.lwg.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

/**
 * @author Kin
 * @description mqtt producer
 * @email kinsanities@sina.com
 * @time 2021/5/1 10:02 下午
 */

@Component
@Slf4j
public class Producer {
	@Value("${broker}")
	private static final String broker = "tcp://39.96.89.236:1883";
	private static final int qos = 1;
	private static final String topic = "LWG/MQTT/TOPIC";
	private static final String clientId = "MQTT_PUB_CLIENT";

	MqttClient pubClient;

	@PostConstruct
	public void init()throws MqttException, InterruptedException {
		if (pubClient == null){
			pubClient = getMqttClient();
		}
	}

	/**
	 * send message
	 * @param msg
	 */
	public void sendMsg(String msg){
		if (StringUtils.isEmpty(msg)){
			log.error("mqtt send msg error: msg is null.");
			return;
		}
		MqttMessage mqttMessage = new MqttMessage(msg.getBytes());
		//TODO 加密
		try {
			pubClient.publish(topic, mqttMessage);
			log.info("mqtt send msg:{} success.", msg);
		} catch (MqttException e) {
			e.printStackTrace();
			log.error("mqtt send msg error:" + e.getMessage());
		}
	}

	private static MqttClient getMqttClient() {
		try {
			MqttClient pubClient = new MqttClient(broker, clientId, new MemoryPersistence());
			MqttConnectOptions connectOptions = new MqttConnectOptions();
			connectOptions.setWill("lwt", "this is a will message".getBytes(), qos, false);
			connectOptions.setCleanSession(false);
			pubClient.connect(connectOptions);
			log.info("mqtt producer connect to {} success.", broker);
			return pubClient;
		} catch (MqttException e) {
			e.printStackTrace();
			log.error("mqtt producer connect to {} failure.", broker);
		}
		return null;
	}
}