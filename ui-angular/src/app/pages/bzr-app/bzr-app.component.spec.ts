import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BzrAppComponent } from './bzr-app.component';

describe('BzrAppComponent', () => {
  let component: BzrAppComponent;
  let fixture: ComponentFixture<BzrAppComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BzrAppComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BzrAppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
