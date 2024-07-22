import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnnounceViewComponent } from './announce-view.component';

describe('AnnounceViewComponent', () => {
  let component: AnnounceViewComponent;
  let fixture: ComponentFixture<AnnounceViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AnnounceViewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AnnounceViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
