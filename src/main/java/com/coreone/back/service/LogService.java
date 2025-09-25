package com.coreone.back.service;

import com.coreone.back.domain.Log;
import com.coreone.back.domain.User;
import com.coreone.back.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository repository;

    public void create(User user, String action, String entity, UUID entityId, String value) {
        Log log = new Log();
        log.setUser(user);
        log.setAction(action);
        log.setEntity(entity);
        log.setEntityId(entityId);
        log.setNewValue(value);

        repository.save(log);
    }

    public Page<Log> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAllWithUser(pageable);
    }

    public Log findById(UUID id) {
        return repository.findById(id).orElse(null);
    }
}
