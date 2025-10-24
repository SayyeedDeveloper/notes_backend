package sayyeed.com.notesbackend.entity.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sayyeed.com.notesbackend.enums.UserRoleEnum;

@Entity
@Table(name = "user_role")
@Setter
@Getter
public class UserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_id")
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, insertable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column
    private UserRoleEnum role;
}
