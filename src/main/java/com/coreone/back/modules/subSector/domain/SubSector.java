package com.coreone.back.modules.subSector.domain;

import com.coreone.back.modules.sector.domain.Sector;
import com.coreone.back.modules.task.domain.TaskSubSector;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "sub_sector")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubSector {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sector_id", nullable = false)
    private Sector sector;

    @OneToMany(mappedBy = "subSector", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubSectorUser> userLinks = new HashSet<>();

    @OneToMany(mappedBy = "subSector", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskSubSector> taskLinks = new ArrayList<>();
}
