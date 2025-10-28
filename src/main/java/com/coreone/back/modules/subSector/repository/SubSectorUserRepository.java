package com.coreone.back.modules.subSector.repository;

import com.coreone.back.modules.subSector.domain.SubSectorUser;
import com.coreone.back.modules.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SubSectorUserRepository extends JpaRepository<SubSectorUser, UUID> {
    List<SubSectorUser> findByUser(User user);

    void removeSubSectorUserByUser(User user);
}
