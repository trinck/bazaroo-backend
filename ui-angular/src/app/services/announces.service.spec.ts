import { TestBed } from '@angular/core/testing';

import { AnnouncesService } from './announces.service';

describe('AnnouncesService', () => {
  let service: AnnouncesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnnouncesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
