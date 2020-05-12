import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JwtuserComponent } from './jwtuser.component';

describe('JwtuserComponent', () => {
  let component: JwtuserComponent;
  let fixture: ComponentFixture<JwtuserComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JwtuserComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JwtuserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
