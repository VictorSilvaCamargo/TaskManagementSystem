package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.model.Task;
import com.taskmanager.taskmanager.model.Status;
import com.taskmanager.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        return taskOptional.orElse(null);
    }

    public Task saveTask(Task task) {
        task.setStatus(Status.PENDING);
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task taskDetails) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task existingTask = optionalTask.get();

            existingTask.setStatus(taskDetails.getStatus());

            return taskRepository.save(existingTask);
        }
        return null;
    }


    public void deleteTask(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task taskToDelete = optionalTask.get();


            if (taskToDelete.getStatus() != Status.PENDING) {
                throw new RuntimeException("A tarefa só pode ser excluída se estiver pendente.");
            }

            taskRepository.delete(taskToDelete);
        }
    }


    private boolean isCreatedBeforeNDays(Task task, int days) {
        LocalDate creationDate = task.getCreationDate();
        LocalDate now = LocalDate.now();
        return creationDate.isBefore(now.minusDays(days));
    }


    public boolean isWeekday(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return !dayOfWeek.equals(DayOfWeek.SATURDAY) && !dayOfWeek.equals(DayOfWeek.SUNDAY);
    }
}
