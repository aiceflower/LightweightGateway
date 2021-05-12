package com.scnu.lwg.mqtt;

import com.scnu.lwg.util.CryptoUtils;
import com.scnu.lwg.util.JSONUtils;
import com.scnu.lwg.util.OkHttpCli;
import com.scnu.lwg.web.util.SignUtil;
import com.scnu.lwg.web.vo.HumitureMqttVo;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
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
	@Value("${mqtt.url}")
	private String url;
	@Resource
	OkHttpCli httpCli;

	@Resource
	CryptoUtils cryptoUtils;

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
					String msg = new String(mqttMessage.getPayload());
					if (StringUtils.isEmpty(msg)){
						log.error("receive error msg.");
						return;
					}

					HumitureMqttVo humiture = null;
					try {
						humiture = JSONUtils.jsonToObj(msg, HumitureMqttVo.class);
					} catch (IOException e) {
						log.error("parse message {} error.", msg);
					}

					log.info("From topic: {} , Receive msg {}", s, msg);
					HumitureMqttVo vo = new HumitureMqttVo();
					vo.setDeviceId(humiture.getDeviceId());
					vo.setHumidity(humiture.getHumidity());
					vo.setTemperature(humiture.getTemperature());
					vo.setCreateTime(new Date());
					vo.setSign(SignUtil.sign(humiture));
					String token = humiture.getToken();
					if (StringUtils.isEmpty(token)){
						log.error("invalid request. token is null.");
						return;
					}
					String str = JSONUtils.objToJson(vo);
					Map<String, String> param = new HashMap<>(1);
					param.put("data", cryptoUtils.wbSm4Enc(str));
					param.put("token", token);
					String resp = httpCli.doPost(url, param);
					System.out.println("request finished, the response is: " + resp);
					log.info("msg {} request finished, the response is {}", msg, resp);
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