package com.api.csm.models;

import com.api.csm.user.RoleEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE roles SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class Role extends Auditable {
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    private String description;

    @ManyToMany(mappedBy = "roles")
    @JsonManagedReference
    private List<User> users;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;
}
