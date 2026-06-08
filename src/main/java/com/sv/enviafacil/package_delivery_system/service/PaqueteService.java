package com.sv.enviafacil.package_delivery_system.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;
import com.sv.enviafacil.package_delivery_system.dto.request.PaqueteCreateRequest;
import com.sv.enviafacil.package_delivery_system.dto.response.ClienteResponse;
import com.sv.enviafacil.package_delivery_system.model.Cliente;
import com.sv.enviafacil.package_delivery_system.model.InformacionDeEnvio;
import com.sv.enviafacil.package_delivery_system.model.Itinerario;
import com.sv.enviafacil.package_delivery_system.model.Paquete;
//import com.sv.enviafacil.package_delivery_system.model.Sucursal;
import com.sv.enviafacil.package_delivery_system.model.enums.EstadoPaquete;
import com.sv.enviafacil.package_delivery_system.repository.ClienteRepository;
import com.sv.enviafacil.package_delivery_system.repository.ItinerarioRepository;
import com.sv.enviafacil.package_delivery_system.repository.PaqueteRepository;
import com.sv.enviafacil.package_delivery_system.repository.SucursalRepository;
import com.sv.enviafacil.package_delivery_system.utils.CodigoSeguimientoGenerator;
import com.sv.enviafacil.package_delivery_system.utils.ExcepcionPersonalizada;
import com.sv.enviafacil.package_delivery_system.utils.MisValidadores;

@Service
public class PaqueteService {
	private PaqueteRepository paqueteRepository;
	private ItinerarioRepository itinerarioRepository;
	private SucursalRepository sucursalRepository;
	private ClienteRepository clienteRepository;
	private final HashMap<Integer, String> sucursalesExistentes;

	private PaqueteService(PaqueteRepository paqueteRepository, ItinerarioRepository itinerarioRepository,
			SucursalRepository sucursalRepository, ClienteRepository clienteRepository) {
		this.paqueteRepository = paqueteRepository;
		this.itinerarioRepository = itinerarioRepository;
		this.sucursalRepository = sucursalRepository;
		this.clienteRepository = clienteRepository;
		this.sucursalesExistentes = this.sucursalRepository.obtenerSucursalesDisponibles();
		inicializacionItinerarioSimulacion();
	}

	// Aqui se debe generar la instancia de : InformacionDeEnvio (LISTO), Cliente
	// (PROCESO)
	public void crearPaquete(PaqueteCreateRequest nuevoPaquete) {
		if (!verificarSucursalExistente(nuevoPaquete.sucursalOrigen())
				|| !verificarSucursalExistente(nuevoPaquete.sucursalDestino()))
			throw new ExcepcionPersonalizada("sucursal",
					"La sucursal origen y/o destino no son válidas o no están disponibles.");

		Paquete paquete = new Paquete(EstadoPaquete.PENDIENTE, nuevoPaquete.peso(), nuevoPaquete.descripcion());
		InformacionDeEnvio envio = new InformacionDeEnvio(CodigoSeguimientoGenerator.generarCodigoDeSeguimiento());
		paquete.setEnvio(envio);
		Itinerario itinerario = this.itinerarioRepository
				.verificarItinerarioExistente(nuevoPaquete.sucursalOrigen(), nuevoPaquete.sucursalDestino())
				.orElse(null);
		if (itinerario == null || itinerario.getVolumendisponible() < paquete.getPeso()) {
			itinerario = new Itinerario(this.sucursalRepository.buscarSucursal(nuevoPaquete.sucursalOrigen()).get(),
					this.sucursalRepository.buscarSucursal(nuevoPaquete.sucursalDestino()).get(), 1000, true);
			paquete.setItinerario(itinerario);
			this.paqueteRepository.guardarPaquete(paquete);
		}

		this.descontarPesoDisponible(itinerario, paquete.getPeso());
		paquete.setItinerario(itinerario);
		this.paqueteRepository.guardarPaquete(paquete);
		this.paqueteRepository.verPaquetesIngresados().forEach(p -> {
			System.out.println(p.toString());
		});
		// en esta parte el itinerario se tiene que asignar al paquete, ademas de
		// encontrar
		// alguna solucion si se puede en una var instanciar el optional.(LISTO)

		System.out.println("total de paquetes: " + this.paqueteRepository.verTotalPaquetes());
	}

	// Utilizado para dar formato a lo que se enviara para el listbox
	private ClienteResponse convertirClienteAResponse(int id, String nombres, String apellidos, String termino) {
		String infoClienteConFormato = String.join(", ", nombres, apellidos, termino);
		// StringBuilder respuestaConFormato = new StringBuilder(nombres.concat("
		// ").concat(apellidos).concat(" ").concat(termino));

		ClienteResponse clienteRespuesta = new ClienteResponse(id, infoClienteConFormato);
		return clienteRespuesta;
	}

	public ClienteResponse buscarCliente(String termino) {
		final int resultado = MisValidadores.esDUIoTelefono(termino);
		ClienteResponse clienteRespuesta;
		if (resultado == 1) {
			Cliente cliente = this.clienteRepository.buscarPorDUI(termino).orElse(null);
			if (cliente != null) {
				clienteRespuesta = convertirClienteAResponse(cliente.getId(), cliente.getNombres(),
						cliente.getApellidos(), cliente.getDui());
				return clienteRespuesta;
			}
		}
		if (resultado == 2) {
			Cliente cliente = this.clienteRepository.buscarPorTelefono(termino).orElse(null);
			if (cliente != null) {
				clienteRespuesta = convertirClienteAResponse(cliente.getId(), cliente.getNombres(),
						cliente.getApellidos(), cliente.getTelefono());
				return clienteRespuesta;
			}

		}
		// return this.clienteRepository.buscarPorId(id).orElseThrow(()->new
		// ExcepcionPersonalizada("cliente","cliente remitente y/o destinatario no
		// encontrado."));
		return null;
	}

	private boolean verificarSucursalExistente(int idSucursal) {
		System.out.println(this.sucursalesExistentes.containsKey(idSucursal));
		return this.sucursalesExistentes.containsKey(idSucursal);
	}

	private void inicializacionItinerarioSimulacion() {
		Itinerario itinerario1 = new Itinerario(this.sucursalRepository.buscarSucursal(1).get(),
				this.sucursalRepository.buscarSucursal(4).get(), 10, true);
		Itinerario itinerario2 = new Itinerario(this.sucursalRepository.buscarSucursal(2).get(),
				this.sucursalRepository.buscarSucursal(3).get(), 10, true);
		this.itinerarioRepository.crearItinerario(itinerario1);
		this.itinerarioRepository.crearItinerario(itinerario2);
	}

	private void descontarPesoDisponible(Itinerario itinerario, int peso) {
		itinerario.setVolumendisponible(itinerario.getVolumendisponible() - peso);
	}
	/*
	 * private void validarSucursalDestino() {
	 * 
	 * }
	 */
	/*
	 * private EstadoPaquete validarEstadoPaquete(String estado) { try { return
	 * EstadoPaquete.valueOf(estado.toUpperCase()); } catch
	 * (IllegalArgumentException e) { throw new
	 * ExcepcionPersonalizada("estado_paquete",
	 * "El estado del paquete no es uno válido."); } }
	 */
}
