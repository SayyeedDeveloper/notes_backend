package sayyeed.com.notesbackend.repositories.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sayyeed.com.notesbackend.entity.users.UserEntity;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, String> {
    @Query("from UserEntity where email = ?1")
    Optional<UserEntity> findByEmail(String email);

    @Query("from UserEntity where id = ?1")
    Optional<UserEntity> findByUserId(String userId);
}
