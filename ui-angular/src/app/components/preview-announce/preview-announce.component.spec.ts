import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviewAnnounceComponent } from './preview-announce.component';

describe('PreviewAnnounceComponent', () => {
  let component: PreviewAnnounceComponent;
  let fixture: ComponentFixture<PreviewAnnounceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PreviewAnnounceComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PreviewAnnounceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
