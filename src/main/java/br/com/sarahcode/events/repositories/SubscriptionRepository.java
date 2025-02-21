package br.com.sarahcode.events.repositories;

import br.com.sarahcode.events.models.Event;
import br.com.sarahcode.events.models.Subscription;
import br.com.sarahcode.events.models.User;
import org.springframework.data.repository.CrudRepository;

public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {
    Subscription findByEventAndSubscriber(Event event, User user);
}
