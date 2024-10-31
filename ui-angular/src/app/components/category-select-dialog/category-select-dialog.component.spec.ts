import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategorySelectDialogComponent } from './category-select-dialog.component';

describe('CategorySelectDialogComponent', () => {
  let component: CategorySelectDialogComponent;
  let fixture: ComponentFixture<CategorySelectDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CategorySelectDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CategorySelectDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
