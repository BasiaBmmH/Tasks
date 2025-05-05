package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final DbService service;
    private final TaskMapper taskMapper;

//    @Autowired
//    public TaskController(DbService service, TaskMapper taskMapper) {
//        this.service = service;
//        this.taskMapper = taskMapper;
//    }

    @GetMapping
    public List<TaskDto> getTasks() {
        List<Task> tasks = (List<Task>) service.getAllTasks();
        return taskMapper.mapToTaskDtoList(tasks);
    }

    @GetMapping(value = "{taskId}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long taskId) {
        return service.getTask(taskId)
                .map(task -> ResponseEntity.ok(taskMapper.mapToTaskDto(task)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



//    @GetMapping(value = "{taskID}")
//    public TaskDto getTask(@PathVariable Long taskID) {
//        return new TaskDto(1L, "test title", "test_content");
//    }

    @DeleteMapping
    public void deleteTask(Long taskId) {

    }

    @PutMapping
    public TaskDto updateTask(TaskDto taskDto) {
        return new TaskDto(1L, "Edited test title", "Test content");
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createTask(@RequestBody TaskDto taskDto) {
        Task task = taskMapper.mapToTask(taskDto);
        service.saveTask(task);
    }

    //zwraca liczbe zadan
    @GetMapping("/count")
    public int countTasks() {
        return getTasks().size();
    }

    //zwrca liste zadan
    @GetMapping("/search")
    public List<TaskDto> searchTasks() {
        return getTasks();
    }




}
