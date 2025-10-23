package com.coreone.back.domain.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "enterprise")
@Getter
@Setter
@Builder
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

    @OneToMany(mappedBy = "enterprise")
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "enterprise")
    private List<Project> projects = new ArrayList<>();

    @OneToMany(mappedBy = "enterprise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EnterpriseRequest> requests = new ArrayList<>();

}