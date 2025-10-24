package sayyeed.com.notesbackend.entity.notes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sayyeed.com.notesbackend.entity.users.UserEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
@Setter
@Getter
public class NoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private String title;

    @Column
    private String description;

    @Column(name = "created_by")
    private String userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", insertable = false, updatable = false)
    private UserEntity user;

    @Column
    private LocalDateTime createdTime;
}
