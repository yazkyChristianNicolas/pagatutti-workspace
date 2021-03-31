import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MailValidationComponent } from './mail-validation.component';

describe('MailValidationComponent', () => {
  let component: MailValidationComponent;
  let fixture: ComponentFixture<MailValidationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MailValidationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MailValidationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
