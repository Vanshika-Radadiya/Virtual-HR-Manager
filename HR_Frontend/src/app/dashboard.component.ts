import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Interview, Result } from './hr-api.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  templateUrl: './dashboard.component.html',
})
export class DashboardComponent {
  @Input({ required: true }) upcomingInterviews: Interview[] = [];
  @Input({ required: true }) pastInterviews: Interview[] = [];
  @Input({ required: true }) results: Result[] = [];
  @Input({ required: true }) userId: number | null = null;
  @Input({ required: true }) today = '';

  @Output() readonly signOut = new EventEmitter<void>();
  @Output() readonly refresh = new EventEmitter<void>();
  @Output() readonly startInterview = new EventEmitter<Interview>();

  selectedPastInterview: Interview | null = null;

  selectPastInterview(interview: Interview): void {
    this.selectedPastInterview =
      this.selectedPastInterview?.interviewId === interview.interviewId ? null : interview;
  }

  selectedReport(): Result | undefined {
    return this.results.find(
      (result) => result.compId?.interviewId === this.selectedPastInterview?.interviewId,
    );
  }

  resultSummary(): number {
    const values = this.results
      .flatMap((result) => [result.oops, result.os, result.dbms])
      .filter((value) => typeof value === 'number');

    return values.length
      ? Math.round(values.reduce((total, value) => total + value, 0) / values.length)
      : 0;
  }

  scoreFill(score: number): string {
    return `${Math.max(2, Math.min(100, score))}%`;
  }

  getDay(date: string): string {
    return new Date(date).getDate().toString().padStart(2, '0');
  }

  formatDate(date: string): string {
    return new Intl.DateTimeFormat('en-US', {
      weekday: 'short',
      month: 'short',
      day: 'numeric',
      hour: 'numeric',
      minute: '2-digit',
    }).format(new Date(date));
  }
}
