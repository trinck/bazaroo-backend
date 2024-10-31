import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MediasUploaderComponent } from './medias-uploader.component';

describe('MediasUploaderComponent', () => {
  let component: MediasUploaderComponent;
  let fixture: ComponentFixture<MediasUploaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MediasUploaderComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MediasUploaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
