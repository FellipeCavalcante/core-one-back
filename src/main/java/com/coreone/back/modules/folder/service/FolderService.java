package com.coreone.back.modules.folder.service;

import com.coreone.back.common.errors.NotFoundException;
import com.coreone.back.common.errors.UnauthorizedException;
import com.coreone.back.modules.enterprise.service.EnterpriseService;
import com.coreone.back.modules.folder.controller.dto.CreateFolderDTO;
import com.coreone.back.modules.folder.domain.Folder;
import com.coreone.back.modules.folder.repository.FolderRepository;
import com.coreone.back.modules.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FolderService {
    private final FolderRepository repository;
    private final EnterpriseService enterpriseService;

    public void create(User user, CreateFolderDTO request) {
        Folder folder = new Folder();
        folder.setName(request.name());
        folder.setDescription(request.description());

        if (request.enterpriseId() != null) {
            var enterprise = enterpriseService.findById(request.enterpriseId());
            folder.setEnterprise(enterprise);
        }

        folder.setUser(user);

        folder.setPublic(request.isPublic() != null ? request.isPublic() : false);

        repository.save(folder);
    }

    public void delete(User user, UUID id) {
        var found = findById(id);

        if (!found.getUser().equals(user)) {
            throw new UnauthorizedException("Cannot delete folder");
        }

        repository.deleteById(id);
    }

    public Folder findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Folder not found!"));
    }
}
