import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BanierPubComponent } from './banier-pub.component';

describe('BanierPubComponent', () => {
  let component: BanierPubComponent;
  let fixture: ComponentFixture<BanierPubComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BanierPubComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BanierPubComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
