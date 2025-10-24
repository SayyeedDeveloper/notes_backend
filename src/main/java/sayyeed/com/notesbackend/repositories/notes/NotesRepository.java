package sayyeed.com.notesbackend.repositories.notes;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sayyeed.com.notesbackend.entity.notes.NoteEntity;

import java.util.List;
import java.util.Optional;

public interface NotesRepository extends CrudRepository<NoteEntity, String> {
    @Query("from NoteEntity where userId = ?1")
    List<NoteEntity> getAllNotesByUserId(String userId);

    @Query("from NoteEntity where id = ?1")
    Optional<NoteEntity> getNoteById(String id);
}
