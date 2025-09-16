package com.findjobs.back.service;

import com.findjobs.back.domain.Log;
import com.findjobs.back.domain.User;
import com.findjobs.back.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Log> findAll() {
        return repository.findAllWithUser();
    }
}
