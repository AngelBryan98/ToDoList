package com.angel.todolist.repository;

import com.angel.todolist.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    TaskRepository repo;

    @Test
    void saves_and_find() {

        Task t = new Task("Leer", "Leer un libro.");
        repo.save(t);

        assertThat(repo.findById(t.getId())).isPresent();
    }
}