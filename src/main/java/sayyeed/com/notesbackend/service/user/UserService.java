package sayyeed.com.notesbackend.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.notesbackend.dto.user.UserResponseDTO;
import sayyeed.com.notesbackend.dto.user.UserUpdateDTO;
import sayyeed.com.notesbackend.entity.users.UserEntity;
import sayyeed.com.notesbackend.exceptions.AppBadException;
import sayyeed.com.notesbackend.repositories.user.UserRepository;
import sayyeed.com.notesbackend.utils.SpringSecurityUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public UserResponseDTO updateUserInfo(UserUpdateDTO dto) {
        Optional<UserEntity> userOptional = repository.findByUserId(SpringSecurityUtil.currentProfileId());
        if (userOptional.isEmpty()) {
            throw new AppBadException("User not found");
        }
        UserEntity entity = userOptional.get();

        if (dto.getName() != null) {
            if (dto.getName().trim().isEmpty()){
                entity.setName("");
            }
            entity.setName(dto.getName());
        }

        if (dto.getOccupation() != null) {
            if (dto.getOccupation().trim().isEmpty()){
                entity.setOccupation("");
            }
            entity.setOccupation(dto.getOccupation());
        }

        if (dto.getBirthday() != null) {
            if (dto.getBirthday().trim().isEmpty()) {
                entity.setBirthday(null);
            } else {
                try {
                    entity.setBirthday(LocalDate.parse(dto.getBirthday(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                } catch (Exception e) {
                    throw new AppBadException("Invalid birthday format. Use dd/MM/yyyy format");
                }
            }
        }

        repository.save(entity);
        return toDTO(entity);
    }

    private UserResponseDTO toDTO(UserEntity entity) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setName(entity.getName());
        userResponseDTO.setEmail(entity.getEmail());
        userResponseDTO.setOccupation(entity.getOccupation());

        if (entity.getBirthday() != null) {
            userResponseDTO.setBirthday(entity.getBirthday().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } else {
            userResponseDTO.setBirthday("");
        }
        return userResponseDTO;
    }

}
