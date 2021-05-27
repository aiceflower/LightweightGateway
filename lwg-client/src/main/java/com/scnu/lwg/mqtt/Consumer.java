package com.scnu.lwg.mqtt;

import com.scnu.lwg.util.JSONUtils;
import com.scnu.lwg.util.OkHttpCli;
import com.scnu.lwg.entity.HumitureMqttVo;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
	private String broker;
	/**
	 * mqtt username
	 */
	@Value("${mqtt.username}")
	private String username;
	/**
	 * mqtt password
	 */
	@Value("${mqtt.password}")
	private String password;
	/**
	 * mqtt topic
	 */
	@Value("#{'${mqtt.topics}'.split(',')}")
	private String[] topics;
	/**
	 * mqtt client id
	 */
	private final String clientId = "MQTT_SUB_CLIENT";
	/**
	 * mqtt server flag.
	 */
	@Value("${mqtt.start}")
	private boolean start;
	@Value("${mqtt.url}")
	private String url;

	@Resource
	OkHttpCli httpCli;

	@PostConstruct
	public void init() throws MqttException {
		if (start){
			MqttClient subClient = getMqttClient();
			subClient.setCallback(new MqttCallback() {
				@Override
				public void connectionLost(Throwable throwable) {
					try {
						//reconnect
						subClient.reconnect();
					} catch (MqttException e) {
						e.printStackTrace();
					}
					log.error("mqtt consumer Connect lost,do some thing to solve it");
				}

				@Override
				public void messageArrived(String topic, MqttMessage mqttMessage) {
					String msg = new String(mqttMessage.getPayload());
					if (StringUtils.isEmpty(msg)){
						log.error("receive error msg.");
						return;
					}

					log.info("From topic: {} , Receive msg {}", topic, msg);
					String[] msgs = msg.split(" ");
					String deviceId = topic.split("/")[1];
					HumitureMqttVo humiture = new HumitureMqttVo();
					humiture.setDeviceId(deviceId);
					humiture.setHumidity(Float.valueOf(msgs[0]));
					humiture.setTemperature(Float.valueOf(msgs[1]));
					humiture.setCreateTime(new Date());

					String str = JSONUtils.objToJson(humiture);
					Map<String, String> param = new HashMap<>(1);
					param.put("data", str);
					String resp = httpCli.doPost(url + "/lwg/humiture/send", param);
					log.info("msg {} request finished, the response is {}", msg, resp);
				}
				@Override
				public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
					System.out.println("deliveryComplete");
				}

			});
			//subscribe topics
			subClient.subscribe(topics);
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

			//set username and password
			if (!StringUtils.isEmpty(username)){
				connectOptions.setUserName(username);
			}
			if (!StringUtils.isEmpty(password)){
				connectOptions.setPassword(password.toCharArray());
			}
			pubClient.connect(connectOptions);
			log.info("mqtt consumer Connecting to broker:{} success.", broker);
			return pubClient;
		} catch (MqttException e) {
			e.printStackTrace();
		}
		return null;
	}
}