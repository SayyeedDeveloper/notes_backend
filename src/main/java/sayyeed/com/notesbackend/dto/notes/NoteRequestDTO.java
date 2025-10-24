package sayyeed.com.notesbackend.dto.notes;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class NoteRequestDTO {
    @NotBlank(message = "Title required")
    private String title;
    @NotBlank(message = "Description required")
    private String description;
}
