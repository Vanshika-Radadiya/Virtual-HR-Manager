import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';


@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {

  constructor(private http: HttpClient){}
  
  details;
  ngOnInit(): void {
    this.getAllDetails().subscribe(
      content => {this.details = content}
    );
  }


  getAllDetails() {
    // now returns an Observable of Config
    return this.http.get<any>("http://localhost:8080/getallusers");
  }
}
