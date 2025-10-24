package sayyeed.com.notesbackend.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.PrimitiveIterator;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDTO {
    private String name;
    private String email;
    private String occupation;
    private LocalDate birthday;
}
