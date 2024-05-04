import { Component, OnInit } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { ConferenceRoom } from '../conference-room/conference-room.interface';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { DatePipe, NgFor, NgIf } from '@angular/common';
import { Conference } from '../conference/conference.interface';
import { ToastrService } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';
import { Subject, debounceTime, distinctUntilChanged } from 'rxjs';
import { ConferenceRoomEventModel } from './event.model';

@Component({
  selector: 'app-backoffice',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, HttpClientModule, NgFor, NgIf, FormsModule, DatePipe],
  templateUrl: './backoffice.component.html',
  styleUrl: './backoffice.component.css'
})
export class BackofficeComponent implements OnInit {

  conferenceRooms: ConferenceRoom[] = [];
  conferences: Conference[] = [];
  conferenceRoomPreviousStatus: String | null = null;
  conferenceRoomMaxCapacityChnaged = new Subject<ConferenceRoomEventModel>();

  constructor(private http: HttpClient, private toast: ToastrService) {
    this.conferenceRoomMaxCapacityChnaged.pipe(
      debounceTime(2000),
      distinctUntilChanged())
      .subscribe((data: ConferenceRoomEventModel) => {
        this.onConferenceRoomMaxCapacityChnaged(data.value, data.model);
      });
   }

  ngOnInit() {
    this.http.get<ConferenceRoom[]>(
      'http://127.0.0.1:8080/api/backoffice/conference_room/get_all',
    ).subscribe(data => {
      this.conferenceRooms = data;
    });

    this.getConferences();
  }

  getConferences() {
    this.http.get<Conference[]>(
      'http://127.0.0.1:8080/api/backoffice/conference/get_all',
    ).subscribe(data => {
      this.conferences = data;
    });
  }

  showBasedOnDate(date: Date | null) {

    if (date) {
      let startDate = new Date(date)
      let userTimezoneOffset = startDate.getTimezoneOffset() * 60000;
      startDate = new Date(startDate.getTime() + userTimezoneOffset);

      return startDate > new Date();
    }

    return false;
   }

   cancelConference(conference: Conference) {
    if(confirm("Are you sure?")) {
      this.http.post(
        'http://127.0.0.1:8080/api/backoffice/conference/cancel/' + conference.uuid,
        null
      ).subscribe(data => {
        if (!data) {
          this.toast.warning('Cannot cancel conference!', 'Warning!');
        } else {
          this.toast.success('Conference canceled successfully!', 'Success!');
          conference.cancelled = true;
        }
      });
    }
   }

   onConferenceRoomStatusChange(status: string | null, conferenceRoom: ConferenceRoom) {
    if(confirm("Are you sure?")) {
      conferenceRoom.status = status;
      this.http.post(
        'http://127.0.0.1:8080/api/backoffice/conference_room/' + conferenceRoom.uuid + '/change_status',
        status
      ).subscribe(data => {
        if (data) {
          this.toast.success('Status updated successfully!', 'Success!');
          this.getConferences();

          return;
        }
      });
    }

    let previousStatus = conferenceRoom.status;
    conferenceRoom.status = null;
    setTimeout(() => {
      conferenceRoom.status = previousStatus;
    }, 100);

   }

   onConferenceRoomMaxCapacityChnaged(capacity: number, conferenceRoom: ConferenceRoom) {
    this.http.post(
      'http://127.0.0.1:8080/api/backoffice/conference_room/' + conferenceRoom.uuid + '/change_max_capacity',
      capacity
    ).subscribe((response: any) => {
      if (response.success) {
        this.toast.success(response.message, 'Success!');
        conferenceRoom.maxCapacity = capacity;

        return;
      }

      this.toast.warning(response.message, 'Warning!');

      let previsousCapacity = conferenceRoom.maxCapacity;
      conferenceRoom.maxCapacity = null;
      setTimeout(() => {
        conferenceRoom.maxCapacity = previsousCapacity;
      }, 100);
    });
   }
}
