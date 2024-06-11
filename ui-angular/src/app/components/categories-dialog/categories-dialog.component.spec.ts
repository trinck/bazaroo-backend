import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoriesDialogComponent } from './categories-dialog.component';

describe('CategoriesDialogComponent', () => {
  let component: CategoriesDialogComponent;
  let fixture: ComponentFixture<CategoriesDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CategoriesDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CategoriesDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
