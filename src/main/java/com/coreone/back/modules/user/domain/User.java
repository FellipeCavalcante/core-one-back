package com.coreone.back.modules.user.domain;

import com.coreone.back.modules.enterprise.domain.Enterprise;
import com.coreone.back.modules.folder.domain.Folder;
import com.coreone.back.modules.note.domain.Note;
import com.coreone.back.modules.payment.domain.CreditCard;
import com.coreone.back.modules.payment.domain.Payment;
import com.coreone.back.modules.plan.domain.Plan;
import com.coreone.back.modules.subSector.domain.SubSector;
import com.coreone.back.modules.task.domain.Task;
import com.coreone.back.modules.task.domain.TaskMember;
import com.coreone.back.modules.user.domain.enums.UserType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private UserType type;

    @ManyToOne
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    @ManyToOne
    @JoinColumn(name = "sub_sector_id")
    private SubSector subSector;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserPlan> userPlans = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CreditCard> creditCards = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskMember> taskMemberships = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Folder> folders = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes = new ArrayList<>();

    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Timestamp(System.currentTimeMillis());
    }

    public Plan getCurrentPlan() {
        return userPlans.stream()
                .filter(userPlan -> Boolean.TRUE.equals(userPlan.getIsCurrent()))
                .filter(userPlan -> "ACTIVE".equals(userPlan.getStatus()))
                .map(UserPlan::getPlan)
                .findFirst()
                .orElse(null);
    }


    public UserPlan getCurrentUserPlan() {
        return userPlans.stream()
                .filter(userPlan -> Boolean.TRUE.equals(userPlan.getIsCurrent()))
                .filter(userPlan -> "ACTIVE".equals(userPlan.getStatus()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Add new plan for user
     * remove plan if necessaire
     */
    public void addPlan(Plan plan, String status) {
        userPlans.stream()
                .filter(userPlan -> Boolean.TRUE.equals(userPlan.getIsCurrent()))
                .forEach(userPlan -> {
                    userPlan.setIsCurrent(false);
                    userPlan.setEndDate(LocalDateTime.now());
                });

        // create new plan
        UserPlan newUserPlan = new UserPlan();
        newUserPlan.setUser(this);
        newUserPlan.setPlan(plan);
        newUserPlan.setStartDate(LocalDateTime.now());
        newUserPlan.setStatus(status != null ? status : "ACTIVE");
        newUserPlan.setIsCurrent(true);
        newUserPlan.setCreatedAt(LocalDateTime.now());

        userPlans.add(newUserPlan);
    }

    public void updateCurrentPlanStatus(String status) {
        UserPlan current = getCurrentUserPlan();
        if (current != null) {
            current.setStatus(status);
            if ("EXPIRED".equals(status) || "CANCELLED".equals(status)) {
                current.setIsCurrent(false);
                current.setEndDate(LocalDateTime.now());
            }
        }
    }

    /**
     * Verify user plan
     */
    public boolean hasActivePlan() {
        return getCurrentPlan() != null;
    }

    public List<UserPlan> getPlanHistory() {
        return userPlans.stream()
                .sorted((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()))
                .collect(Collectors.toList());
    }


    public List<UserPlan> getActivePlans() {
        return userPlans.stream()
                .filter(userPlan -> "ACTIVE".equals(userPlan.getStatus()))
                .collect(Collectors.toList());
    }


    public Set<Task> getTasks() {
        return taskMemberships.stream()
                .map(TaskMember::getTask)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority("ROLE_" + this.type.name()));
    }

    @Override
    public String getUsername() { return this.email; }
    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}