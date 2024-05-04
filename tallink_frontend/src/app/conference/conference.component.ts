import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Conference } from './conference.interface';
import { DatePipe, NgFor, NgIf } from '@angular/common';

@Component({
  selector: 'app-conference',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, FormsModule, HttpClientModule, NgFor, DatePipe, NgIf],
  templateUrl: './conference.component.html',
  styleUrl: './conference.component.css'
})
export class ConferenceComponent {

  startDateTime: String | null = null;
  endDateTime: String | null = null;
  availableConferences: Conference[] = [];
  participantUUID: String | null = null;
  conferenceFeedback: String | null = null;

  constructor(private http: HttpClient, private toast: ToastrService) { }

  findAvailableConferences() {
    this.http.post(
      'http://127.0.0.1:8080/api/conference/available',
      {
        startDateTime: this.startDateTime,
        endDateTime: this.endDateTime
      }
    ).subscribe((response: any)  => {
        this.availableConferences = response.data;
    });
  }

  shouldShowRegisterButton(conference: Conference) {
    return conference.conferenceRoom?.maxCapacity && 
      conference.conferenceParticipants && 
      conference.conferenceRoom.maxCapacity > conference.conferenceParticipants.length;
  }

  cancelRegistration() {
    if(confirm("Are you sure?")) {
      this.http.post(
        'http://127.0.0.1:8080/api/conference/cancel_registration/' + this.participantUUID,
        null
      ).subscribe((response: any)  => {
        if (response.success) {
          this.toast.success(response.message, 'Success!');
          this.updateAvailableConference(response.data);
          return;
        }

        this.toast.warning(response.message, 'Warning!');
      });
    }
  }

  updateAvailableConference(conference: Conference) {
      this.availableConferences.forEach((availableConference: Conference, index: number) => {
        if (conference.uuid === availableConference.uuid) {
          this.availableConferences[index] = conference;
          return;
        }
      });
  }

  leaveFeedback() {
    if(confirm("Are you sure?")) {
      this.http.post(
        'http://127.0.0.1:8080/api/conference/feedback',
        {
          participantUUID: this.participantUUID,
          feedback: this.conferenceFeedback
        }
      ).subscribe((response: any)  => {
        if (response.success) {
          this.toast.success(response.message, 'Success!');
          return;
        }

        this.toast.warning(response.message, 'Warning!');
      });
    }
  }

}
