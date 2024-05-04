import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideToastr } from 'ngx-toastr';
import { provideAnimations } from '@angular/platform-browser/animations';
import { DATE_PIPE_DEFAULT_OPTIONS } from '@angular/common';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes), 
    provideAnimations(), 
    provideToastr({ positionClass: 'toast-top-right' }),
    { provide: DATE_PIPE_DEFAULT_OPTIONS, useValue: { dateFormat: 'yyyy.MM.dd HH:mm:ss', timezone: 'UTC' } }
  ]
};
