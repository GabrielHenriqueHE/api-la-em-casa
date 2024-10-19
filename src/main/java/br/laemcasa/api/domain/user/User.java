package br.laemcasa.api.domain.user;

import br.laemcasa.api.domain.group.Group;
import br.laemcasa.api.domain.task.Task;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

@Entity
@Table(name = "TB_users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements Serializable, UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp(source = SourceType.DB)
    private Timestamp createdAt;

    @UpdateTimestamp(source = SourceType.DB)
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private Set<Group> ownedGroups = new HashSet<>();

    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
    private Set<Group> groups = new HashSet<>();

    @OneToMany(mappedBy = "assignedTo", fetch = FetchType.LAZY)
    private Set<Task> tasks = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN ) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }
}
