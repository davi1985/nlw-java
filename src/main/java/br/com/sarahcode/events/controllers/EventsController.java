package br.com.sarahcode.events.controllers;

import br.com.sarahcode.events.models.Event;
import br.com.sarahcode.events.services.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventsController {

    @Autowired
    private EventsService eventsService;

    @PostMapping("/events")
    public Event addNewEvent(@RequestBody Event newEvent) {
        return eventsService.addNewEvent(newEvent);
    }

    @GetMapping("/events")
    public List<Event> getAllEvents() {
        return eventsService.getAllEvents();
    }


    @GetMapping("/events/{prettyName}")
    public ResponseEntity<Event> getEventByPrettyName(@PathVariable String prettyName) {
        Event event = eventsService.getByPrettyName(prettyName);

        if (event != null) {
            return ResponseEntity.ok(event);
        }

        return ResponseEntity.notFound().build();
    }
}
