package sayyeed.com.notesbackend.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {

    @NotBlank(message = "Email required")
    private String email;

    @NotBlank(message = "Password required")
    private String password;

}
