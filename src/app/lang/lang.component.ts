import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';

declare var webkitSpeechRecognition: any;
@Component({
  selector: 'app-lang',
  templateUrl: './lang.component.html',
  styleUrls: ['./lang.component.css']
})
export class LangComponent implements OnInit {

  ques = ["It seems that you are interested in Web Development, So can you please elaborate on the projects that you had worked on in this domain",
    "Can you please Share Details about the projects that you had worked on?",
    "Can you Share us some of your achivements",
    "Can you Share details about your experience in previous internships."
  ];

  counter = 1;

  ques1 = "1. Can you please introduce yourself";
  cameraSrc = "assets/images/1.mp4";
  ques2 = null;
  ques3 = null;
  ques4 = null;
  ques5 = null;
  ans1 = null;
  ans2 = null;
  ans3 = null;
  ans4 = null;
  ans5 = null;
  time1 = null;
  time2 = null;
  time3 = null;
  time4 = null;
  time5 = null;
  recognition: any
  constructor(private http: HttpClient){}

  ngOnInit(): void {
    this.playPause()
    const startButton = document.getElementById('start');
    const stopButton = document.getElementById('stop');
    const resultElement = document.getElementById('result');

    this.recognition = new webkitSpeechRecognition();

    this.recognition.lang = window.navigator.language;
    this.recognition.interimResults = true;
    setTimeout(function () {      
      // playerMP3.play();
      var myVideo: any = document.getElementById("my_video_1");
      myVideo.play();
   }, 150);

    startButton.addEventListener('click', () => { this.recognition.start(); });
    stopButton.addEventListener('click', () => { this.recognition.stop(); });

    this.recognition.addEventListener('result', (event) => {
      const result = event.results[event.results.length - 1][0].transcript;
      // resultElement.textContent = result;
      console.log(this.counter);
      if(this.counter % 2 == 0){
      if (this.ques2 == null) {
        this.ans1 = result;
        // this.ques2 = this.ques[1];
      }
      else if (this.ques3 == null) {
        this.ans2 = result;
        // this.ques3 = this.ques[0];
      }
      else if (this.ques4 == null) {
        this.ans3 = result;
        // this.ques4 = this.ques[2];
      }
      else if (this.ques5 == null) {
        this.ans4 = result;
        // this.ques5 = this.ques[3];
      }
      else {
        this.ans5 = result;
      }
    }

    });
  }

  async playPause(x=0) {
    var myVideo: any = document.getElementById("my_video_1");
    if (myVideo.paused) myVideo.play();
    else myVideo.pause();
    if(x == 1)
    {
      myVideo.play()
    }
    if(x == 2)
      {
        myVideo.pause();
      }
  }

  getSaveURl(name, prbm, ans) {
    // now returns an Observable of Config
    return this.http.get<any>("http://localhost:8080/saveuser?name=" + name+ "&problemDescription=" + prbm + "&ans=" + ans);
  }

  done1 = false;
  done2 = false;
  done3 = false;
  done4 = false;
  done5 = false;

  first1 = false;
  first2 = false;
  first3 = false;
  first4 = false;
  first5 = false;

  async incC()
  {
    console.log("inside");
    this.counter+=1;
    if(this.counter % 2 != 0)
      {
        // this.recognition.stop();
        // this.playPause(2);
        console.log("inside ques");
        if (this.ques2 == null) {
        console.log("inside ques2");
          this.ques2 = "2. " + this.ques[1];
          if(!this.first1)
            {
              this.cameraSrc = "assets/images/2.mp4";
              console.log(this.cameraSrc)
              this.first1 = true;
              setTimeout(function () {      
                // playerMP3.play();
                var myVideo: any = document.getElementById("my_video_1");
                myVideo.play();
             }, 150);
            }
          if(this.ans1 && !this.done1){
            console.log(localStorage.getItem("Username"))
          this.getSaveURl(localStorage.getItem("Username"), this.ques1, this.ans1).subscribe();
          this.done1 = true;
          }

        }
        else if (this.ques3 == null && !this.done2) {
          this.ques3 = "3. " + this.ques[0];
          if(this.ans2.contains("web development"))
          if(!this.first2)
            {
              this.cameraSrc = "assets/images/3.mp4";
              this.first2 = true;
              setTimeout(function () {      
                // playerMP3.play();
                var myVideo: any = document.getElementById("my_video_1");
                myVideo.play();
             }, 150);
            }
          if(this.ans2){
          this.getSaveURl(localStorage.getItem("Username"), this.ques2, this.ans2).subscribe();
          this.done2 = true;
          }
        }
        else if (this.ques4 == null && !this.done3) {
          this.ques4 = "4. " + this.ques[2];
          if(!this.first3)
            {
              this.cameraSrc = "assets/images/4.mp4";
              this.first3 = true;
              setTimeout(function () {      
                // playerMP3.play();
                var myVideo: any = document.getElementById("my_video_1");
                myVideo.play();
             }, 150);
            }
          if(this.ans3){
          this.getSaveURl(localStorage.getItem("Username"), this.ques3, this.ans3).subscribe();
          this.done3 = true;
          }
        }
        else if (this.ques5 == null && !this.done4) {
          this.ques5 = "5. " + this.ques[3];
          if(!this.first4)
            {
              this.cameraSrc = "assets/images/5.mp4";
              this.first4 = true;
              setTimeout(function () {      
                // playerMP3.play();
                var myVideo: any = document.getElementById("my_video_1");
                myVideo.play();
             }, 150);
            }
          if(this.ans4){
          this.getSaveURl(localStorage.getItem("Username"), this.ques4, this.ans4).subscribe();
          this.done4 = true;
          }
        }
        else
        {
          if(this.ans5 && !this.done5){
          this.getSaveURl(localStorage.getItem("Username"), this.ques5, this.ans5).subscribe();
          this.done5 = true;
          }
          if(!this.first5)
            {
              this.cameraSrc = "assets/images/6.mp4";
              this.first5 = true;
              setTimeout(function () {      
                // playerMP3.play();
                var myVideo: any = document.getElementById("my_video_1");
                myVideo.play();
             }, 150);
            }

        }
      }
  }




}
