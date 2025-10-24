package sayyeed.com.notesbackend.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sayyeed.com.notesbackend.dto.RegisterDTO;
import sayyeed.com.notesbackend.entity.users.UserEntity;
import sayyeed.com.notesbackend.exceptions.AppBadException;
import sayyeed.com.notesbackend.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public String register(RegisterDTO dto) {

        // validate email format
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!dto.getEmail().matches(emailRegex)) {
            throw new AppBadException("Invalid email format");
        }

        // checking user by email;
        Optional<UserEntity> userEntityOptional = repository.findByEmail(dto.getEmail());
        if (userEntityOptional.isPresent()) {
            throw new AppBadException("User already exists");
        }

        // create user
        UserEntity entity = new UserEntity();
        entity.setEmail(dto.getEmail());
        // setting hashed password
        entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        entity.setCreatedDate(LocalDateTime.now());
        repository.save(entity);
        return "Successfully registered";
    }

}
