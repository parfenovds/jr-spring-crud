package com.javarush.controller;

import com.javarush.dto.TaskDto;
import com.javarush.entity.Task;
import com.javarush.mapper.TaskMapper;
import com.javarush.service.TaskService;
import java.util.List;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class TaskController {
  private final TaskService taskService;
  private final TaskMapper taskMapper;

  @GetMapping("/")
  public String tasks(Model model,
      @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
    List<Task> tasks = taskService.findAllPaging((page - 1) * limit, limit);
    model.addAttribute("current_page", page);
    model.addAttribute("tasks", tasks);
    int pageAmount = (int) Math.ceil(((double) taskService.getAllCount()) / limit);
    if (pageAmount > 1) {
      List<Integer> pageNumbers = IntStream.rangeClosed(1, pageAmount).boxed().toList();
      model.addAttribute("page_numbers", pageNumbers);
    }
    return "tasks";
  }

  @PostMapping("/{id}")
  public String update(Model model,
      @PathVariable Integer id,
      @RequestBody TaskDto taskDto) {
    Task task = taskService.update(id, taskDto);
    return tasks(model, 1, 10);
  }

  @PostMapping("/")
  public String save(Model model, @RequestBody TaskDto taskDto) {
    Task save = taskService.save(taskMapper.toEntity(taskDto));
    return tasks(model, 1, 10);
  }
  @DeleteMapping("/{id}")
  public String delete(Model model, @PathVariable Integer id) {
    taskService.deleteById(id);
    return tasks(model, 1, 10);
  }
}
