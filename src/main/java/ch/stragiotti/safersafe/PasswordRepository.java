package ch.stragiotti.safersafe;

import org.springframework.data.repository.CrudRepository; // CRUD - create, read, update, delete
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasswordRepository extends CrudRepository<Password, Long> {
    List<Password> findByUser (User user);
}
