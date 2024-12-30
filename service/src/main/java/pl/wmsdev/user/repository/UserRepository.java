package pl.wmsdev.user.repository;

import org.springframework.data.repository.CrudRepository;
import pl.wmsdev.user.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findUserByUsername(String username);
}
