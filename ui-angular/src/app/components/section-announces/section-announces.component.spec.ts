import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SectionAnnouncesComponent } from './section-announces.component';

describe('SectionAnnouncesComponent', () => {
  let component: SectionAnnouncesComponent;
  let fixture: ComponentFixture<SectionAnnouncesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SectionAnnouncesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SectionAnnouncesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
