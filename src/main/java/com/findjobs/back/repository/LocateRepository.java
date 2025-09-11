package com.findjobs.back.repository;

import com.findjobs.back.domain.Locate;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface LocateRepository extends CrudRepository<Locate, UUID> {
}
