// import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Injectable, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AudioRecordingServiceService {
//   recognition: any;

//   constructor(@Inject(PLATFORM_ID) private platformId: Object) {
//     if (isPlatformBrowser(this.platformId)) {
//       this.recognition = new (window.SpeechRecognition || (window as any).webkitSpeechRecognition)();
//       this.recognition.lang = 'en-US';
//       this.recognition.interimResults = false;
//       this.recognition.maxAlternatives = 1;
//     } else {
//       console.warn('Speech recognition is not supported on this platform.');
//     }
//   }

//   startListening(): Promise<string> {
//     return new Promise((resolve, reject) => {
//       if (!this.recognition) {
//         reject('Speech recognition is not supported on this platform.');
//         return;
//       }

//       this.recognition.onresult = (event: any) => {
//         const transcript = event.results[0][0].transcript;
//         resolve(transcript);
//       };

//       this.recognition.onerror = (event: any) => {
//         reject(event.error);
//       };

//       this.recognition.start();
//     });
//   }

//   stopListening(): void {
//     if (this.recognition) {
//       this.recognition.stop();
//     }
//   }

baseApiUrl = "https://file.io"
    
  constructor(private http:HttpClient) { } 
  
  // Returns an observable 
  upload(file: any):Observable<any> { 
  
      // Create form data 
      const formData = new FormData();  
        
      // Store form name as "file" with file data 
      formData.append("file", file, file.name); 
        
      // Make http post request over api 
      // with formData as req 
      return this.http.post(this.baseApiUrl, formData) 
  } 

}