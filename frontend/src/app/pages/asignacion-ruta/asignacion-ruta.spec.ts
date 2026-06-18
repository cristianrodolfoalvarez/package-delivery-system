import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AsignacionRuta } from './asignacion-ruta';

describe('AsignacionRuta', () => {
  let component: AsignacionRuta;
  let fixture: ComponentFixture<AsignacionRuta>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AsignacionRuta],
    }).compileComponents();

    fixture = TestBed.createComponent(AsignacionRuta);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
