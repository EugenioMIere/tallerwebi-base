package com.tallerwebi.dominio;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ServicioSuscripcion {
    Boolean registrarCliente(Cliente nuevoCliente);

    Cliente obtenerClientePorDni(int dni);


    String suscribirCliente(int dni, String tipoSuscripcion);


    List<Cliente> ObtenerporTipoSuscripcion(String básica);

    void eliminarSuscripcion(int dni);

    List<Cliente> obtenerTodos();
}
