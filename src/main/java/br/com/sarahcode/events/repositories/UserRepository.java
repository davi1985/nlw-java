package br.com.sarahcode.events.repositories;

import br.com.sarahcode.events.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email);
}
