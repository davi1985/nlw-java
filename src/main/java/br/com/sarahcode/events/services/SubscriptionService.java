package br.com.sarahcode.events.services;

import br.com.sarahcode.events.dtos.SubscriptionResponse;
import br.com.sarahcode.events.exceptions.EventNotFoundException;
import br.com.sarahcode.events.exceptions.SubscriptionConflictException;
import br.com.sarahcode.events.exceptions.UserIndicatorNotFound;
import br.com.sarahcode.events.models.Event;
import br.com.sarahcode.events.models.Subscription;
import br.com.sarahcode.events.models.User;
import br.com.sarahcode.events.repositories.EventsRepository;
import br.com.sarahcode.events.repositories.SubscriptionRepository;
import br.com.sarahcode.events.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    @Autowired
    private EventsRepository eventsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public SubscriptionResponse createNewSubscription(String eventName, User user, Integer userId) {
        Event event = eventsRepository.findByPrettyName(eventName);
        if (event == null) {
            throw new EventNotFoundException("Event: " + eventName + " not found");
        }


        User userFound = userRepository.findByEmail(user.getEmail());
        if (userFound == null) {
            userFound = userRepository.save(user);
        }
        User indicator = null;
        if (userId != null) {
            indicator = userRepository.findById(userId).orElse(null);

            if (indicator == null) {
                throw new UserIndicatorNotFound("Indicator user not found");
            }
        }

        Subscription subscription = new Subscription();
        subscription.setEvent(event);
        subscription.setSubscriber(userFound);
        subscription.setIndication(indicator);

        Subscription subscriptionFound = subscriptionRepository.findByEventAndSubscriber(event, userFound);
        if (subscriptionFound != null) {
            throw new SubscriptionConflictException("User " + userFound.getName() + " already subscriber in event " + event.getTitle());
        }

        Subscription subscriptionSaved = subscriptionRepository.save(subscription);

        var subscriberNumber = subscriptionSaved.getSubscriptionNumber();
        var designation = "http://codercraft.com/subscription/" + subscriptionSaved.getEvent().getPrettyName() + "/" + subscriptionSaved.getSubscriber().getId();

        return new SubscriptionResponse(subscriberNumber, designation);
    }
}












