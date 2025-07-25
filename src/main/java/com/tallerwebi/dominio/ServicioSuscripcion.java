package com.tallerwebi.dominio;

import javax.transaction.Transactional;

@Transactional
public interface ServicioSuscripcion {
    Boolean registrarCliente(Cliente nuevoCliente);

    Cliente obtenerClientePorDni(int dni);

    void actualizarCliente(Cliente cliente);
}
