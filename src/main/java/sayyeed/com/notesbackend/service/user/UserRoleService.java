package sayyeed.com.notesbackend.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.notesbackend.entity.users.UserRoleEntity;
import sayyeed.com.notesbackend.enums.UserRoleEnum;
import sayyeed.com.notesbackend.repositories.user.UserRoleRepository;

import java.util.List;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository repository;

    public void create(String userId, UserRoleEnum roleEnum) {
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserId(userId);
        userRoleEntity.setRole(roleEnum);
        repository.save(userRoleEntity);
    }

    public List<UserRoleEnum> getRolesById(String userId) {
        return repository.getRolesByUserId(userId);
    }
}
