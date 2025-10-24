package sayyeed.com.notesbackend.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sayyeed.com.notesbackend.dto.notes.NoteInfoDTO;
import sayyeed.com.notesbackend.dto.notes.NoteRequestDTO;
import sayyeed.com.notesbackend.dto.notes.NoteUpdateDTO;
import sayyeed.com.notesbackend.repositories.notes.NotesRepository;
import sayyeed.com.notesbackend.service.NotesService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
public class NoteController {

    @Autowired
    private NotesService service;

    @PostMapping("")
    public ResponseEntity<NoteInfoDTO> create(@RequestBody @Valid NoteRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("")
    public ResponseEntity<List<NoteInfoDTO>> getAllNotes() {
        return ResponseEntity.ok(service.getAllNotes());
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<NoteInfoDTO> getNoteById(@PathVariable(name = "noteId") String noteId) {
        return ResponseEntity.ok(service.getNoteById(noteId));
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<NoteInfoDTO> update(
            @RequestBody NoteRequestDTO dto,
            @PathVariable(name = "noteId") String noteId
    ) {
        return ResponseEntity.ok(service.update(noteId, dto));
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<String> delete(@PathVariable(name = "noteId") String noteId) {
        return ResponseEntity.ok(service.delete(noteId));
    }
}
