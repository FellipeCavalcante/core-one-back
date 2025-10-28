package com.coreone.back.modules.sector.domain;

import com.coreone.back.modules.subSector.domain.SubSector;
import com.coreone.back.modules.workstation.domain.Workstation;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "sector")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "sector", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubSector> subSectors = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "workstation_id", nullable = false)
    private Workstation workstation;
}
