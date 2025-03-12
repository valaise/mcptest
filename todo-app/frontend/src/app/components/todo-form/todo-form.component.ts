import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Todo } from '../../models/todo.model';
import { TodoService } from '../../services/todo.service';

@Component({
  selector: 'app-todo-form',
  templateUrl: './todo-form.component.html',
  styleUrls: ['./todo-form.component.scss']
})
export class TodoFormComponent implements OnInit {
  todoForm!: FormGroup;
  isEditMode = false;
  todoId?: number;
  loading = false;
  error = '';
  submitted = false;

  constructor(
    private fb: FormBuilder,
    private todoService: TodoService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.initForm();
    
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.isEditMode = true;
        this.todoId = +params['id'];
        this.loadTodo(this.todoId);
      }
    });
  }

  initForm(): void {
    this.todoForm = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(3)]],
      description: [''],
      completed: [false]
    });
  }

  loadTodo(id: number): void {
    this.loading = true;
    this.todoService.getTodoById(id).subscribe({
      next: (todo) => {
        this.todoForm.patchValue({
          title: todo.title,
          description: todo.description,
          completed: todo.completed
        });
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Fehler beim Laden des Todos: ' + err.message;
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    this.submitted = true;
    
    if (this.todoForm.invalid) {
      return;
    }

    const todoData: Todo = {
      ...this.todoForm.value
    };

    this.loading = true;
    
    if (this.isEditMode && this.todoId) {
      this.todoService.updateTodo(this.todoId, todoData).subscribe({
        next: () => {
          this.router.navigate(['/']);
        },
        error: (err) => {
          this.error = 'Fehler beim Aktualisieren des Todos: ' + err.message;
          this.loading = false;
        }
      });
    } else {
      this.todoService.createTodo(todoData).subscribe({
        next: () => {
          this.router.navigate(['/']);
        },
        error: (err) => {
          this.error = 'Fehler beim Erstellen des Todos: ' + err.message;
          this.loading = false;
        }
      });
    }
  }
} 