import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Interview {
  interviewId: number;
  role: string;
  description: string;
  startDate: string;
  endDate: string;
}
export interface Question {
  que: string;
  category: string;
  level: number;
  key: string;
}
export interface AnswerRequest extends Question {
  answer: string;
  userId: number;
  interviewId: number;
}
export interface Result {
  compId: { userId: number; interviewId: number };
  oops: number;
  os: number;
  dbms: number;
}

@Injectable({ providedIn: 'root' })
export class HrApiService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080';
  validate(username: string, password: string): Observable<number | null> {
    const params = new HttpParams().set('username', username).set('password', password);
    return this.http.get<number | null>(`${this.baseUrl}/validate`, { params });
  }
  getUpcoming(userId: number): Observable<Interview[]> {
    return this.http.get<Interview[]>(`${this.baseUrl}/upcoming/${userId}`);
  }
  getPast(userId: number): Observable<Interview[]> {
    return this.http.get<Interview[]>(`${this.baseUrl}/past/${userId}`);
  }
  getResults(userId: number): Observable<Result[]> {
    return this.http.get<Result[]>(`${this.baseUrl}/result/userId/${userId}`);
  }
  submitAnswer(request: AnswerRequest): Observable<Question | null> {
    return this.http.post<Question | null>(`${this.baseUrl}/saverecord`, request);
  }
}
