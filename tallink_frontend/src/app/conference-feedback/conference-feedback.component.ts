import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ConferenceFeedback } from './conference.feedback';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-conference-feedback',
  standalone: true,
  imports: [NgFor, HttpClientModule, RouterLink],
  templateUrl: './conference-feedback.component.html',
  styleUrl: './conference-feedback.component.css'
})
export class ConferenceFeedbackComponent implements OnInit {

  conferenceUuid: String | null = null;

  conferenceFeedbacks: ConferenceFeedback[] = [];

  constructor(private http: HttpClient, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.conferenceUuid = params["conferenceUuid"];
    });

    this.http.get<ConferenceFeedback[]>(
      'http://127.0.0.1:8080/api/backoffice/conference/' + this.conferenceUuid + '/feedbacks',
    ).subscribe(data => {
      this.conferenceFeedbacks = data;
    });
  }

}
