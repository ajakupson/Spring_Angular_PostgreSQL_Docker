import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { ConferenceRoom } from '../conference-room/conference-room.interface';
import { NgIf } from '@angular/common';
import { ToastrService } from 'ngx-toastr';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-create-conference-room',
  standalone: true,
  imports: [FormsModule, HttpClientModule, NgIf, RouterLink],
  templateUrl: './create-conference-room.component.html',
  styleUrl: './create-conference-room.component.css'
})
export class CreateConferenceRoomComponent {
  conferenceRoom: ConferenceRoom = {
    uuid: null,
    name: null,
    status: null,
    location: null,
    maxCapacity: null
  }

  constructor(private http: HttpClient, private toast: ToastrService) { }

  public createRoom() {
    this.http.post(
      'http://127.0.0.1:8080/api/backoffice/conference_room/create',
      this.conferenceRoom
    ).subscribe((response: any) => {
      if (response.success) {
        this.toast.success(response.message, 'Success!');
        return;
      }

      this.toast.warning(response.message, 'Warning!');
    });
  }
}
