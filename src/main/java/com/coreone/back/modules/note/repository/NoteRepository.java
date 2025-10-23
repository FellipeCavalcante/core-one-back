package com.coreone.back.modules.note.repository;

import com.coreone.back.modules.note.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NoteRepository extends JpaRepository<Note, UUID> {
}
