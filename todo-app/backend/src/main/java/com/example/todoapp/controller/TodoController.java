package com.example.todoapp.controller;

import com.example.todoapp.model.Todo;
import com.example.todoapp.service.TodoService;
import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    @Timed(value = "get.all.todos", description = "Time taken to return all todos")
    public ResponseEntity<List<Todo>> getAllTodos() {
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @GetMapping("/{id}")
    @Timed(value = "get.todo.by.id", description = "Time taken to return a todo by id")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        return todoService.getTodoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status")
    @Timed(value = "get.todos.by.status", description = "Time taken to return todos by status")
    public ResponseEntity<List<Todo>> getTodosByStatus(@RequestParam boolean completed) {
        return ResponseEntity.ok(todoService.getTodosByCompleted(completed));
    }

    @PostMapping
    @Timed(value = "create.todo", description = "Time taken to create a todo")
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody Todo todo) {
        return new ResponseEntity<>(todoService.createTodo(todo), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Timed(value = "update.todo", description = "Time taken to update a todo")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @Valid @RequestBody Todo todoDetails) {
        return todoService.updateTodo(id, todoDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Timed(value = "delete.todo", description = "Time taken to delete a todo")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        return todoService.deleteTodo(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
} 