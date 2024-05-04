import { Component, OnInit } from '@angular/core';
import { Conference } from '../conference/conference.interface';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { RouterLink } from '@angular/router';
import { ConferenceRoom } from '../conference-room/conference-room.interface';
import { DatePipe, NgFor, NgIf } from '@angular/common';
import { ToastrService } from 'ngx-toastr';
import DateUtils from '../utils/date.utils';

@Component({
  selector: 'app-create-conference',
  standalone: true,
  imports: [
    FormsModule, 
    HttpClientModule, 
    RouterLink, 
    NgFor, 
    NgIf, 
    ReactiveFormsModule,
    DatePipe
  ],
  templateUrl: './create-conference.component.html',
  styleUrl: './create-conference.component.css'
})
export class CreateConferenceComponent implements OnInit {

  currentDate = new Date();

  conferenceRooms: ConferenceRoom[] = [];

  conferences: Conference[] = [];

  conference: Conference = {
    uuid: null,
    conferenceRoomUUID: null,
    name: null,
    description: null,
    startDateTime: null,
    endDateTime: null,
    cancelled: null,
    conferenceRoom: null,
    conferenceParticipants: null
  }


  constructor(private http: HttpClient, private toast: ToastrService) { }

  ngOnInit(): void {
    this.http.get<ConferenceRoom[]>(
      'http://127.0.0.1:8080/api/backoffice/conference_room/get_all',
    ).subscribe(data => {
      this.conferenceRooms = data;
    });

    this.http.get<Conference[]>(
      'http://127.0.0.1:8080/api/backoffice/conference/get_all',
    ).subscribe(data => {
      this.conferences = data;
    });
  }

  public createConference() {
    this.http.post(
      'http://127.0.0.1:8080/api/backoffice/conference/create',
      this.conference
    ).subscribe((response: any) => {
      if (response.success) {
        this.toast.success(response.message, 'Success!');
        return;
      }

      this.toast.warning(response.message, 'Warning!');
    });
  }

  unmodifiedDate(inputDate: Event) {
    let value = (inputDate.target as HTMLInputElement).value;
    let timeZoneOffset = DateUtils.getTimeZoneOffsetString();
    let date = new Date(value + timeZoneOffset);

    return date;
  }
}
