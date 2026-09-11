import { Component } from '@angular/core';
// import { FormControl } from '@angular/forms';
import { FormControl, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'project';

  // username="name";
  name = new FormControl('');

  fun(event)
  {
    console.log(event);
    console.log("inside");
    // console.log(this.username)
    localStorage.setItem("Username", this.name.value)
  }
}
