package com.sv.enviafacil.package_delivery_system.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.sv.enviafacil.package_delivery_system.dto.request.PaqueteCreateRequest;
import com.sv.enviafacil.package_delivery_system.dto.response.ClienteResponse;
import com.sv.enviafacil.package_delivery_system.model.Cliente;
import com.sv.enviafacil.package_delivery_system.model.InformacionDeEnvio;
import com.sv.enviafacil.package_delivery_system.model.Itinerario;
import com.sv.enviafacil.package_delivery_system.model.Paquete;
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

	private final PaqueteRepository paqueteRepository;
	private final ItinerarioRepository itinerarioRepository;
	private final SucursalRepository sucursalRepository;
	private final ClienteRepository clienteRepository;
	private final HashMap<Integer, String> sucursalesExistentes;

	public PaqueteService(PaqueteRepository paqueteRepository, ItinerarioRepository itinerarioRepository,
			SucursalRepository sucursalRepository, ClienteRepository clienteRepository) {
		this.paqueteRepository = paqueteRepository;
		this.itinerarioRepository = itinerarioRepository;
		this.sucursalRepository = sucursalRepository;
		this.clienteRepository = clienteRepository;
		this.sucursalesExistentes = this.sucursalRepository.obtenerSucursalesDisponibles();
		inicializacionItinerarioSimulacion();
	}

	/**
	 * Crea un nuevo paquete en el sistema
	 */
	public boolean crearPaquete(PaqueteCreateRequest nuevoPaquete) {
		System.out.println("=== INICIANDO CREACIÓN DE PAQUETE ===");
		if (Itinerario.PESO_MAXIMO < nuevoPaquete.peso())
			return false;
		if (!validarSucursales(nuevoPaquete.sucursalOrigen(), nuevoPaquete.sucursalDestino()))
			return false;
		Cliente remitente = buscarClientePorIdentificador(nuevoPaquete.remitenteDUIOTelefono(), "remitente");
		if (remitente == null)
			return false;// throw new ExcepcionPersonalizada("remitente", "Error, remitente no
							// encontrado.");
		System.out.println("Remitente encontrado: " + remitente.getNombres() + " " + remitente.getApellidos());

		Cliente destinatario = buscarClientePorIdentificador(nuevoPaquete.destinatarioDUIOTelefono(), "destinatario");
		if (destinatario == null)
			return false;// throw new ExcepcionPersonalizada("remitente", "Error, remitente no
							// encontrado.");
		System.out.println("Destinatario encontrado: " + destinatario.getNombres() + " " + destinatario.getApellidos());
		Itinerario itinerario = asignarItinerario(nuevoPaquete.sucursalOrigen(), nuevoPaquete.sucursalDestino(),
				nuevoPaquete.peso());
		Paquete paquete = new Paquete(EstadoPaquete.PENDIENTE, nuevoPaquete.peso(), nuevoPaquete.descripcion());
		paquete.setItinerario(itinerario);
		paquete.setRemitente(remitente);
		paquete.setDestinatario(destinatario);

		InformacionDeEnvio envio = new InformacionDeEnvio(CodigoSeguimientoGenerator.generarCodigoDeSeguimiento());
		paquete.setEnvio(envio);

		boolean guardado = this.paqueteRepository.guardarPaquete(paquete);
		if (guardado) {
			// 9. Descontar peso disponible del itinerario
			// this.itinerarioRepository.
			descontarPesoDisponible(itinerario, paquete.getPeso());

			// 10. Mostrar información de confirmación
			System.out.println("PAQUETE REGISTRADO");
			System.out.println("ID del paquete: " + paquete.getId());
			System.out.println("Código de seguimiento: " + envio.getCodSeguimiento());
			System.out.println("Remitente: " + remitente.getNombres() + " " + remitente.getApellidos());
			System.out.println("Destinatario: " + destinatario.getNombres() + " " + destinatario.getApellidos());
			System.out.println("Peso: " + paquete.getPeso() + " kg");
			System.out.println("Estado: " + paquete.getEstado());
			System.out.println("Itinerario: " + itinerario.getSucursalOrigen().getNombre() + " -> "
					+ itinerario.getSucursalDestino().getNombre());
			System.out.println("Total de paquetes en sistema: " + this.paqueteRepository.verTotalPaquetes());
		} else
			return false;// throw new ExcepcionPersonalizada("repositorio", "Error al guardar el paquete
							// en el repositorio");
		return true;
	}

	/**
	 * Valida que las sucursales existan y estén disponibles
	 */
	private boolean validarSucursales(int sucursalOrigen, int sucursalDestino) {
		if (sucursalOrigen == sucursalDestino || !verificarSucursalExistente(sucursalOrigen)
				|| !verificarSucursalExistente(sucursalDestino))
			return false;
		return true;
	}

	/**
	 * Busca un cliente por DUI o teléfono
	 */
	private Cliente buscarClientePorIdentificador(String duiOTelefono, String tipo) {
		if (duiOTelefono == null || duiOTelefono.trim().isEmpty()) {
			throw new ExcepcionPersonalizada("cliente", "El " + tipo + " no puede estar vacío.");
		}

		final int resultado = MisValidadores.esDUIoTelefono(duiOTelefono);
		Cliente cliente = null;

		if (resultado == 1) {
			cliente = this.clienteRepository.buscarPorDUI(duiOTelefono).orElse(null);
		} else if (resultado == 2) {
			cliente = this.clienteRepository.buscarPorTelefono(duiOTelefono).orElse(null);
		} else {
			throw new ExcepcionPersonalizada("cliente",
					"Formato inválido para " + tipo + ". Debe ser DUI (9 dígitos) o teléfono (8 dígitos).");
		}

		if (cliente == null) {
			throw new ExcepcionPersonalizada("cliente",
					"No se encontró un cliente con " + (resultado == 1 ? "DUI" : "teléfono") + ": " + duiOTelefono);
		}

		return cliente;
	}

	/**
	 * Valida y convierte el estado del paquete
	 */
	private EstadoPaquete validarEstadoPaquete(String estado) {
		if (estado == null || estado.trim().isEmpty()) {
			return EstadoPaquete.PENDIENTE;
		}

		try {
			return EstadoPaquete.valueOf(estado.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new ExcepcionPersonalizada("estado_paquete",
					"Estado inválido. Valores permitidos: PENDIENTE, EN_RUTA, ENTREGADO");
		}
	}

	/**
	 * Asigna o crea un itinerario
	 */
	private Itinerario asignarItinerario(int sucursalOrigen, int sucursalDestino, int peso) {
		Itinerario itinerario = this.itinerarioRepository.verificarItinerarioExistente(sucursalOrigen, sucursalDestino)
				.orElse(null);
		if (itinerario == null) {// si no existe se crea
			System.out.println("No existe itinerario con esa ruta, se creará uno nuevo");
			itinerario = new Itinerario(this.sucursalRepository.buscarSucursal(sucursalOrigen).get(),
					this.sucursalRepository.buscarSucursal(sucursalDestino).get(), Itinerario.PESO_MAXIMO, true);
			this.itinerarioRepository.crearItinerario(itinerario);
			System.out.println("Itinerario creado con capacidad: " + itinerario.getVolumendisponible() + " kg");
		}

		if (itinerario.getVolumendisponible() < peso) {// se crea uuno nuevo por capacidad insuficiente.
			System.out.println("Capacidad insuficiente en itinerario existente.");
			itinerario = new Itinerario(this.sucursalRepository.buscarSucursal(sucursalOrigen).get(),
					this.sucursalRepository.buscarSucursal(sucursalDestino).get(), Itinerario.PESO_MAXIMO, true);
			this.itinerarioRepository.crearItinerario(itinerario);
			System.out.println("Nuevo itinerario creado con capacidad: " + itinerario.getVolumendisponible() + " kg");
		}
		return itinerario;
	}

	/**
	 * Busca un cliente para el formulario (retorna respuesta formateada)
	 */
	public ClienteResponse buscarCliente(String termino) {
		System.out.println("Buscando cliente con término: " + termino);

		if (termino == null || termino.trim().isEmpty()) {
			return null;
		}

		final int resultado = MisValidadores.esDUIoTelefono(termino);
		Cliente cliente = null;

		if (resultado == 1) {
			cliente = this.clienteRepository.buscarPorDUI(termino).orElse(null);
			if (cliente != null) {
				return convertirClienteAResponse(cliente.getId(), cliente.getNombres(), cliente.getApellidos(),
						cliente.getDui());
			}
		} else if (resultado == 2) {
			cliente = this.clienteRepository.buscarPorTelefono(termino).orElse(null);
			if (cliente != null) {
				return convertirClienteAResponse(cliente.getId(), cliente.getNombres(), cliente.getApellidos(),
						cliente.getTelefono());
			}
		}
		return null;
	}

	/**
	 * Convierte cliente a formato de respuesta
	 */
	private ClienteResponse convertirClienteAResponse(int id, String nombres, String apellidos, String termino) {
		String infoClienteConFormato = String.join(", ", nombres, apellidos, termino);
		return new ClienteResponse(id, infoClienteConFormato);
	}

	/**
	 * Verifica si una sucursal existe
	 */
	private boolean verificarSucursalExistente(int idSucursal) {
		return this.sucursalesExistentes.containsKey(idSucursal);
	}

	/**
	 * Inicializa itinerarios de simulación
	 */
	private void inicializacionItinerarioSimulacion() {
		if (this.sucursalRepository.buscarSucursal(1).isPresent()
				&& this.sucursalRepository.buscarSucursal(4).isPresent()) {
			Itinerario itinerario1 = new Itinerario(this.sucursalRepository.buscarSucursal(1).get(),
					this.sucursalRepository.buscarSucursal(4).get(), 1000, true);
			this.itinerarioRepository.crearItinerario(itinerario1);
		}

		if (this.sucursalRepository.buscarSucursal(2).isPresent()
				&& this.sucursalRepository.buscarSucursal(3).isPresent()) {
			Itinerario itinerario2 = new Itinerario(this.sucursalRepository.buscarSucursal(2).get(),
					this.sucursalRepository.buscarSucursal(3).get(), 1000, true);
			this.itinerarioRepository.crearItinerario(itinerario2);
		}
	}

	/**
	 * Descuenta peso del itinerario
	 */
	private void descontarPesoDisponible(Itinerario itinerario, int peso) {
		itinerario.setVolumendisponible(itinerario.getVolumendisponible() - peso);
		System.out.println("Peso descontado. Disponible restante: " + itinerario.getVolumendisponible() + " kg");
	}
}
