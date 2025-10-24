package sayyeed.com.notesbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.notesbackend.dto.notes.NoteInfoDTO;
import sayyeed.com.notesbackend.dto.notes.NoteRequestDTO;
import sayyeed.com.notesbackend.dto.notes.NoteUpdateDTO;
import sayyeed.com.notesbackend.dto.user.UserResponseDTO;
import sayyeed.com.notesbackend.entity.notes.NoteEntity;
import sayyeed.com.notesbackend.entity.users.UserEntity;
import sayyeed.com.notesbackend.exceptions.AppBadException;
import sayyeed.com.notesbackend.repositories.notes.NotesRepository;
import sayyeed.com.notesbackend.service.user.UserService;
import sayyeed.com.notesbackend.utils.SpringSecurityUtil;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class NotesService {

    @Autowired
    private NotesRepository repository;

    @Autowired
    private UserService userService;

    public NoteInfoDTO create(NoteRequestDTO dto) {
        NoteEntity entity = new NoteEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setUserId(SpringSecurityUtil.currentProfileId());
        entity.setCreatedTime(LocalDateTime.now());
        repository.save(entity);
        return toDTO(entity);
    }

    public List<NoteInfoDTO> getAllNotes() {
        UserResponseDTO userResponseDTO = userService.getUserInfo();
        Iterable<NoteEntity> iterable = repository.getAllNotesByUserId(SpringSecurityUtil.currentProfileId());

        List<NoteInfoDTO> responseList = new LinkedList<>();
        iterable.forEach( map -> responseList.add(toDTO(map)));

        return responseList;
    }

    public NoteInfoDTO getNoteById(String noteId) {
        Optional<NoteEntity> noteEntityOptional = repository.getNoteByIdAndUserId(noteId, SpringSecurityUtil.currentProfileId());
        if (noteEntityOptional.isEmpty()) {
            throw new AppBadException("Note not found");
        }
        NoteEntity entity = noteEntityOptional.get();
        return toDTO(entity);
    }

    public NoteInfoDTO update(String id, NoteRequestDTO dto) {
        Optional<NoteEntity> noteEntityOptional = repository.getNoteByIdAndUserId(id, SpringSecurityUtil.currentProfileId());
        if (noteEntityOptional.isEmpty()) {
            throw new AppBadException("Note not found");
        }
        NoteEntity entity = noteEntityOptional.get();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        repository.save(entity);
        return toDTO(entity);
    }

    public String delete(String id) {
        int flag = repository.deleteByIdAndUserId(id, SpringSecurityUtil.currentProfileId());
        if (flag == 0) {
            throw new AppBadException("Note not found");
        }
        return "Successfully deleted";
    }

    private NoteInfoDTO toDTO(NoteEntity entity) {
        NoteInfoDTO infoDTO = new NoteInfoDTO();
        infoDTO.setId(entity.getId());
        infoDTO.setTitle(entity.getTitle());
        infoDTO.setDescription(entity.getDescription());
        return infoDTO;
    }

}
