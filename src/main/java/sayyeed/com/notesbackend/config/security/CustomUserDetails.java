package sayyeed.com.notesbackend.config.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sayyeed.com.notesbackend.enums.UserRoleEnum;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    @Getter
    private final String id;
    private final String username;
    private final String password;
    private final List<SimpleGrantedAuthority> roles;

    public CustomUserDetails(String id, String username, String password, List<UserRoleEnum> roleList) {
        this.id = id;
        this.username = username;
        this.password = password;
        List<SimpleGrantedAuthority> roles = new LinkedList<>();
        System.out.println("Role list: " + roleList);
        roleList.forEach( role -> {
            roles.add(new SimpleGrantedAuthority(role.name()));
        });
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
