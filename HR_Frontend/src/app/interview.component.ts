import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Interview, Question } from './hr-api.service';

@Component({
  selector: 'app-interview',
  standalone: true,
  templateUrl: './interview.component.html',
})
export class InterviewComponent {
  @Input({ required: true }) interview!: Interview;
  @Input({ required: true }) question!: Question;
  @Input({ required: true }) questionNumber = 1;
  @Input({ required: true }) transcript = '';
  @Input({ required: true }) isListening = false;
  @Input({ required: true }) isSpeaking = false;
  @Input({ required: true }) isSubmitting = false;
  @Input({ required: true }) speechSupported = false;

  @Output() readonly back = new EventEmitter<void>();
  @Output() readonly speak = new EventEmitter<void>();
  @Output() readonly toggleRecording = new EventEmitter<void>();
  @Output() readonly submit = new EventEmitter<void>();
  @Output() readonly transcriptChange = new EventEmitter<string>();

  updateTranscript(event: Event): void {
    const input = event.target as HTMLTextAreaElement;
    this.transcriptChange.emit(input.value);
  }
}
