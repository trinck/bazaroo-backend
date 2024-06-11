import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnnounceVipComponent } from './announce-vip.component';

describe('AnnounceVipComponent', () => {
  let component: AnnounceVipComponent;
  let fixture: ComponentFixture<AnnounceVipComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AnnounceVipComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AnnounceVipComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
