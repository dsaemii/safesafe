package ch.stragiotti.safesave.Repos;

import ch.stragiotti.safesave.Model.Password;
import ch.stragiotti.safesave.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasswordRepository extends JpaRepository<Password, Integer> {
    List<Password> findByUser (User user);
}