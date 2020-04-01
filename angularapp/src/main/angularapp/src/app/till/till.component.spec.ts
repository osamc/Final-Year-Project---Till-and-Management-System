import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TillComponent } from './till.component';

describe('TillComponent', () => {
  let component: TillComponent;
  let fixture: ComponentFixture<TillComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TillComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TillComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
