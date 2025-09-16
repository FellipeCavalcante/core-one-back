package com.findjobs.back.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskSubSector> subSectors = new HashSet<>();

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskMember> members = new HashSet<>();

    public void addMember(TaskMember member) {
        members.add(member);
        member.setTask(this);
    }

    public void removeMember(TaskMember member) {
        members.remove(member);
        member.setTask(null);
    }

    public void addSubSector(TaskSubSector subSector) {
        subSectors.add(subSector);
        subSector.setTask(this);
    }

    public void removeSubSector(TaskSubSector subSector) {
        subSectors.remove(subSector);
        subSector.setTask(null);
    }
}
