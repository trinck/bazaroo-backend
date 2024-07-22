import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BzrMapComponent } from './bzr-map.component';

describe('BzrMapComponent', () => {
  let component: BzrMapComponent;
  let fixture: ComponentFixture<BzrMapComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BzrMapComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BzrMapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
