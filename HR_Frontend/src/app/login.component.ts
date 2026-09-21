import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
})
export class LoginComponent {
  @Input() errorMessage = '';
  @Input() isLoading = false;
  @Output() readonly submitted = new EventEmitter<{ username: string; password: string }>();

  username = '';
  password = '';
  showPassword = false;

  submit(event: Event): void {
    event.preventDefault();
    this.submitted.emit({ username: this.username, password: this.password });
  }
}
