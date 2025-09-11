package com.findjobs.back.domain;

import com.findjobs.back.domain.enums.JobLocal;
import com.findjobs.back.domain.enums.JobType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "job")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;
    private String description;

    @Column(name = "min_pay")
    private Double minPay;

    @Column(name = "max_pay")
    private Double maxPay;

    @Enumerated(EnumType.STRING)
    private JobType type;

    @Enumerated(EnumType.STRING)
    private JobLocal local;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne
    @JoinColumn(name = "id_locate")
    private Locate locate;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Proposal> proposals;
}
