package com.coreone.back.modules.project.domain;

import com.coreone.back.modules.subSector.domain.SubSector;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "project_sub_sector")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSubSector {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "sub_sector_id")
    private SubSector subSector;
}
