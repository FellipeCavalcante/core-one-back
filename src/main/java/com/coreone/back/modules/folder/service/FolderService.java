package com.coreone.back.modules.folder.service;

import com.coreone.back.common.errors.NotFoundException;
import com.coreone.back.common.errors.UnauthorizedException;
import com.coreone.back.modules.enterprise.service.EnterpriseService;
import com.coreone.back.modules.folder.controller.dto.CreateFolderDTO;
import com.coreone.back.modules.folder.controller.dto.GetFolderResponseDTO;
import com.coreone.back.modules.folder.controller.dto.UpdateFolderDTO;
import com.coreone.back.modules.folder.domain.Folder;
import com.coreone.back.modules.folder.repository.FolderRepository;
import com.coreone.back.modules.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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

    public Page<GetFolderResponseDTO> getMyFolders(int page, int size, User user) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Folder> folders = repository.findAllByUser(user, pageable);

        return folders.map(folder -> new GetFolderResponseDTO(
                folder.getId(),
                folder.getName(),
                folder.getDescription(),
                folder.getCreatedAt()
        ));
    }

    public void update(User user, UUID id, UpdateFolderDTO request) {
        var found = findById(id);

        if (!found.getUser().equals(user)) {
            throw new UnauthorizedException("Cannot update folder");
        }

        if (request.isPublic() != null) {
            found.setPublic(request.isPublic());
        }
        if (request.name() != null) {
            found.setName(request.name());
        }
        if (request.description() != null) {
            found.setDescription(request.description());
        }

        found.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        repository.save(found);
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
