import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConferenceFeedbackComponent } from './conference-feedback.component';

describe('ConferenceFeedbackComponent', () => {
  let component: ConferenceFeedbackComponent;
  let fixture: ComponentFixture<ConferenceFeedbackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConferenceFeedbackComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConferenceFeedbackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
