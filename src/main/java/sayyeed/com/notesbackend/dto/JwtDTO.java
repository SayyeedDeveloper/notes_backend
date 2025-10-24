package sayyeed.com.notesbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sayyeed.com.notesbackend.enums.UserRoleEnum;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtDTO {

    public JwtDTO(String email, String code) {
        this.email = email;
        this.code = code;
    }

    private String email;
    private String code;
    private List<UserRoleEnum> roles;
}