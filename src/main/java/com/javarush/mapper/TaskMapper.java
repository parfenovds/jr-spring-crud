package com.javarush.mapper;

import com.javarush.dto.TaskDto;
import com.javarush.entity.Task;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
  public Task toEntity(TaskDto dto) {
    Task task = new Task();
    Optional.ofNullable(dto.getId()).ifPresent(task::setId);
    Optional.ofNullable(dto.getDescription()).ifPresent(task::setDescription);
    Optional.ofNullable(dto.getStatus()).ifPresent(task::setStatus);
    return task;
  }

  public TaskDto toDto(Task entity) {
    TaskDto taskDto = new TaskDto();
    Optional.ofNullable(entity.getId()).ifPresent(taskDto::setId);
    Optional.ofNullable(entity.getDescription()).ifPresent(taskDto::setDescription);
    Optional.ofNullable(entity.getStatus()).ifPresent(taskDto::setStatus);
    return taskDto;
  }
}
