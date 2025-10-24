package sayyeed.com.notesbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.notesbackend.dto.notes.NoteInfoDTO;
import sayyeed.com.notesbackend.dto.notes.NoteRequestDTO;
import sayyeed.com.notesbackend.entity.notes.NoteEntity;
import sayyeed.com.notesbackend.repositories.notes.NotesRepository;
import sayyeed.com.notesbackend.utils.SpringSecurityUtil;

import java.time.LocalDateTime;

@Service
public class NotesService {

    @Autowired
    private NotesRepository repository;

    public NoteInfoDTO create(NoteRequestDTO dto) {
        NoteEntity entity = new NoteEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setUserId(SpringSecurityUtil.currentProfileId());
        entity.setCreatedTime(LocalDateTime.now());
        repository.save(entity);
        return toDTO(entity);
    }

    private NoteInfoDTO toDTO(NoteEntity entity) {
        NoteInfoDTO infoDTO = new NoteInfoDTO();
        infoDTO.setTitle(entity.getTitle());
        infoDTO.setDescription(entity.getDescription());
        return infoDTO;
    }

}
