package com.javarush.service;

import com.javarush.dto.TaskDto;
import com.javarush.entity.Task;
import com.javarush.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {
  private final TaskRepository taskRepository;

  public TaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  @Transactional(readOnly = true)
  public Task findById(Integer id) {
    return taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("not found"));
  }

  @Transactional
  public void deleteById(Integer id) {
    Task found = findById(id);
    taskRepository.deleteById(found.getId());
  }

  @Transactional
  public Task update(Integer id, TaskDto dto) {
    Task found = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("not found"));
    Optional.ofNullable(dto.getStatus()).ifPresent(found::setStatus);
    Optional.ofNullable(dto.getDescription()).ifPresent(found::setDescription);
    return taskRepository.update(found);
  }

  @Transactional
  public Task save(Task entity) {
    return taskRepository.save(entity);
  }

  @Transactional(readOnly = true)
  public List<Task> findAll() {
    return taskRepository.findAll();
  }

  @Transactional(readOnly = true)
  public List<Task> findAllPaging(Integer offset, Integer limit) {
    return taskRepository.findAllPaging(offset, limit);
  }

  @Transactional(readOnly = true)
  public Integer getAllCount() {
    return taskRepository.getAllCount();
  }
}
