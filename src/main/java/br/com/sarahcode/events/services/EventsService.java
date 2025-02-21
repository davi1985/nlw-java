package br.com.sarahcode.events.services;

import br.com.sarahcode.events.models.Event;
import br.com.sarahcode.events.repositories.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventsService {

    @Autowired
    private EventsRepository eventsRepository;


    public Event addNewEvent(Event event) {
        System.out.println(event.getTitle());
        event.setPrettyName(event.getTitle().toLowerCase().replaceAll(" ", "-"));

        return eventsRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return (List<Event>) eventsRepository.findAll();
    }

    public Event getByPrettyName(String prettyName) {
        return eventsRepository.findByPrettyName(prettyName);
    }
}
