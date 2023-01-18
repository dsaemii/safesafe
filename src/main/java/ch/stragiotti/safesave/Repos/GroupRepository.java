package ch.stragiotti.safesave.Repos;

import ch.stragiotti.safesave.Model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    Group findByName (String name);
}