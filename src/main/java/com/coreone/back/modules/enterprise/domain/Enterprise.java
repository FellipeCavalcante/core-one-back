package com.coreone.back.modules.enterprise.domain;

import com.coreone.back.modules.sector.domain.Sector;
import com.coreone.back.modules.task.domain.Task;
import com.coreone.back.modules.user.domain.User;
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

    @OneToMany(mappedBy = "enterprise")
    private List<Task> tasks = new ArrayList<>();

}
