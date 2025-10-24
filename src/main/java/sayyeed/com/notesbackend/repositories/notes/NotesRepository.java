package sayyeed.com.notesbackend.repositories.notes;

import org.springframework.data.repository.CrudRepository;
import sayyeed.com.notesbackend.entity.notes.NoteEntity;

public interface NotesRepository extends CrudRepository<NoteEntity, String> {
}
