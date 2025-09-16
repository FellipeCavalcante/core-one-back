package com.coreone.back.mapper;

import com.coreone.back.domain.Task;
import com.coreone.back.domain.TaskMember;
import com.coreone.back.domain.TaskSubSector;
import com.coreone.back.dto.task.*;
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
            TaskMemberResponse dto = new TaskMemberResponse();
            dto.setId(member.getId());
            dto.setUserId(member.getUser().getId());
            dto.setUserName(member.getUser().getName());
            return dto;
        }).collect(Collectors.toList()));

        response.setSubSectors(task.getSubSectors().stream().map(subSector -> {
            TaskSubSectorResponse dto = new TaskSubSectorResponse();
            dto.setId(subSector.getId());
            dto.setSubSectorId(subSector.getSubSector().getId());
            dto.setSubSectorName(subSector.getSubSector().getName());
            return dto;
        }).collect(Collectors.toList()));

        return response;
    }
}
