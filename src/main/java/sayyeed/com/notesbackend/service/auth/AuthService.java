package sayyeed.com.notesbackend.service.auth;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sayyeed.com.notesbackend.dto.auth.LoginDTO;
import sayyeed.com.notesbackend.dto.auth.LoginResponseDTO;
import sayyeed.com.notesbackend.dto.auth.RegisterDTO;
import sayyeed.com.notesbackend.entity.users.UserEntity;
import sayyeed.com.notesbackend.enums.UserRoleEnum;
import sayyeed.com.notesbackend.exceptions.AppBadException;
import sayyeed.com.notesbackend.repositories.user.UserRepository;
import sayyeed.com.notesbackend.service.user.UserRoleService;
import sayyeed.com.notesbackend.utils.JwtUtil;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRoleService userRoleService;

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

        // setting user role
        userRoleService.create(entity.getId(), UserRoleEnum.ROLE_USER);

        return "Successfully registered";
    }

    public LoginResponseDTO login(LoginDTO dto) {

        // checking user
        Optional<UserEntity> userEntityOptional = repository.findByEmail(dto.getEmail());
        if (userEntityOptional.isEmpty()) {
            throw new AppBadException("Email or password wrong");
        }

        UserEntity entity = userEntityOptional.get();

        // checking password
        boolean flag = bCryptPasswordEncoder.matches(dto.getPassword(), entity.getPassword());

        if (!flag) {
            throw new AppBadException("Email or password wrong");
        }

        LoginResponseDTO responseDTO = new LoginResponseDTO();
        responseDTO.setToken(JwtUtil.encode(entity.getEmail(), userRoleService.getRolesById(entity.getId())));
        return responseDTO;
    }

}
