package com.scnu.lwg.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Kin
 * @description mqtt consumer
 * @email kinsanities@sina.com
 * @time 2021/5/1 10:01 下午
 */

@Component
@Slf4j
public class Consumer {

	/**
	 * mqtt server url
	 */
	@Value("${mqtt.broker}")
	private final String broker = "tcp://39.96.89.236:1883";
	/**
	 * mqtt topic
	 */
	private final String topic = "LWG/MQTT/TOPIC";
	/**
	 * mqtt client id
	 */
	private final String clientId = "MQTT_SUB_CLIENT";
	/**
	 * mqtt server flag.
	 */
	@Value("${mqtt.start}")
	private boolean start;

	@PostConstruct
	public void init() throws MqttException {
		if (start){
			MqttClient subClient = getMqttClient();
			subClient.setCallback(new MqttCallback() {
				@Override
				public void connectionLost(Throwable throwable) {
					try {
						//重联
						subClient.reconnect();
					} catch (MqttException e) {
						e.printStackTrace();
					}
					log.error("mqtt consumer Connect lost,do some thing to solve it");
				}

				@Override
				public void messageArrived(String s, MqttMessage mqttMessage) {
					//TODO 处理消息
					System.out.println("From topic: " + s);
					System.out.println("Message content: " + new String(mqttMessage.getPayload()));
				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
					System.out.println("deliveryComplete");
				}

			});
			subClient.subscribe(topic);
		}
	}

	/**
	 * get mqtt client
	 * @return
	 */
	private MqttClient getMqttClient() {
		try {
			MqttClient pubClient = new MqttClient(broker, clientId, new MemoryPersistence());
			MqttConnectOptions connectOptions = new MqttConnectOptions();
			connectOptions.setCleanSession(false);
			pubClient.connect(connectOptions);
			log.info("mqtt consumer Connecting to broker:{} success.", broker);
			return pubClient;
		} catch (MqttException e) {
			e.printStackTrace();
		}
		return null;
	}
}