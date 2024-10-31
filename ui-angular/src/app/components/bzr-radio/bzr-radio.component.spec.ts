import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BzrRadioComponent } from './bzr-radio.component';

describe('BzrRadioComponent', () => {
  let component: BzrRadioComponent;
  let fixture: ComponentFixture<BzrRadioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BzrRadioComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BzrRadioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
