package br.com.emiliosanches.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.emiliosanches.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {
  @Autowired
  private ITaskRepository taskRepository;

  @PostMapping
  public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
    var userId = request.getAttribute("userId");
    taskModel.setUserId((UUID) userId);

    LocalDateTime currentDate = LocalDateTime.now();

    if (currentDate.isAfter(taskModel.getStartsAt())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Não é permitido cadastrar tarefas com data de início anterior à atual");
    }

    if (currentDate.isAfter(taskModel.getEndsAt())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Não é permitido cadastrar tarefas com data de término anterior à atual");
    }

    if (taskModel.getEndsAt().isBefore(taskModel.getStartsAt())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Não é permitido cadastrar tarefas com data de início maior que a data de término");
    }

    TaskModel task = this.taskRepository.save(taskModel);
    return ResponseEntity.status(HttpStatus.OK).body(task);
  }

  @GetMapping
  public List<TaskModel> list(HttpServletRequest request) {
    return this.taskRepository.findByUserId((UUID) request.getAttribute("userId"));
  }

  @PutMapping("/{id}")
  public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request,
      @PathVariable("id") UUID taskId) {
    // could use `@PathVariable String id` (without invoking decorator with
    // parameter) if the name in path is the same as in method

    var task = this.taskRepository.findById(taskId).orElse(null);

    if (task == null) {
      return ResponseEntity.status(404).body("Tarefa não encontrada");
    }

    Utils.copyNonNullProperty(taskModel, task);

    var savedTask = this.taskRepository.save(task);

    return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
  }
}
