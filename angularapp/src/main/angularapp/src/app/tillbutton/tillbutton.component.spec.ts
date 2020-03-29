import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TillbuttonComponent } from './tillbutton.component';

describe('TillbuttonComponent', () => {
  let component: TillbuttonComponent;
  let fixture: ComponentFixture<TillbuttonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TillbuttonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TillbuttonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
