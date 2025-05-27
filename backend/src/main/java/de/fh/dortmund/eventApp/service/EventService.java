package de.fh.dortmund.eventApp.service;

import de.fh.dortmund.eventApp.dto.Response;
import de.fh.dortmund.eventApp.dto.EventDTO;
import de.fh.dortmund.eventApp.entity.Event;
import de.fh.dortmund.eventApp.exception.CustomException;
import de.fh.dortmund.eventApp.repo.BookingRepository;
import de.fh.dortmund.eventApp.repo.EventRepository;
import de.fh.dortmund.eventApp.requestBody.EventBody;
import de.fh.dortmund.eventApp.utils.Utils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

@Service
public class EventService  {


    private final EventRepository eventRepository;
    private final BookingRepository bookingRepository;

    public EventService(EventRepository eventRepository, BookingRepository bookingRepository) {
        this.eventRepository = eventRepository;
        this.bookingRepository = bookingRepository;
    }


    public Response addNewEvent(EventBody eventBody) {
        Response response = new Response();

        try {
            Event savedEvent = convertAndSaveEventBodyToEvent(eventBody);
            EventDTO eventDTO = Utils.mapEventEntityToEventDTO(savedEvent);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setEvent(eventDTO);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error saving an event " + e.getMessage());
        }
        return response;
    }

    public Event convertAndSaveEventBodyToEvent(EventBody eventBody) {
        Event event = new Event();
        event.setStatus(eventBody.getStatus());
        event.setEventDate(eventBody.getEventDate());
        event.setEventPhoto(eventBody.getEventPhoto());
        event.setEventLocation(eventBody.getEventLocation());
        event.setMaxParticipant(eventBody.getMaxParticipant());
        event.setEventDescription(eventBody.getEventDescription());
        event.setEventName(eventBody.getEventName());
        return eventRepository.save(event);
    }

    public Response getAllEvents() {
        Response response = new Response();

        try {
            List<Event> eventList = eventRepository.findAll()
                    .stream()
                    .sorted((e1, e2) -> Math.toIntExact(e1.getId() - e2.getId())).toList();
            List<EventDTO> eventDTOList = Utils.mapEventListEntityToEventListDTO(eventList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setEventList(eventDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error saving an event " + e.getMessage());
        }
        return response;
    }

    public Response deleteEvent(Long eventId) {
        Response response = new Response();

        try {
            eventRepository.findById(eventId).orElseThrow(() -> new CustomException("Event Not Found"));
            eventRepository.deleteById(eventId);
            response.setStatusCode(200);
            response.setMessage("successful");

        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error saving event " + e.getMessage());
        }
        return response;
    }

    public Response updateEvent(Long eventID, MultipartFile photo, Event body) {
        Response response = new Response();

        try {
            Event event = eventRepository.findById(eventID).orElseThrow(() -> new CustomException("Event Not Found"));
            if (body.getEventDate() != null) {
                event.setEventDate(body.getEventDate());
            }
            if (body.getStatus() != null) event.setStatus(body.getStatus());
            if (body.getEventLocation() != null) event.setEventLocation(body.getEventLocation());
            if (body.getEventPhoto() != null) event.setEventPhoto(body.getEventPhoto());
            if(body.getMaxParticipant() != event.getMaxParticipant() && body.getMaxParticipant() > 0){
                event.setMaxParticipant(body.getMaxParticipant());
            }
            if(!photo.isEmpty()){
                Base64.getEncoder().encodeToString(photo.getBytes());
            }
            Event updatedEvent = eventRepository.save(event);
            EventDTO eventDTO = Utils.mapEventEntityToEventDTO(updatedEvent);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setEvent(eventDTO);

        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error saving an event " + e.getMessage());
        }
        return response;
    }

    public Response getEventById(Long eventID) {
        Response response = new Response();

        try {
            Event event = eventRepository.findById(eventID).orElseThrow(() -> new CustomException("Event Not Found"));
            EventDTO eventDTO = Utils.mapEventEntityToEventDTOPlusBookings(event);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setEvent(eventDTO);

        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error saving a event " + e.getMessage());
        }
        return response;
    }


    public Response getAllAvailableEvents() {
        Response response = new Response();

        try {
            List<Event> eventList = findAllAvailableEvents();
            List<EventDTO> eventDTOList = Utils.mapEventListEntityToEventListDTO(eventList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setEventList(eventDTOList);

        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error loading an Event " + e.getMessage());
        }
        return response;
    }

    private List<Event> findAllAvailableEvents() {
        return eventRepository.findAll().stream().filter(this::isEventAvailable).toList();
    }


    public boolean isEventAvailable(Event event){
        return
                event.getMaxParticipant() > event.getBookings().size() && LocalDate.now().isBefore(event.getEventDate());
     }




}
