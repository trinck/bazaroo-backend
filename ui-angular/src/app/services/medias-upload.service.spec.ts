import { TestBed } from '@angular/core/testing';

import { MediasUploadService } from './medias-upload.service';

describe('MediasUploadService', () => {
  let service: MediasUploadService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MediasUploadService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
