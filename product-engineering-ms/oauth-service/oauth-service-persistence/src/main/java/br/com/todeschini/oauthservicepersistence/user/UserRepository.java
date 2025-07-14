package br.com.todeschini.oauthservicepersistence.user;

import br.com.todeschini.libvalidationhandler.metadata.QueryService;
import br.com.todeschini.oauthservicepersistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@QueryService
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);
}
