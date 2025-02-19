package br.com.sarahcode.events.repositories;

import br.com.sarahcode.events.models.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventsRepository extends CrudRepository<Event, Integer> {
    Event findByPrettyName(String prettyName);
}
