package de.fh.dortmund.eventApp.mqtt;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.fh.dortmund.eventApp.entity.Event;
import de.fh.dortmund.eventApp.repo.EventRepository;
import de.fh.dortmund.eventApp.requestBody.EventBody;
import de.fh.dortmund.eventApp.service.EventService;
import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class MqttSubscriber {


    private final ObjectMapper objectMapper = new ObjectMapper();

    private final EventRepository eventRepository;
    private final EventService eventService;
    private final String brokerUrl = "tcp://mosquitto:1883";
    private final String topic = "infos/servertest";
    private final String clientId = "serverA-subscriber";

    public MqttSubscriber(EventService eventService, EventRepository eventRepository) {
        this.eventService = eventService;
        this.eventRepository = eventRepository;
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @PostConstruct
    public void subscribe() {
        for (int i = 0; i < 5; i++) {
            try {
                MqttClient client = new MqttClient(brokerUrl, clientId, new MemoryPersistence());
                MqttConnectOptions options = new MqttConnectOptions();
                options.setCleanSession(true);
                client.connect(options);

                client.subscribe(topic, (t, msg) -> {
                    String payload = new String(msg.getPayload());
                    System.out.println("Message reçu sur le topic " + t + ": " + payload);

                    try {
                        EventBody eventBody = objectMapper.readValue(payload, EventBody.class);
                        eventService.convertAndSaveEventBodyToEvent(eventBody);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                System.out.println("Serveur A abonné au topic : " + topic);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }
}

