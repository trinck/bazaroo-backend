import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BzrStepComponent } from './bzr-step.component';

describe('BzrStepComponent', () => {
  let component: BzrStepComponent;
  let fixture: ComponentFixture<BzrStepComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BzrStepComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BzrStepComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
