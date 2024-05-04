import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateConferenceRoomComponent } from './create-conference-room.component';

describe('CreateConferenceRoomComponent', () => {
  let component: CreateConferenceRoomComponent;
  let fixture: ComponentFixture<CreateConferenceRoomComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateConferenceRoomComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CreateConferenceRoomComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
