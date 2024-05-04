import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Conference } from '../conference/conference.interface';
import { DatePipe, NgFor, NgIf } from '@angular/common';
import { FormsModule, NgModel } from '@angular/forms';
import { ConferenceRoom } from '../conference-room/conference-room.interface';
import DateUtils from '../utils/date.utils';

@Component({
  selector: 'app-update-conference',
  standalone: true,
  imports: [NgIf, FormsModule, DatePipe, HttpClientModule, NgFor, RouterLink],
  templateUrl: './update-conference.component.html',
  styleUrl: './update-conference.component.css'
})
export class UpdateConferenceComponent implements OnInit {

  conferenceUuid: String | null = null;

  currentDate = new Date();

  conferenceRooms: ConferenceRoom[] = [];

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

  constructor(private route: ActivatedRoute, private http: HttpClient, private toast: ToastrService) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.conferenceUuid = params["conferenceUuid"];
    });

    this.http.get<ConferenceRoom[]>(
      'http://127.0.0.1:8080/api/backoffice/conference_room/get_all',
    ).subscribe(data => {
      this.conferenceRooms = data;
    });

    this.http.get<Conference>(
      'http://127.0.0.1:8080/api/backoffice/conference/' + this.conferenceUuid,
    ).subscribe(data => {
      this.conference = data;
    });
  }

  updateConference() {
    this.http.post(
      'http://127.0.0.1:8080/api/backoffice/conference/update',
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

