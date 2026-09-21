import { Component, OnDestroy, OnInit, ViewEncapsulation, inject, signal } from '@angular/core';
import { DashboardComponent } from './dashboard.component';
import { HrApiService, Interview, Question, Result } from './hr-api.service';
import { InterviewComponent } from './interview.component';
import { LoginComponent } from './login.component';

interface SpeechRecognitionEventLike extends Event {
  results: {
    length: number;
    [index: number]: { [index: number]: { transcript: string } };
  };
}

interface SpeechRecognitionLike {
  continuous: boolean;
  interimResults: boolean;
  lang: string;
  start(): void;
  stop(): void;
  onresult: ((event: SpeechRecognitionEventLike) => void) | null;
  onend: (() => void) | null;
  onerror: (() => void) | null;
}

declare global {
  interface Window {
    webkitSpeechRecognition?: new () => SpeechRecognitionLike;
    SpeechRecognition?: new () => SpeechRecognitionLike;
  }
}

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [LoginComponent, DashboardComponent, InterviewComponent],
  templateUrl: './app.html',
  styleUrl: './app.css',
  encapsulation: ViewEncapsulation.None,
})
export class App implements OnInit, OnDestroy {
  private readonly api = inject(HrApiService);

  readonly isAuthenticated = signal(false);
  readonly userId = signal<number | null>(null);
  readonly isLoading = signal(false);
  readonly errorMessage = signal('');
  readonly upcomingInterviews = signal<Interview[]>([]);
  readonly pastInterviews = signal<Interview[]>([]);
  readonly results = signal<Result[]>([]);
  readonly activeInterview = signal<Interview | null>(null);
  readonly currentQuestion = signal<Question | null>(null);
  readonly transcript = signal('');
  readonly isListening = signal(false);
  readonly isSpeaking = signal(false);
  readonly isSubmitting = signal(false);
  readonly questionNumber = signal(1);
  readonly today = new Intl.DateTimeFormat('en-US', {
    month: 'short',
    day: 'numeric',
    year: 'numeric',
  }).format(new Date());
  readonly speechSupported =
    typeof window !== 'undefined' && !!(window.SpeechRecognition || window.webkitSpeechRecognition);

  private recognition: SpeechRecognitionLike | null = null;
  private recorder: MediaRecorder | null = null;
  private firstQuestionTimer: ReturnType<typeof setTimeout> | null = null;

  ngOnInit(): void {
    const savedUserId = localStorage.getItem('hr-user-id');

    if (savedUserId) {
      this.userId.set(Number(savedUserId));
      this.isAuthenticated.set(true);
      this.loadWorkspace();
    }
  }

  ngOnDestroy(): void {
    this.recognition?.stop();
    this.recorder?.stop();
    this.clearFirstQuestionTimer();
  }

  login(credentials: { username: string; password: string }): void {
    this.isLoading.set(true);
    this.errorMessage.set('');

    this.api.validate(credentials.username, credentials.password).subscribe({
      next: (id) => {
        if (id === null || id === undefined) {
          this.errorMessage.set('Those credentials did not match. Please try again.');
        } else {
          this.userId.set(id);
          this.isAuthenticated.set(true);
          localStorage.setItem('hr-user-id', String(id));
          this.loadWorkspace();
        }

        this.isLoading.set(false);
      },
      error: () => {
        this.errorMessage.set(
          'Unable to reach HR Manager. Check that the backend is running on port 8080.',
        );
        this.isLoading.set(false);
      },
    });
  }

  logout(): void {
    localStorage.removeItem('hr-user-id');
    this.isAuthenticated.set(false);
    this.userId.set(null);
  }

  loadWorkspace(): void {
    const id = this.userId();

    if (id === null) {
      return;
    }

    this.api.getUpcoming(id).subscribe({
      next: (interviews) => this.upcomingInterviews.set(interviews),
      error: () => this.upcomingInterviews.set([]),
    });
    this.api.getPast(id).subscribe({
      next: (interviews) => this.pastInterviews.set(interviews),
      error: () => this.pastInterviews.set([]),
    });
    this.api.getResults(id).subscribe({
      next: (results) => this.results.set(results),
      error: () => this.results.set([]),
    });
  }

