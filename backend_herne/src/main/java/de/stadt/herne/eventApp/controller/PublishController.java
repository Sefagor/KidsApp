package de.stadt.herne.eventApp.controller;

import de.stadt.herne.eventApp.entity.EventBody;
import de.stadt.herne.eventApp.mqtt.MqttPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RestController
public class PublishController {

    private final MqttPublisher mqttPublisher;
    private final ObjectMapper objectMapper;

    public PublishController(MqttPublisher mqttPublisher, ObjectMapper objectMapper) {
        this.mqttPublisher = mqttPublisher;
        this.objectMapper = objectMapper;
    }

    @PostMapping(path = "/send", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> sendMessage(
            @RequestPart("event") String eventJson,
            @RequestPart("image") MultipartFile imageFile) throws IOException {

        EventBody eventBody = objectMapper.readValue(eventJson, EventBody.class);
        eventBody.setEventPhoto(Base64.getEncoder().encodeToString(imageFile.getBytes()));
        mqttPublisher.publish(objectMapper.writeValueAsString(eventBody));
        return ResponseEntity.ok("Message envoyé avec succès");
    }

}

