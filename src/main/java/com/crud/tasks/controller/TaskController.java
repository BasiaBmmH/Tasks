package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final DbService service;
    private final TaskMapper taskMapper;

    @GetMapping
    public ResponseEntity<List<TaskDto>> getTasks() {
        List<Task> tasks = (List<Task>) service.getAllTasks();
        return ResponseEntity.ok(taskMapper.mapToTaskDtoList(tasks));
    }


    @GetMapping(value = "{taskId}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long taskId) throws TaskNotFoundException {

        return ResponseEntity.ok(taskMapper.mapToTaskDto(service.getTask(taskId)));

    }

//    @DeleteMapping(value = ("{taskId}"))
//    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
//        return ResponseEntity.ok().build();
//    }

//    @PutMapping
//    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto) {
//        return ResponseEntity.ok(new TaskDto(1L, "Edited test title", "Test content"));
//    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTask(@RequestBody TaskDto taskDto) {
        Task task = taskMapper.mapToTask(taskDto);
        service.saveTask(task);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto) {
        Task task = taskMapper.mapToTask(taskDto);
        Task savedTask = service.saveTask(task);
        return ResponseEntity.ok(taskMapper.mapToTaskDto(savedTask));
    }

    @DeleteMapping(value = "{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        try {
            service.deleteTask(taskId);
            return ResponseEntity.ok().build();
        } catch (TaskNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
