package sayyeed.com.notesbackend.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.notesbackend.dto.user.UserResponseDTO;
import sayyeed.com.notesbackend.entity.users.UserEntity;
import sayyeed.com.notesbackend.exceptions.AppBadException;
import sayyeed.com.notesbackend.repositories.user.UserRepository;
import sayyeed.com.notesbackend.utils.SpringSecurityUtil;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public UserResponseDTO getUserInfo() {
        Optional<UserEntity> userOptional = repository.findByUserId(SpringSecurityUtil.currentProfileId());
        if (userOptional.isEmpty()) {
            throw new AppBadException("User not found");
        }
        return toDTO(userOptional.get());
    }

    private UserResponseDTO toDTO(UserEntity entity) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setName(entity.getName());
        userResponseDTO.setEmail(entity.getEmail());
        userResponseDTO.setOccupation(entity.getOccupation());
        userResponseDTO.setBirthday(entity.getBirthday());
        return userResponseDTO;
    }

}
