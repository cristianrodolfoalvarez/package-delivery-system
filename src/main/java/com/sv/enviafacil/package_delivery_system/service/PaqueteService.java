package com.sv.enviafacil.package_delivery_system.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sv.enviafacil.package_delivery_system.dto.request.ActualizarEstadoRequest;
import com.sv.enviafacil.package_delivery_system.dto.request.PaqueteCreateRequest;
import com.sv.enviafacil.package_delivery_system.dto.response.ClienteResponse;
import com.sv.enviafacil.package_delivery_system.dto.response.PaqueteResponse;
import com.sv.enviafacil.package_delivery_system.dto.response.PuntoTransitoResponse;
import com.sv.enviafacil.package_delivery_system.model.Cliente;
import com.sv.enviafacil.package_delivery_system.model.InformacionDeEnvio;
import com.sv.enviafacil.package_delivery_system.model.Itinerario;
import com.sv.enviafacil.package_delivery_system.model.Paquete;
import com.sv.enviafacil.package_delivery_system.model.PuntoDeTransito;
import com.sv.enviafacil.package_delivery_system.model.Sucursal;
import com.sv.enviafacil.package_delivery_system.model.Usuario;
import com.sv.enviafacil.package_delivery_system.model.enums.EstadoPaquete;
import com.sv.enviafacil.package_delivery_system.repository.ClienteRepository;
import com.sv.enviafacil.package_delivery_system.repository.ItinerarioRepository;
import com.sv.enviafacil.package_delivery_system.repository.PaqueteRepository;
import com.sv.enviafacil.package_delivery_system.repository.SucursalRepository;
import com.sv.enviafacil.package_delivery_system.repository.UsuarioRepository;
import com.sv.enviafacil.package_delivery_system.utils.CodigoSeguimientoGenerator;
import com.sv.enviafacil.package_delivery_system.utils.ExcepcionPersonalizada;
import com.sv.enviafacil.package_delivery_system.utils.MisValidadores;

@Service
public class PaqueteService {

	private final PaqueteRepository paqueteRepository;
	private final ItinerarioRepository itinerarioRepository;
	private final SucursalRepository sucursalRepository;
	private final ClienteRepository clienteRepository;
	private final UsuarioRepository usuarioRepository;
	private final HashMap<Integer, String> sucursalesExistentes;

	public PaqueteService(PaqueteRepository paqueteRepository, ItinerarioRepository itinerarioRepository,
			SucursalRepository sucursalRepository, ClienteRepository clienteRepository, UsuarioRepository usuarioRepository) {
		this.paqueteRepository = paqueteRepository;
		this.itinerarioRepository = itinerarioRepository;
		this.sucursalRepository = sucursalRepository;
		this.clienteRepository = clienteRepository;
		this.usuarioRepository = usuarioRepository;
		this.sucursalesExistentes = this.sucursalRepository.obtenerSucursalesDisponibles();
		inicializacionItinerarioSimulacion();
	}
	// service/PaqueteService.java - Agregar este método

