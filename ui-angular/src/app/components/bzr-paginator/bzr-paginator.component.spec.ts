import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BzrPaginatorComponent } from './bzr-paginator.component';

describe('BzrPaginatorComponent', () => {
  let component: BzrPaginatorComponent;
  let fixture: ComponentFixture<BzrPaginatorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BzrPaginatorComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BzrPaginatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
