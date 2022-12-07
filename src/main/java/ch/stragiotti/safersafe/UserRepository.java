package ch.stragiotti.safersafe;

import org.springframework.data.repository.CrudRepository; // CRUD - create, read, update, delete
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
