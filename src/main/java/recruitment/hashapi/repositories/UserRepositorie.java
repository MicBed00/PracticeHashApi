package recruitment.hashapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import recruitment.hashapi.models.User;

@Repository
public interface UserRepositorie extends JpaRepository<User, Long> {
}
