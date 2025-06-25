package com.angel.todolist.service;

import com.angel.todolist.model.Task;
import com.angel.todolist.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    TaskRepository repo;

    @InjectMocks
    TaskService service;

    @Test
    void save_persists_and_return_task() {

        Task t = new Task("Titulo", "Descripcion");

        when(repo.save(t)).thenReturn(t);

        Task saved = service.save(t);

        assertThat(saved.getTitle()).isEqualTo("Titulo");
        verify(repo).save(t);

    }

    @Test
    void findAll_delegates_to_repository() {

        when(repo.findAll()).thenReturn(List.of(new Task("a", "b")));

        List<Task> result = service.findAll();

        assertThat(result).hasSize(1);
        verify(repo).findAll();
    }

    @Test
    void delete_calls_repository() {
        service.delete(10L);
        verify(repo).deleteById(10L);
    }

    @Test
    void exists_returns_true_when_repro_returns_true() {
        when(repo.existsById(1L)).thenReturn(true);
        assertThat(service.exists(1L)).isTrue();
    }

}
