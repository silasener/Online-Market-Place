package tr.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tr.com.model.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserById(UUID id);

    Optional<User> findUserByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username = :username AND u.id <> :id") // another record check
    Optional<User> findUserByUsernameAndIdNot(@Param("username") String username, @Param("id") UUID id);

}
