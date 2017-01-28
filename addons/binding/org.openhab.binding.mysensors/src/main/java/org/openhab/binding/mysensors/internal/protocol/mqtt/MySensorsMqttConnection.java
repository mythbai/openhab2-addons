package org.openhab.binding.mysensors.internal.protocol.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.openhab.binding.mysensors.config.MySensorsBridgeConfiguration;
import org.openhab.binding.mysensors.internal.event.MySensorsEventType;
import org.openhab.binding.mysensors.internal.event.MySensorsStatusUpdateEvent;
import org.openhab.binding.mysensors.internal.handler.MySensorsBridgeHandler;
import org.openhab.binding.mysensors.internal.protocol.MySensorsBridgeConnection;
import org.openhab.binding.mysensors.internal.protocol.message.MySensorsMessage;
import org.openhab.binding.mysensors.internal.protocol.message.MySensorsMessageParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySensorsMqttConnection extends MySensorsBridgeConnection {

	private Logger logger = LoggerFactory.getLogger(MySensorsMqttConnection.class);	
	
	// Configuration from thing file
    private MySensorsBridgeConfiguration myConfiguration = null;
	
	private MqttClient mqttClient = null;
	
	private MqttConnectOptions options = null;
	
	private MySensorsBridgeHandler bridgeHandler = null;
	private String url = null;
	private String topic = null;
	
	public MySensorsMqttConnection(MySensorsBridgeHandler bridgeHandler, String url, String topic) {
		super(bridgeHandler);
		this.url = url;
		this.topic = topic;
		myConfiguration = bridgeHandler.getBridgeConfiguration();
		
		options = new MqttConnectOptions();
		//Setzen einer Persistent Session
		options.setCleanSession(false);
		
	}

	@Override
	protected boolean _connect() {
		try {
			mqttClient = new MqttClient(url, "openhab-MySensors");
			
			if(myConfiguration.username.length()>0) {
				options.setUserName(myConfiguration.username);
			}
			if(myConfiguration.password.length()>0) {
				options.setPassword(myConfiguration.password.toCharArray());
			}
			
			mqttClient.connect(options);
			logger.debug("Connection to MQTT broker: {} established", url);
			
			mqttClient.setCallback(new MyMqttCallback());
			
			
			logger.debug("Subscribing to topic: {}", myConfiguration.topic);
			
			mqttClient.subscribe(myConfiguration.topic);
			
			addEventListener(this);
				
		} catch (MqttException e) {
			logger.error("Error in initialization of the MqttClient: + {}", e.toString());
		}
		return true;
	}

	@Override
	protected void _disconnect() {
		// TODO Auto-generated method stub
		
	}
	
	public void sendMessage(MySensorsMessage msg) {
		String newTopic = "mygateway1-in/" + 
						msg.getNodeId() + "/" + 
						msg.getChildId() + "/" +
						msg.getMsgType() + "/" +
						msg.getAck() + "/" + 
						msg.getSubType();
		
		MqttMessage newMessage = new MqttMessage(msg.getMsg().getBytes());
		
		try {
			mqttClient.publish(newTopic, newMessage);
		} catch (MqttPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
						
	}
	
	
	
	private class MyMqttCallback implements MqttCallback {
		@Override
		public void messageArrived(String topic, MqttMessage message) throws Exception {
			logger.debug("topic: {}, message: {}", topic, message);
			if(topic.contains(myConfiguration.topic)){
				MySensorsMessage msg = MySensorsMessageParser.parseMQTT(topic, message.toString());
				if (msg != null) {
                    MySensorsStatusUpdateEvent event = new MySensorsStatusUpdateEvent(
                            MySensorsEventType.INCOMING_MESSAGE, msg);
                    broadCastEvent(event);
				}
			}
		}
		
		@Override
		public void deliveryComplete(IMqttDeliveryToken arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void connectionLost(Throwable arg0) {
			// TODO Auto-generated method stub
			
		}
	}
}
