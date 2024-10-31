import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BzrCheckboxComponent } from './bzr-checkbox.component';

describe('BzrCheckboxComponent', () => {
  let component: BzrCheckboxComponent;
  let fixture: ComponentFixture<BzrCheckboxComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BzrCheckboxComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BzrCheckboxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
