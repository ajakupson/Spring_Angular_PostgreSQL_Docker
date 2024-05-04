import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ConferenceComponent } from './conference/conference.component';
import { BackofficeComponent } from './backoffice/backoffice.component';
import { CreateConferenceRoomComponent } from './create-conference-room/create-conference-room.component';
import { CreateConferenceComponent } from './create-conference/create-conference.component';
import { RegisterParticipantComponent } from './register-participant/register-participant.component';
import { UpdateConferenceComponent } from './update-conference/update-conference.component';
import { ConferenceFeedbackComponent } from './conference-feedback/conference-feedback.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'backoffice', component: BackofficeComponent },
    { path: 'conference', component: ConferenceComponent },
    { path: 'create-conference-room', component: CreateConferenceRoomComponent },
    { path: 'create-conference', component: CreateConferenceComponent },
    { path: 'update-conference', component: UpdateConferenceComponent },
    { path: 'register-participant', component: RegisterParticipantComponent },
    { path: 'conference-feedback', component: ConferenceFeedbackComponent },
];