	/**
	 * Cambia el estado de un paquete y registra un punto de transito
	 */
	public boolean actualizarEstadoPaquete(int id, ActualizarEstadoRequest request) {
	    System.out.println("=== ACTUALIZANDO ESTADO DE PAQUETE ===");
	    System.out.println("ID Paquete: " + id);
	    System.out.println("Nuevo Estado: " + request.nuevoEstado());
	    System.out.println("Sucursal ID: " + request.sucursalId());
	    
	    // Buscar el paquete
	    Paquete paquete = this.paqueteRepository.buscarPorId(id).orElse(null);
	    if (paquete == null) {
	        System.out.println("ERROR: Paquete no encontrado con ID: " + id);
	        return false;
	    }
	    
	    // Validar el nuevo estado
	    EstadoPaquete nuevoEstado;
	    try {
	        nuevoEstado = EstadoPaquete.fromString(request.nuevoEstado());
	    } catch (IllegalArgumentException e) {
	        System.out.println("ERROR: Estado invalido: " + request.nuevoEstado());
	        return false;
	    }
	    
	    // Validar que el estado actual sea diferente al nuevo
	    if (paquete.getEstado() == nuevoEstado) {
	        System.out.println("El paquete ya tiene el estado: " + nuevoEstado);
	        return true;
	    }
	    
	    // Obtener la sucursal
	    Sucursal sucursal = this.sucursalRepository.buscarSucursal(request.sucursalId()).orElse(null);
	    if (sucursal == null) {
	        System.out.println("ERROR: Sucursal no encontrada con ID: " + request.sucursalId());
	        return false;
	    }
	    
	    // Registrar punto de transito
	    PuntoDeTransito punto = new PuntoDeTransito(new Date(), sucursal);
	    paquete.agregarPuntoDeTransito(punto);
	    
	    // Actualizar sucursal actual del paquete
	    paquete.setSucursalActual(sucursal);
	    
	    // Si el estado es ENTREGADO, la ubicacion actual es la sucursal destino
	    if (nuevoEstado == EstadoPaquete.ENTREGADO) {
	        System.out.println("Paquete entregado en: " + sucursal.getNombre());
	    }
	    
	    // Actualizar el estado del paquete
	    paquete.setEstado(nuevoEstado);
	    
	    System.out.println("Punto de transito registrado en: " + sucursal.getNombre());
	    System.out.println("Nuevo estado: " + nuevoEstado.getDescripcion());
	    System.out.println("Total de puntos de transito: " + paquete.getPuntosDeTransito().size());
	    
	    return true;
	}
	
	
	/**
	 * Crea un nuevo paquete en el sistema
	 */
	public boolean crearPaquete(PaqueteCreateRequest nuevoPaquete) {
		System.out.println("=== INICIANDO CREACIÓN DE PAQUETE ===");
		final int PESO_TOTAL = nuevoPaquete.peso()*nuevoPaquete.cantidad();
		
	    Usuario usuarioRegistro = null;
	    if (nuevoPaquete.usuarioId() != null) {
	        usuarioRegistro = this.usuarioRepository.obtenerUsuario(nuevoPaquete.usuarioId()).orElse(null);
	        if (usuarioRegistro == null) {
	        	return false;//throw new ExcepcionPersonalizada("usuario", "Usuario no encontrado con ID: " + nuevoPaquete.usuarioId());
	        }
	        System.out.println("Usuario que registra: " + usuarioRegistro.getNombreUsuario());
	    } else {
	      return false; // System.out.println("No se especificó usuario que registra el paquete");
	    }
		if (Itinerario.PESO_MAXIMO < PESO_TOTAL)
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
		Itinerario itinerario = validarItinerario(nuevoPaquete.sucursalOrigen(), nuevoPaquete.sucursalDestino(),
				PESO_TOTAL);
		Paquete paquete = new Paquete(EstadoPaquete.PENDIENTE, nuevoPaquete.peso(), nuevoPaquete.descripcion(),
				remitente, destinatario, nuevoPaquete.precio(), nuevoPaquete.cantidad());
		paquete.setItinerario(itinerario);
		paquete.setUsuarioRegistro(usuarioRegistro);
		InformacionDeEnvio envio = new InformacionDeEnvio(CodigoSeguimientoGenerator.generarCodigoDeSeguimiento());
		paquete.setEnvio(envio);

		boolean guardado = this.paqueteRepository.guardarPaquete(paquete);
		if (guardado) {
			// 9. Descontar peso disponible del itinerario
			// this.itinerarioRepository.
			descontarPesoDisponible(itinerario, PESO_TOTAL);

			// 10. Mostrar información de confirmación
			System.out.println("PAQUETE REGISTRADO");
			System.out.println("ID del paquete: " + paquete.getId());
			System.out.println("Código de seguimiento: " + envio.getCodSeguimiento());
			System.out.println("Remitente: " + remitente.getNombres() + " " + remitente.getApellidos());
			System.out.println("Destinatario: " + destinatario.getNombres() + " " + destinatario.getApellidos());
			System.out.println("Peso: " + paquete.getPeso() + " kg");
		    System.out.println("Cantidad paquetes: " + paquete.getCantidad());
		    System.out.println("Precio unitario: " + paquete.getPrecioUnit());
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
	}*/

	/**
	 * Asigna o crea un itinerario
	 */
	private Itinerario validarItinerario(int sucursalOrigen, int sucursalDestino, int peso) {
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
	
	// service/PaqueteService.java - Método convertirPaqueteAResponse
	public List<PaqueteResponse> obtenerTodosLosPaquetes() {
	    System.out.println("=== OBTENIENDO TODOS LOS PAQUETES ===");
	    List<Paquete> paquetes = this.paqueteRepository.verPaquetesIngresados();
	    List<PaqueteResponse> response = new ArrayList<>();
	    
	    for (Paquete paquete : paquetes) {
	        response.add(convertirPaqueteAResponse(paquete));
	    }
	    
	    System.out.println("Total de paquetes encontrados: " + response.size());
	    return response;
	}
	public PaqueteResponse buscarPaquetePorCodigo(String codigo) {
	    System.out.println("Buscando paquete por codigo: " + codigo);
	    
	    List<Paquete> paquetes = this.paqueteRepository.verPaquetesIngresados();
	    
	    for (Paquete paquete : paquetes) {
	        if (paquete.getEnvio() != null && 
	            paquete.getEnvio().getCodSeguimiento().equalsIgnoreCase(codigo)) {
	            System.out.println("Paquete encontrado: " + paquete.getId());
	            return convertirPaqueteAResponse(paquete);
	        }
	    }
	    
	    System.out.println("Paquete no encontrado con codigo: " + codigo);
	    return null;
	}
	private PaqueteResponse convertirPaqueteAResponse(Paquete paquete) {
	    System.out.println("Convirtiendo paquete a response: " + paquete.getId());
	    
	    // Datos del remitente
	    String remitente = "";
	    if (paquete.getRemitente() != null) {
	        remitente = paquete.getRemitente().getNombres() + " " + paquete.getRemitente().getApellidos();
	    }
	    
	    // Datos del destinatario
	    String destinatario = "";
	    if (paquete.getDestinatario() != null) {
	        destinatario = paquete.getDestinatario().getNombres() + " " + paquete.getDestinatario().getApellidos();
	    }
	    
	    // Datos del itinerario
	    String sucursalOrigen = "";
	    String sucursalDestino = "";
	    String ubicacionActual = "";
	    
	    if (paquete.getItinerario() != null) {
	        if (paquete.getItinerario().getSucursalOrigen() != null) {
	            sucursalOrigen = paquete.getItinerario().getSucursalOrigen().getNombre();
	        }
	        if (paquete.getItinerario().getSucursalDestino() != null) {
	            sucursalDestino = paquete.getItinerario().getSucursalDestino().getNombre();
	        }
	    }
	    
	    // Ubicacion actual (sucursal actual o la de origen)
	    if (paquete.getSucursalActual() != null) {
	        ubicacionActual = paquete.getSucursalActual().getNombre();
	    } else {
	        ubicacionActual = sucursalOrigen;
	    }
	    
	    // Codigo de seguimiento
	    String codigoSeguimiento = "";
	    if (paquete.getEnvio() != null) {
	        codigoSeguimiento = paquete.getEnvio().getCodSeguimiento();
	    }
	    
	    // Estado del paquete
	    String estado = "";
	    if (paquete.getEstado() != null) {
	        estado = paquete.getEstado().name(); // Devuelve PENDIENTE, EN_RUTA, etc.
	    }
	    
	    // Convertir puntos de transito
	    List<PuntoTransitoResponse> puntosResponse = new ArrayList<>();
	    if (paquete.getPuntosDeTransito() != null && !paquete.getPuntosDeTransito().isEmpty()) {
	        for (PuntoDeTransito punto : paquete.getPuntosDeTransito()) {
	            String estadoPunto = paquete.getEstado() != null ? 
	                paquete.getEstado().getDescripcion() : "Sin estado";
	            puntosResponse.add(PuntoTransitoResponse.fromModel(
	                punto.getFechaRecepcion(),
	                punto.getSucursalRecepcionTemporal().getNombre(),
	                estadoPunto
	            ));
	        }
	    }
	    
	    return new PaqueteResponse(
	        paquete.getId(),
	        codigoSeguimiento,
	        remitente,
	        destinatario,
	        estado,
	        sucursalOrigen,
	        sucursalDestino,
	        ubicacionActual,
	        puntosResponse
	    );
	}
}
