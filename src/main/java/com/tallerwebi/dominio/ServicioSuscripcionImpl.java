package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

@Service
public class ServicioSuscripcionImpl implements ServicioSuscripcion {
    private RepositorioCliente repositorioCliente;

    public ServicioSuscripcionImpl(RepositorioCliente repositorioCliente) {
        this.repositorioCliente = repositorioCliente;
    }

    @Override
    public Boolean registrarCliente(Cliente nuevoCliente) {
        Cliente clienteExistente = repositorioCliente.obtenerPorDni(nuevoCliente.getDni());

        if (clienteExistente != null ) {
            return false;
        }

        repositorioCliente.crear(nuevoCliente);

        return true;
    }
}
