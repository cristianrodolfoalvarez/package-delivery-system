package com.sv.enviafacil.package_delivery_system.service;

import org.springframework.stereotype.Service;

import com.sv.enviafacil.package_delivery_system.repository.InformacionDeEnvioRepository;

@Service
public class InformacionDeEnvioService {
	private InformacionDeEnvioRepository informacionDeEnvioRepository;
	
	public InformacionDeEnvioService(InformacionDeEnvioRepository informacionDeEnvioRepository) {
		this.informacionDeEnvioRepository = informacionDeEnvioRepository;
	}
	
}
