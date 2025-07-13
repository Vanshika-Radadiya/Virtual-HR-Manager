import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'

@Component({
  selector: 'app-language',
  templateUrl: './language.component.html',
  styleUrls: ['./language.component.css']
})
export class LanguageComponent implements OnInit {

  constructor( private router: Router) { }

  ngOnInit(): void {
  }

  language()
  {
    this.router.navigate(['/lang']);
  }
}
