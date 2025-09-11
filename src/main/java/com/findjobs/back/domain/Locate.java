package com.findjobs.back.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "locate")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Locate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int cep;
    private String state;
    private String city;
    private String street;
    private Integer number;
    private String reference;

    @OneToMany(mappedBy = "locate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Job> jobs;
}
