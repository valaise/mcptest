import { Component, OnInit } from '@angular/core';
import { Todo } from '../../models/todo.model';
import { TodoService } from '../../services/todo.service';

@Component({
  selector: 'app-todo-list',
  templateUrl: './todo-list.component.html',
  styleUrls: ['./todo-list.component.scss']
})
export class TodoListComponent implements OnInit {
  todos: Todo[] = [];
  loading = false;
  error = '';
  filterStatus: 'all' | 'active' | 'completed' = 'all';

  constructor(private todoService: TodoService) { }

  ngOnInit(): void {
    this.loadTodos();
  }

  loadTodos(): void {
    this.loading = true;
    this.error = '';

    if (this.filterStatus === 'all') {
      this.todoService.getAllTodos().subscribe({
        next: (data) => {
          this.todos = data;
          this.loading = false;
        },
        error: (err) => {
          this.error = 'Fehler beim Laden der Todos: ' + err.message;
          this.loading = false;
        }
      });
    } else {
      const completed = this.filterStatus === 'completed';
      this.todoService.getTodosByStatus(completed).subscribe({
        next: (data) => {
          this.todos = data;
          this.loading = false;
        },
        error: (err) => {
          this.error = 'Fehler beim Laden der Todos: ' + err.message;
          this.loading = false;
        }
      });
    }
  }

  toggleStatus(todo: Todo): void {
    const updatedTodo = { ...todo, completed: !todo.completed };
    this.todoService.updateTodo(todo.id!, updatedTodo).subscribe({
      next: (data) => {
        const index = this.todos.findIndex(t => t.id === todo.id);
        if (index !== -1) {
          this.todos[index] = data;
        }
      },
      error: (err) => {
        this.error = 'Fehler beim Aktualisieren des Todos: ' + err.message;
      }
    });
  }

  deleteTodo(id: number): void {
    if (confirm('Sind Sie sicher, dass Sie dieses Todo löschen möchten?')) {
      this.todoService.deleteTodo(id).subscribe({
        next: () => {
          this.todos = this.todos.filter(todo => todo.id !== id);
        },
        error: (err) => {
          this.error = 'Fehler beim Löschen des Todos: ' + err.message;
        }
      });
    }
  }

  applyFilter(status: 'all' | 'active' | 'completed'): void {
    this.filterStatus = status;
    this.loadTodos();
  }
} 