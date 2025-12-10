package com.api.csm.models;

import com.api.csm.utils.Auditable;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class User extends Auditable {
    @Id
    @GeneratedValue
    private UUID id;
    private String fullname;
    @Column(unique = true, nullable = false, length = 100)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )

    @JsonManagedReference
    private List<Role> roles;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    @Transient
    private boolean plainPassword = false;
}
