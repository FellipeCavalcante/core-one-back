package com.coreone.back.modules.note.service;

import com.coreone.back.modules.folder.service.FolderService;
import com.coreone.back.modules.note.controller.dto.CreateNoteRequestDTO;
import com.coreone.back.modules.note.domain.Note;
import com.coreone.back.modules.note.repository.NoteRepository;
import com.coreone.back.modules.user.domain.User;
import com.coreone.back.modules.workstation.service.WorkstationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository repository;
    private final WorkstationService workstationService;
    private final FolderService folderService;

    public void save(User user, CreateNoteRequestDTO request) {
        Note note = new Note();
        note.setUser(user);
        note.setTitle(request.title());
        note.setContent(request.content());
        note.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        if (request.workstationId() != null) {
            var workstation = workstationService.getWorkstationById(request.workstationId());
            note.setWorkstation(workstation);
        }

        if (request.folderId() != null) {
            var folder = folderService.findById(request.folderId());
            note.setFolder(folder);
        }

        note.setPublic(request.isPublic() != null ? request.isPublic() : false);

        repository.save(note);
    }
}
