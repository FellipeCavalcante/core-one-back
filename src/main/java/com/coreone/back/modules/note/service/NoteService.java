package com.coreone.back.modules.note.service;

import com.coreone.back.common.errors.UnauthorizedException;
import com.coreone.back.modules.enterprise.service.EnterpriseService;
import com.coreone.back.modules.folder.service.FolderService;
import com.coreone.back.modules.note.controller.dto.CreateNoteRequestDTO;
import com.coreone.back.modules.note.domain.Note;
import com.coreone.back.modules.note.repository.NoteRepository;
import com.coreone.back.modules.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository repository;
    private EnterpriseService enterpriseService;
    private FolderService folderService;

    public void save(User user, CreateNoteRequestDTO request) {
        if (request.enterpriseId() != null && !request.enterpriseId().equals(user.getEnterprise().getId())) {
            throw new UnauthorizedException("Cannot save note if you are not the same enterprise");
        }

        Note note = new Note();
        note.setUser(user);
        note.setTitle(request.title());
        note.setContent(request.content());
        note.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        if (request.enterpriseId() != null) {
            var enterprise = enterpriseService.findById(request.enterpriseId());
            note.setEnterprise(enterprise);
        }

        if (request.folderId() != null) {
            var folder = folderService.findById(request.folderId());
            note.setFolder(folder);
        }

        note.setPublic(request.isPublic() != null ? request.isPublic() : false);

        repository.save(note);
    }
}
