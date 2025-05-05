package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DbService {

    private final TaskRepository repository;

    public Iterable<Task> getAllTasks() {
        return repository.findAll();
    }

    public Optional<Task> getTaskById(Long taskId) {
        return repository.findById(taskId);
    }
}
