package com.example.todoapp.controller;

import com.example.todoapp.model.Todo;
import com.example.todoapp.service.TodoService;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*")
public class TodoController {

    private final TodoService todoService;
    private final Counter todoCreatedCounter;
    private final Counter todoCompletedCounter;

    @Autowired
    public TodoController(TodoService todoService, MeterRegistry registry) {
        this.todoService = todoService;
        this.todoCreatedCounter = registry.counter("todo.created");
        this.todoCompletedCounter = registry.counter("todo.completed");
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        return ResponseEntity.ok(todoService.findAllTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        return todoService.findTodoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody Todo todo) {
        Todo createdTodo = todoService.saveTodo(todo);
        todoCreatedCounter.increment();
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @Valid @RequestBody Todo todo) {
        return todoService.findTodoById(id)
                .map(existingTodo -> {
                    todo.setId(id);
                    
                    // Track completion status change
                    if (!existingTodo.isCompleted() && todo.isCompleted()) {
                        todoCompletedCounter.increment();
                    }
                    
                    return ResponseEntity.ok(todoService.saveTodo(todo));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        return todoService.findTodoById(id)
                .map(todo -> {
                    todoService.deleteTodo(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/completed")
    public ResponseEntity<List<Todo>> getCompletedTodos() {
        return ResponseEntity.ok(todoService.findTodosByCompleted(true));
    }

    @GetMapping("/incomplete")
    public ResponseEntity<List<Todo>> getIncompleteTodos() {
        return ResponseEntity.ok(todoService.findTodosByCompleted(false));
    }
}
