package com.sv.enviafacil.package_delivery_system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

/*import com.sv.enviafacil.package_delivery_system.model.Itinerario;
import com.sv.enviafacil.package_delivery_system.model.Sucursal;*/
import com.sv.enviafacil.package_delivery_system.repository.SucursalRepository;

@Service
public class ItinerarioService {
	HashMap<Integer,String> sucursales = new SucursalRepository().obtenerSucursalesDisponibles();
}
