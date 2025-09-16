package com.coreone.back.mapper;

import com.coreone.back.domain.Task;
import com.coreone.back.domain.TaskMember;
import com.coreone.back.domain.TaskSubSector;
import com.coreone.back.dto.task.CreateTaskRequestDTO;
import com.coreone.back.dto.task.CreateTaskResponseDTO;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toTask(CreateTaskRequestDTO dto);

    default CreateTaskResponseDTO toCreateTaskResponseDTO(Task task, CreateTaskRequestDTO dto) {
        CreateTaskResponseDTO response = new CreateTaskResponseDTO();
        response.setId(task.getId());
        response.setCreatorId(dto.getCreatorId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());

        response.setMemberIds(task.getMembers().stream()
                .map(TaskMember::getUser)
                .map(user -> user.getId())
                .collect(Collectors.toList()));

        response.setSubSectorIds(task.getSubSectors().stream()
                .map(TaskSubSector::getSubSector)
                .map(subSector -> subSector.getId())
                .collect(Collectors.toList()));

        return response;
    }
}