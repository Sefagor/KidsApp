package de.fh.dortmund.eventApp.controller;


import de.fh.dortmund.eventApp.dto.Response;
import de.fh.dortmund.eventApp.entity.Event;
import de.fh.dortmund.eventApp.repo.EventRepository;
import de.fh.dortmund.eventApp.requestBody.EmailContent;
import de.fh.dortmund.eventApp.requestBody.EventBody;
import de.fh.dortmund.eventApp.service.BookingService;
import de.fh.dortmund.eventApp.service.EmailService;
import de.fh.dortmund.eventApp.service.EventService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final BookingService bookingService;

    private final EventRepository eventRepository;

    private final EmailService emailService;

    public EventController(EventService eventService, BookingService bookingService, EventRepository eventRepository, EmailService emailService) {
        this.eventService = eventService;
        this.bookingService = bookingService;
        this.eventRepository = eventRepository;
        this.emailService = emailService;
    }


    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addNewEvent(@Valid @RequestBody EventBody eventBody,
                                                @RequestParam(value = "photo", required = false) MultipartFile photo
    ) {
        try {
            eventBody.setEventPhoto(Base64.getEncoder().encodeToString(photo.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Response response = eventService.addNewEvent(eventBody);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllEvents() {
        Response response = eventService.getAllEvents();
        emailService.testConnection();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @GetMapping("/event-by-id/{eventID}")
    public ResponseEntity<Response> getEventById(@PathVariable Long eventID) {
        Response response = eventService.getEventById(eventID);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all-available-events")
    public ResponseEntity<Response> getAvailableEvents() {
        Response response = eventService.getAllAvailableEvents();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @PutMapping("/update/{eventID}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateEvent(@PathVariable Long eventID,
                                               @RequestParam(value = "photo", required = false) MultipartFile photo,
                                               @RequestBody Event event

    ) {
        Response response = eventService.updateEvent(eventID, photo, event);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{eventID}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteEvent(@PathVariable Long eventID) {
        Response response = eventService.deleteEvent(eventID);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }


    @PreAuthorize("hasAuthority('ADMIN')")
@PostMapping("notify")
public  void sendEmailToEveryUser(@Valid EmailContent emailContent){
        emailService.sendToEveryone(emailContent);
}

}
