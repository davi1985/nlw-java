package br.com.sarahcode.events.controllers;

import br.com.sarahcode.events.dtos.ErrorMessage;
import br.com.sarahcode.events.dtos.SubscriptionResponse;
import br.com.sarahcode.events.exceptions.EventNotFoundException;
import br.com.sarahcode.events.exceptions.SubscriptionConflictException;
import br.com.sarahcode.events.exceptions.UserIndicatorNotFound;
import br.com.sarahcode.events.models.User;
import br.com.sarahcode.events.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscriptionController {
    @Autowired
    private SubscriptionService service;

    @PostMapping({"/subscription/{prettyName}", "/subscription/{prettyName}/{userId}"})
    public ResponseEntity<?> createSubscription(@PathVariable String prettyName, @RequestBody User subscriber, @PathVariable(required = false) Integer userId) {
        try {
            SubscriptionResponse subscription = service.createNewSubscription(prettyName, subscriber, userId);

            if (subscription != null) {
                return ResponseEntity.ok(subscription);
            }
        } catch (EventNotFoundException | UserIndicatorNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(e.getMessage()));
        } catch (SubscriptionConflictException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessage(e.getMessage()));
        }

        return ResponseEntity.badRequest().build();
    }
}
