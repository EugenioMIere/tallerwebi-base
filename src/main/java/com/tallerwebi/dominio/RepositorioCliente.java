package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioCliente {
    Boolean crear(Cliente cliente);

    Cliente obtenerPorDni(int dni);

    List<Cliente> obtenerPorTipoSuscripcion(String tipoSuscripcion);

    List<Cliente> obtenerTodos();

    void eliminar(Long id);
}
