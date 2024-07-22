import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TextIconComponent } from './text-icon.component';

describe('TextIconComponent', () => {
  let component: TextIconComponent;
  let fixture: ComponentFixture<TextIconComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TextIconComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TextIconComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
