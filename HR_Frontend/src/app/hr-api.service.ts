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

export interface LoginResponse {
  userId: number;
  username: string;
  role: string;
}

export interface CandidateScore {
  userId: number;
  username: string;
  emailId: string;
  score: number;
  hasResult: boolean;
  completed: boolean;
  oops: number;
  os: number;
  dbms: number;
}

export interface InterviewRecord {
  recordId: number;
  userId: number;
  interviewId: number;
  que: string;
  ans: string;
  score: number;
}

export interface CreateInterviewRequest {
  role: string;
  description: string;
  startDate: string;
  endDate: string;
  emailIds: string[];
}

@Injectable({ providedIn: 'root' })
export class HrApiService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = 'https://virtual-hr-manager.onrender.com';
  private readonly credentials = { withCredentials: true };

  login(username: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(
      `${this.baseUrl}/login`,
      { username, password },
      this.credentials,
    );
  }

  getSession(): Observable<LoginResponse> {
    return this.http.get<LoginResponse>(`${this.baseUrl}/session`, this.credentials);
  }

  logout(): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/logout`, {}, this.credentials);
  }

  validate(username: string, password: string): Observable<number | null> {
    const params = new HttpParams().set('username', username).set('password', password);
    return this.http.get<number | null>(`${this.baseUrl}/validate`, {
      params,
      ...this.credentials,
    });
  }
  getUpcoming(userId: number): Observable<Interview[]> {
    return this.http.get<Interview[]>(`${this.baseUrl}/upcoming/${userId}`, this.credentials);
  }
  getPast(userId: number): Observable<Interview[]> {
    return this.http.get<Interview[]>(`${this.baseUrl}/past/${userId}`, this.credentials);
  }
  getResults(userId: number): Observable<Result[]> {
    return this.http.get<Result[]>(`${this.baseUrl}/result/userId/${userId}`, this.credentials);
  }
  submitAnswer(request: AnswerRequest): Observable<Question | null> {
    return this.http.post<Question | null>(`${this.baseUrl}/saverecord`, request, this.credentials);
  }

  getRecruiterInterviews(): Observable<Interview[]> {
    return this.http.get<Interview[]>(`${this.baseUrl}/recruiter/interviews`, this.credentials);
  }

  getInterviewCandidates(interviewId: number): Observable<CandidateScore[]> {
    return this.http.get<CandidateScore[]>(
      `${this.baseUrl}/recruiter/interviews/${interviewId}/candidates`,
      this.credentials,
    );
  }

  getCandidateAnswers(interviewId: number, candidateId: number): Observable<InterviewRecord[]> {
    return this.http.get<InterviewRecord[]>(
      `${this.baseUrl}/recruiter/interviews/${interviewId}/candidates/${candidateId}/answers`,
      this.credentials,
    );
  }

  createInterview(request: CreateInterviewRequest): Observable<Interview> {
    return this.http.post<Interview>(
      `${this.baseUrl}/recruiter/interviews`,
      request,
      this.credentials,
    );
  }

  importCandidates(file: File): Observable<string[]> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<string[]>(
      `${this.baseUrl}/recruiter/import-candidates`,
      formData,
      this.credentials,
    );
  }
}
