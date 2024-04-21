package studio.startapps.pandemona.repositories;

import org.springframework.data.repository.CrudRepository;
import studio.startapps.pandemona.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {
}
