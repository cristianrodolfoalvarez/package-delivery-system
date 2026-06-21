export interface PuntoTransito {
    fecha: string;
    sucursal: string;
    estado: string;
}

export interface Paquete {
    id: number;
    codigoSeguimiento: string;
    remitente: string;
    destinatario: string;
    estado: string;
    sucursalOrigen: string;
    sucursalDestino: string;
    ubicacionActual: string;
    puntosDeTransito: PuntoTransito[];
}