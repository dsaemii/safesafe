package ch.stragiotti.safersafe;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository; // CRUD - create, read, update, delete
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByName (String name);
    Optional<User> findByToken (String token);
    /*    @Query("SELECT new ch.stragiotti.safersafe.User(u.user_id, u.name, u.password, u.token) from User u WHERE u.name = :username")
    User findByUsername(@Param("username") String username);*/
}