  startInterview(interview: Interview): void {
    this.activeInterview.set(interview);
    this.questionNumber.set(1);
    this.currentQuestion.set({
      que: 'What is OOps',
      category: 'oops',
      level: 0,
      key: 'Q1',
    });
    this.transcript.set('');
    this.clearFirstQuestionTimer();
    this.firstQuestionTimer = setTimeout(() => this.speakQuestion(), 2000);
  }

  exitInterview(): void {
    this.recognition?.stop();
    this.recorder?.stop();
    this.clearFirstQuestionTimer();
    this.activeInterview.set(null);
    this.currentQuestion.set(null);
    this.transcript.set('');
  }

  speakQuestion(): void {
    const text = this.currentQuestion()?.que;

    if (!text || typeof speechSynthesis === 'undefined') {
      return;
    }

    speechSynthesis.cancel();
    const utterance = new SpeechSynthesisUtterance(text);
    utterance.rate = 0.92;
    utterance.onstart = () => this.isSpeaking.set(true);
    utterance.onend = () => this.isSpeaking.set(false);
    speechSynthesis.speak(utterance);
  }

  toggleListening(): void {
    if (this.isListening()) {
      this.recognition?.stop();
      this.recorder?.stop();
      this.isListening.set(false);
      return;
    }

    this.startListening();
  }

  private startListening(): void {
    if (!this.speechSupported) {
      return;
    }

    const Recognition = window.SpeechRecognition || window.webkitSpeechRecognition;

    if (!Recognition) {
      return;
    }

    this.recognition = new Recognition();
    this.recognition.continuous = true;
    this.recognition.interimResults = true;
    this.recognition.lang = 'en-US';
    this.recognition.onresult = (event) => {
      let text = '';

      for (let index = 0; index < event.results.length; index++) {
        text += event.results[index][0].transcript;
      }

      this.transcript.set(text);
    };
    this.recognition.onend = () => this.isListening.set(false);
    this.recognition.onerror = () => this.isListening.set(false);
    this.recognition.start();
    this.isListening.set(true);
    this.captureAudio();
  }

  private captureAudio(): void {
    if (!navigator.mediaDevices?.getUserMedia) {
      return;
    }

    navigator.mediaDevices
      .getUserMedia({ audio: true })
      .then((stream) => {
        this.recorder = new MediaRecorder(stream);
        this.recorder.onstop = () => stream.getTracks().forEach((track) => track.stop());
        this.recorder.start();
      })
      .catch(() => undefined);
  }

  submitResponse(): void {
    const question = this.currentQuestion();
    const interview = this.activeInterview();
    const id = this.userId();
    const answer = this.transcript().trim();

    if (!question || !interview || id === null || !answer) {
      return;
    }

    this.recognition?.stop();
    this.recorder?.stop();
    this.isListening.set(false);
    this.isSubmitting.set(true);

    this.api
      .submitAnswer({
        ...question,
        answer,
        userId: id,
        interviewId: interview.interviewId,
      })
      .subscribe({
        next: (nextQuestion) => {
          this.isSubmitting.set(false);
          this.transcript.set('');

          if (nextQuestion?.que) {
            this.questionNumber.update((value) => value + 1);
            this.currentQuestion.set(nextQuestion);
            this.speakQuestion();
          } else {
            window.location.reload();
          }
        },
        error: () => {
          this.isSubmitting.set(false);
          this.errorMessage.set('We could not save that response. Please try again.');
        },
      });
  }

  private clearFirstQuestionTimer(): void {
    if (this.firstQuestionTimer !== null) {
      clearTimeout(this.firstQuestionTimer);
      this.firstQuestionTimer = null;
    }
  }
}
