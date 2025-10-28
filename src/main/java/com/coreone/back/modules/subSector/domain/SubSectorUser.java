package com.coreone.back.modules.subSector.domain;

import com.coreone.back.modules.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "sub_sector_user",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"sub_sector_id", "user_id"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubSectorUser {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "sub_sector_id", nullable = false)
    private SubSector subSector;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
