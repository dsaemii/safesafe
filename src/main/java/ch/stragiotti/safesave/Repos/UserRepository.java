package ch.stragiotti.safesave.Repos;

import ch.stragiotti.safesave.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByName (String name);
    Optional<User> findByToken (String token);
}