package com.coreone.back.modules.support.domain;

import com.coreone.back.modules.enterprise.domain.Enterprise;
import com.coreone.back.modules.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "ticket_support")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketSupport {

    @Id
    @GeneratedValue
    private UUID id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String status;
    private String type;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;
}
