package com.coreone.back.modules.task.mapper;

import com.coreone.back.modules.task.domain.Task;
import com.coreone.back.modules.task.dto.CreateTaskRequestDTO;
import com.coreone.back.modules.task.dto.CreateTaskResponseDTO;
import com.coreone.back.modules.task.dto.GetTaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "workstation", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "finishedAt", ignore = true)
    @Mapping(target = "subSectors", ignore = true)
    @Mapping(target = "members", ignore = true)
    Task toTask(CreateTaskRequestDTO dto);

    default CreateTaskResponseDTO toCreateTaskResponseDTO(Task task, CreateTaskRequestDTO dto) {
        CreateTaskResponseDTO response = new CreateTaskResponseDTO();
        response.setId(task.getId());
        response.setCreatorId(dto.getCreatorId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());

        response.setMemberIds(task.getMembers().stream()
                .map(member -> member.getUser().getId())
                .collect(Collectors.toList()));

        response.setSubSectorIds(task.getSubSectors().stream()
                .map(subSector -> subSector.getSubSector().getId())
                .collect(Collectors.toList()));

        return response;
    }

    default GetTaskResponse toGetTaskResponse(Task task) {
        GetTaskResponse response = new GetTaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());
        response.setFinishedAt(task.getFinishedAt());

        response.setMembers(task.getMembers().stream().map(member -> {
            var dto = new com.coreone.back.modules.task.dto.TaskMemberResponse();
            dto.setId(member.getId());
            dto.setUserId(member.getUser().getId());
            dto.setUserName(member.getUser().getName());
            return dto;
        }).collect(Collectors.toList()));

        response.setSubSectors(task.getSubSectors().stream().map(subSector -> {
            var dto = new com.coreone.back.modules.task.dto.TaskSubSectorResponse();
            dto.setId(subSector.getId());
            dto.setSubSectorId(subSector.getSubSector().getId());
            dto.setSubSectorName(subSector.getSubSector().getName());
            return dto;
        }).collect(Collectors.toList()));

        return response;
    }
}
