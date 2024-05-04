import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Participant } from './participant.interface';
import { NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-register-participant',
  standalone: true,
  imports: [NgIf, FormsModule, HttpClientModule, RouterLink],
  templateUrl: './register-participant.component.html',
  styleUrl: './register-participant.component.css'
})
export class RegisterParticipantComponent implements OnInit {
  
  conferenceUuid: String | null = null;
  participant: Participant = {
    uuid: null,
    firstName: null,
    lastName: null,
    gender: null,
    email: null,
    dateOfBirth: null
  }

  constructor(private route: ActivatedRoute, private http: HttpClient, private toast: ToastrService) {}
  
  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.conferenceUuid = params["conferenceUuid"];
    });
  }

  register() {
    this.http.post(
      'http://127.0.0.1:8080/api/conference/' + this.conferenceUuid + '/register',
      this.participant
    ).subscribe((response: any) => {
      this.participant = response.data;

      if (response.success) {
        this.toast.success(response.message, 'Success!');
        return;
      }

      this.toast.warning(response.message, 'Warning!');
    });
  }
}
