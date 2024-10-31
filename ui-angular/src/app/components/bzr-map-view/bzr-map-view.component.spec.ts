import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BzrMapViewComponent } from './bzr-map-view.component';

describe('BzrMapViewComponent', () => {
  let component: BzrMapViewComponent;
  let fixture: ComponentFixture<BzrMapViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BzrMapViewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BzrMapViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
