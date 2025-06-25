package com.angel.todolist.controller;

import com.angel.todolist.model.Task;
import com.angel.todolist.service.TaskService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;


@Component
public class ConsoleUI implements ApplicationRunner {

    private final TaskService service;

    public ConsoleUI(TaskService service) {
        this.service = service;
    }


    @Override
    public void run(ApplicationArguments args) {

        var console = new Scanner(System.in);
        var salir = false;

        while (!salir) {
            System.out.println("=== TO-DO LIST ===");
            listar();
            System.out.println("""
                    ------------------
                    1) Añadir tarea
                    2) Marcar/Desmarcar tarea
                    3) Eliminar tarea
                    4) Salir
                    """);
            System.out.println("Elige una opcion: ");
            int entrada = 0;

            try {
                entrada = Integer.parseInt(console.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Numero ingresado invalido.");
            }
            
            switch (entrada) {
                case 1 -> crear(console);
                case 2 -> toggle(console);
                case 3 -> borrar(console);
                case 4 -> salir = true;
                default -> System.out.println("Opción inexistente.");
            }
        }
        System.out.println("¡Hasta luego!");
    }

    private void listar() {
        List<Task> tareas = service.findAll();
        if (tareas.isEmpty()) {
            System.out.println("No hay tareas registradas.");
            return;
        }

        for (Task t : tareas) {
            System.out.printf("%d) [%s] %s%n",
                    t.getId(),
                    t.isCompleted() ? "*" : " ",
                    t.getTitle());
        }
    }

    private void crear(Scanner console) {
        System.out.println("Titulo: ");
        var title = console.nextLine();
        System.out.println("Descripcion: ");
        var description = console.nextLine().trim();
        service.save(new Task(title, description));
    }

    private void toggle(Scanner console) {
        System.out.println("Id: ");
        Long id = Long.parseLong(console.nextLine().trim());
        service.findById(id).ifPresent(t -> {
            t.setCompleted(!t.isCompleted());
            service.save(t);
        });
    }

    private void borrar(Scanner console) {

        System.out.println("Id a borrar: ");

        try {
            Long id = Long.parseLong(console.nextLine().trim());
            if (service.exists(id)) {
                service.delete(id);
                System.out.println("Tarea eliminada.");
            } else {
                System.out.println("Tarea inexistente.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Número invalido.");
        }
    }
}