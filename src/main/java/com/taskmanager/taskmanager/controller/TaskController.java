package com.taskmanager.taskmanager.controller;

import com.taskmanager.taskmanager.model.Status;
import com.taskmanager.taskmanager.model.Task;
import com.taskmanager.taskmanager.repository.TaskRepository;
import com.taskmanager.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository taskRepository;
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskRepository taskRepository, TaskService taskService) {
        this.taskRepository = taskRepository;
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        if (!taskService.isWeekday(LocalDate.now())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        task.setStatus(Status.PENDING);
        task.setCreationDate(LocalDate.now());
        Task createdTask = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        Optional<Task> existingTaskOptional = taskRepository.findById(id);

        if (existingTaskOptional.isPresent()) {
            Task existingTask = existingTaskOptional.get();

            if (existingTask.getStatus() != Status.PENDING) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }

            existingTask.setStatus(updatedTask.getStatus());

            Task savedTask = taskRepository.save(existingTask);
            return ResponseEntity.ok(savedTask);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);

        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();

            if (task.getStatus() != Status.PENDING) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            taskRepository.delete(task);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
