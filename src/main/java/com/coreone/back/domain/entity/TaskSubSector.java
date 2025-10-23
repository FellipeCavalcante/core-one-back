package com.coreone.back.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "task_sub_sector")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskSubSector {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "sub_sector_id")
    private SubSector subSector;
}
