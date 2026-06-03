import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrarPaquete } from './registrar-paquete';

describe('RegistrarPaquete', () => {
  let component: RegistrarPaquete;
  let fixture: ComponentFixture<RegistrarPaquete>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RegistrarPaquete],
    }).compileComponents();

    fixture = TestBed.createComponent(RegistrarPaquete);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
