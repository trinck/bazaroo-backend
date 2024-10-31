import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MediasViewSlideComponent } from './medias-view-slide.component';

describe('MediasViewSlideComponent', () => {
  let component: MediasViewSlideComponent;
  let fixture: ComponentFixture<MediasViewSlideComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MediasViewSlideComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MediasViewSlideComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
