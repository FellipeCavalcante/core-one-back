package com.coreone.back.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "enterprise")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class
Enterprise {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @OneToMany(mappedBy = "enterprise")
    private List<Sector> sectors = new ArrayList<>();

    @OneToMany(mappedBy = "enterprise")
    private List<User> users = new ArrayList<>();

}
