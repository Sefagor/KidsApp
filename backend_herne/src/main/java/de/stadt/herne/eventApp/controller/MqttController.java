package de.stadt.herne.eventApp.controller;

import de.stadt.herne.eventApp.service.MqttService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mqtt")
public class MqttController {

    @Autowired
    private MqttService mqttService;

    @PostMapping("/publish")
    public String publish(@RequestParam String topic, @RequestParam String message) {
        try {
            mqttService.publish(topic, message);
            return "Message envoyé avec succès";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur : " + e.getMessage();
        }
    }
}

