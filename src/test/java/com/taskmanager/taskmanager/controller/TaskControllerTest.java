package com.taskmanager.taskmanager.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.taskmanager.taskmanager.model.Status;
import com.taskmanager.taskmanager.model.Task;
import com.taskmanager.taskmanager.repository.TaskRepository;
import com.taskmanager.taskmanager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TaskControllerTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateTaskWeekday() {
        Task newTask = new Task("Test Task", Status.PENDING);
        newTask.setCreationDate(LocalDate.now());

        when(taskService.isWeekday(any(LocalDate.class))).thenReturn(true);
        when(taskRepository.save(any(Task.class))).thenReturn(newTask);

        ResponseEntity<Task> response = taskController.createTask(newTask);

        verify(taskService, times(1)).isWeekday(any(LocalDate.class));
        verify(taskRepository, times(1)).save(any(Task.class));
        assert response.getStatusCode() == HttpStatus.CREATED;
    }

    @Test
    public void testCreateTaskWeekend() {
        Task newTask = new Task("Test Task", Status.PENDING);
        newTask.setCreationDate(LocalDate.now());

        when(taskService.isWeekday(any(LocalDate.class))).thenReturn(false);

        ResponseEntity<Task> response = taskController.createTask(newTask);

        verify(taskService, times(1)).isWeekday(any(LocalDate.class));
        verify(taskRepository, never()).save(any(Task.class));
        assert response.getStatusCode() == HttpStatus.FORBIDDEN;
    }

    @Test
    public void testUpdateTaskPending() {
        Task existingTask = new Task("Existing Task", Status.PENDING);
        existingTask.setId(1L);

        Task updatedTask = new Task("Updated Task", Status.COMPLETED);
        updatedTask.setId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        ResponseEntity<Task> response = taskController.updateTask(1L, updatedTask);

        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
        assert response.getStatusCode() == HttpStatus.OK;
    }

    @Test
    public void testUpdateTaskCompleted() {
        Task existingTask = new Task("Existing Task", Status.COMPLETED);
        existingTask.setId(1L);

        Task updatedTask = new Task("Updated Task", Status.PENDING);
        updatedTask.setId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));

        ResponseEntity<Task> response = taskController.updateTask(1L, updatedTask);

        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, never()).save(any(Task.class));
        assert response.getStatusCode() == HttpStatus.FORBIDDEN;
    }

    @Test
    public void testUpdateTaskNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Task> response = taskController.updateTask(1L, new Task());

        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, never()).save(any(Task.class));
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }

    @Test
    public void testDeleteTaskPending() {
        Task existingTask = new Task("Existing Task", Status.PENDING);
        existingTask.setId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));

        ResponseEntity<Void> response = taskController.deleteTask(1L);

        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).delete(any(Task.class));
        assert response.getStatusCode() == HttpStatus.NO_CONTENT;
    }

    @Test
    public void testDeleteTaskCompleted() {
        Task existingTask = new Task("Existing Task", Status.COMPLETED);
        existingTask.setId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));

        ResponseEntity<Void> response = taskController.deleteTask(1L);

        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, never()).delete(any(Task.class));
        assert response.getStatusCode() == HttpStatus.FORBIDDEN;
    }

    @Test
    public void testDeleteTaskNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = taskController.deleteTask(1L);

        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, never()).delete(any(Task.class));
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }

    @Test
    public void testGetAllTasks() {
        // Mock de uma lista de tarefas
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task 1", Status.PENDING));
        mockTasks.add(new Task("Task 2", Status.COMPLETED));


        when(taskRepository.findAll()).thenReturn(mockTasks);


        List<Task> returnedTasks = taskController.getAllTasks();

        // Verificação dos resultados
        assert returnedTasks.size() == 2;
        assert returnedTasks.get(0).getTitle().equals("Task 1");
        assert returnedTasks.get(0).getStatus() == Status.PENDING;
        assert returnedTasks.get(1).getTitle().equals("Task 2");
        assert returnedTasks.get(1).getStatus() == Status.COMPLETED;


        verify(taskRepository, times(1)).findAll();
    }
}