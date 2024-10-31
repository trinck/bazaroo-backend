import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BzrStepperComponent } from './bzr-stepper.component';

describe('BzrStepperComponent', () => {
  let component: BzrStepperComponent;
  let fixture: ComponentFixture<BzrStepperComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BzrStepperComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BzrStepperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
