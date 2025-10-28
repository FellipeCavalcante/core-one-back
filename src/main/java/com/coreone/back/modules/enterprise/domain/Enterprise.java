package com.coreone.back.modules.enterprise.domain;

import com.coreone.back.modules.folder.domain.Folder;
import com.coreone.back.modules.note.domain.Note;
import com.coreone.back.modules.project.domain.Project;
import com.coreone.back.modules.sector.domain.Sector;
import com.coreone.back.modules.task.domain.Task;
import com.coreone.back.modules.user.domain.User;
import com.coreone.back.modules.user.domain.UserEnterprise;
import com.coreone.back.modules.workstation.domain.Workstation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "enterprise")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Enterprise {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @OneToMany(mappedBy = "enterprise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Workstation> workstations = new ArrayList<>();

    @OneToMany(mappedBy = "enterprise")
    private List<Sector> sectors = new ArrayList<>();

    @OneToMany(mappedBy = "enterprise")
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "enterprise")
    private List<Project> projects = new ArrayList<>();

    @OneToMany(mappedBy = "enterprise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EnterpriseRequest> requests = new ArrayList<>();

    @OneToMany(mappedBy = "enterprise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Folder> folders = new ArrayList<>();

    @OneToMany(mappedBy = "enterprise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes = new ArrayList<>();

    @OneToMany(mappedBy = "enterprise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserEnterprise> members = new ArrayList<>();

    public List<User> getUsers() {
        return members.stream()
                .map(UserEnterprise::getUser)
                .collect(Collectors.toList());
    }
}
