package com.sv.enviafacil.package_delivery_system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import com.sv.enviafacil.package_delivery_system.model.InformacionDeEnvio;

@Repository
public class InformacionDeEnvioRepository {
	private List<InformacionDeEnvio> envios = new ArrayList<InformacionDeEnvio>();

	public Optional<InformacionDeEnvio> buscarEnvioPorId(int id){
		return this.envios.stream().filter(e->e.getId()==id).findFirst();
	}
}
