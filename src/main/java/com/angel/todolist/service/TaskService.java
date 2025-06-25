package com.angel.todolist.service;

import com.angel.todolist.model.Task;
import com.angel.todolist.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository repo;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    public Task save(Task t) {
        return repo.save(t);
    }
    public List<Task> findAll() {
        return repo.findAll();
    }

    public Optional<Task> findById(Long id) {
        return  repo.findById(id);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public boolean exists(long id) {
        return repo.existsById(id);
    }

}


