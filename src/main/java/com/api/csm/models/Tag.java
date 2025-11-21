package com.api.csm.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tags")
@Getter
@Setter
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // serial
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    public Tag() {}

    public Tag(String name) {
        this.name = name;
    }

}
