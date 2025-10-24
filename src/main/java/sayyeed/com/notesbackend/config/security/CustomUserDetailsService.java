package sayyeed.com.notesbackend.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sayyeed.com.notesbackend.entity.users.UserEntity;
import sayyeed.com.notesbackend.enums.UserRoleEnum;
import sayyeed.com.notesbackend.exceptions.AppBadException;
import sayyeed.com.notesbackend.repositories.user.UserRepository;
import sayyeed.com.notesbackend.repositories.user.UserRoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optional = userRepository.findByEmail(username);
        if (optional.isEmpty()) {
            throw new AppBadException("User name not found");
        }
        UserEntity user = optional.get();
        List<UserRoleEnum> roleList = userRoleRepository.getRolesByUserId(user.getId());

        return new CustomUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                roleList
        );
    }
}
