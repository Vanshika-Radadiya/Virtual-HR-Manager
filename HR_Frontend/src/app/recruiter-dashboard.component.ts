import { Component, EventEmitter, Input, OnInit, Output, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import {
  CandidateScore,
  CreateInterviewRequest,
  HrApiService,
  Interview,
  InterviewRecord,
} from './hr-api.service';

@Component({
  selector: 'app-recruiter-dashboard',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './recruiter-dashboard.component.html',
  styleUrl: './recruiter-dashboard.component.css',
})
export class RecruiterDashboardComponent implements OnInit {
  private readonly api = inject(HrApiService);

  @Input({ required: true }) userId: number | null = null;
  @Input({ required: true }) username = '';
  @Output() readonly signOut = new EventEmitter<void>();

  interviews: Interview[] = [];
  selectedInterview: Interview | null = null;
  candidates: CandidateScore[] = [];
  selectedCandidate: CandidateScore | null = null;
  answers: InterviewRecord[] = [];
  role = '';
  description = '';
  startDate = '';
  endDate = '';
  candidateEmails = '';
  importedEmails: string[] = [];
  isLoading = false;
  isImporting = false;
  isCreating = false;
  errorMessage = '';
  successMessage = '';

  ngOnInit(): void {
    this.loadInterviews();
  }

  loadInterviews(): void {
    this.isLoading = true;
    this.errorMessage = '';
    this.api.getRecruiterInterviews().subscribe({
      next: (interviews) => {
        this.interviews = interviews;
        this.isLoading = false;
      },
      error: (error: unknown) => {
        this.errorMessage = this.getErrorMessage(error, 'Could not load scheduled interviews.');
        this.isLoading = false;
      },
    });
  }

  selectInterview(interview: Interview): void {
    this.selectedInterview = interview;
    this.selectedCandidate = null;
    this.answers = [];
    this.errorMessage = '';
    this.api.getInterviewCandidates(interview.interviewId).subscribe({
      next: (candidates) => (this.candidates = candidates),
      error: (error: unknown) => {
        this.candidates = [];
        this.errorMessage = this.getErrorMessage(error, 'Could not load candidate results.');
      },
    });
  }

  selectCandidate(candidate: CandidateScore): void {
    const interview = this.selectedInterview;
    if (!interview) {
      return;
    }
    this.selectedCandidate = candidate;
    this.answers = [];
    this.api.getCandidateAnswers(interview.interviewId, candidate.userId).subscribe({
      next: (answers) => (this.answers = answers),
      error: (error: unknown) => {
        this.errorMessage = this.getErrorMessage(error, 'Could not load interview answers.');
      },
    });
  }

  importFile(event: Event): void {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0];
    if (!file) {
      return;
    }

    this.isImporting = true;
    this.errorMessage = '';
    this.api.importCandidates(file).subscribe({
      next: (emails) => {
        this.importedEmails = emails;
        this.isImporting = false;
        input.value = '';
      },
      error: (error: unknown) => {
        this.errorMessage = this.getErrorMessage(error, 'Could not read candidate email addresses.');
        this.isImporting = false;
        input.value = '';
      },
    });
  }

  removeImportedEmails(): void {
    this.importedEmails = [];
  }

  submitInterview(event: Event): void {
    event.preventDefault();
    const emails = this.getCandidateEmails();
    if (!emails.length) {
      this.errorMessage = 'Add candidate email addresses manually or import a spreadsheet.';
      return;
    }

    const request: CreateInterviewRequest = {
      role: this.role.trim(),
      description: this.description.trim(),
      startDate: this.startDate,
      endDate: this.endDate,
      emailIds: emails,
    };
    this.isCreating = true;
    this.errorMessage = '';
    this.successMessage = '';
    this.api.createInterview(request).subscribe({
      next: (interview) => {
        this.isCreating = false;
        this.successMessage = `Interview scheduled for ${emails.length} candidate${emails.length === 1 ? '' : 's'}. Invitations were sent.`;
        this.role = '';
        this.description = '';
        this.startDate = '';
        this.endDate = '';
        this.candidateEmails = '';
        this.importedEmails = [];
        this.loadInterviews();
        this.selectInterview(interview);
      },
      error: (error: unknown) => {
        this.errorMessage = this.getErrorMessage(error, 'Could not schedule the interview.');
        this.isCreating = false;
      },
    });
  }

  getCandidateEmails(): string[] {
    const enteredEmails = this.candidateEmails
      .split(/[,\n;]/)
      .map((email) => email.trim())
      .filter(Boolean);
    return [...new Set([...this.importedEmails, ...enteredEmails].map((email) => email.toLowerCase()))];
  }

  formatDate(date: string): string {
    return new Intl.DateTimeFormat('en-US', {
      weekday: 'short',
      month: 'short',
      day: 'numeric',
      year: 'numeric',
      hour: 'numeric',
      minute: '2-digit',
    }).format(new Date(date));
  }

  scorePercent(score: number): number {
    return Math.round((score / 297) * 100);
  }

  private getErrorMessage(error: unknown, fallback: string): string {
    if (error instanceof HttpErrorResponse && typeof error.error === 'string' && error.error.trim()) {
      return error.error;
    }
    if (
      error instanceof HttpErrorResponse &&
      typeof error.error === 'object' &&
      error.error !== null &&
      'message' in error.error &&
      typeof error.error.message === 'string'
    ) {
      return error.error.message;
    }
    if (error instanceof HttpErrorResponse && error.status === 0) {
      return 'Unable to reach HR Manager. Check that the backend is running on port 8080.';
    }
    return fallback;
  }
}
