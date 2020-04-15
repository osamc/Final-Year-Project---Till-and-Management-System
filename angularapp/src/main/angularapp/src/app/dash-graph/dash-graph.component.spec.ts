import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DashGraphComponent } from './dash-graph.component';

describe('DashGraphComponent', () => {
  let component: DashGraphComponent;
  let fixture: ComponentFixture<DashGraphComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DashGraphComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DashGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
