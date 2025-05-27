package de.stadt.herne.eventApp.mqtt;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Component
public class MqttPublisher {

    private final String brokerUrl = "tcp://mosquitto:1883";
    private final String topic = "infos/servertest";
    private final String clientId = "serverB-publisher";

    public void publish(String message) {
        try {
            MqttClient client = new MqttClient(brokerUrl, clientId, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            client.connect(options);

            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(1);
            client.publish(topic, mqttMessage);

            client.disconnect();
            System.out.println("Message publié : " + message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publishJsonFile(Path path) throws IOException {
        String json = Files.readString(path);
        publish(json);  // utilise la méthode publish(String) déjà définie
    }

}

