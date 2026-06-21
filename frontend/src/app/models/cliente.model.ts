export interface Cliente {
    id: number;
    correo: string;
    nombres: string;
    apellidos: string;
    telefono: string;
    dui: string;
    activo: boolean;
}

export interface ClienteCreateRequest {
    correo: string;
    nombres: string;
    apellidos: string;
    telefono: string;
    dui: string;
    activo: boolean;
}