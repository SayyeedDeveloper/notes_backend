package sayyeed.com.notesbackend.entity.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password", columnDefinition = "TEXT")
    private String password;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "birthday")
    private LocalDate birthday;

    @OneToMany(mappedBy = "user")
    private List<UserRoleEntity> roleList;

    @Column
    private LocalDateTime createdDate;
}
