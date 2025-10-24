package sayyeed.com.notesbackend.repositories.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sayyeed.com.notesbackend.entity.users.UserRoleEntity;
import sayyeed.com.notesbackend.enums.UserRoleEnum;

import java.util.List;

public interface UserRoleRepository extends CrudRepository<UserRoleEntity, String> {
    @Query("select role from UserRoleEntity where userId =?1")
    List<UserRoleEnum> getRolesByUserId(String userId);
}
