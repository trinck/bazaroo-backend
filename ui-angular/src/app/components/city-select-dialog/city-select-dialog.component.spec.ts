import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CitySelectDialogComponent } from './city-select-dialog.component';

describe('CitySelectDialogComponent', () => {
  let component: CitySelectDialogComponent;
  let fixture: ComponentFixture<CitySelectDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CitySelectDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CitySelectDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
