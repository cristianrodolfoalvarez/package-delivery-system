/*package com.sv.enviafacil.package_delivery_system.utils;

import java.util.Set;

import org.springframework.stereotype.Controller;

import com.sv.enviafacil.package_delivery_system.dto.request.SucursalCreateRequest;

import jakarta.validation.ConstraintViolation;
//import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

//import jakarta.validation.Valid;

@Controller
public class SucursalController {
	public void CrearSucursal(/*@Valid SucursalCreateRequest sucursal/) {
		SucursalCreateRequest sucursal = new SucursalCreateRequest("", "", "");
		System.out.println("entra");
	}
	public static void main(String[] args) {
		SucursalCreateRequest sucursal = new SucursalCreateRequest("x", "x", "2226-2323");
		ValidatorFactory factory =  Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		System.out.println(sucursal.toString());
		Set<ConstraintViolation<SucursalCreateRequest>> violaciones = validator.validate(sucursal);
		if(violaciones.isEmpty()) {
			System.out.println("Informacion valida");
			System.out.println(sucursal.toString());
		}
		else {
			violaciones.forEach(v->{System.out.printf("   • %s: %s (valor actual: '%s')%n:",v.getPropertyPath(),v.getMessage(), v.getInvalidValue());});
		}
	}
}
*/