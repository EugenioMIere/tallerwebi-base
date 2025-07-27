package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public Cliente obtenerClientePorDni(int dni) {
    Cliente cliente = repositorioCliente.obtenerPorDni(dni);

        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado con DNI: " + dni);
        }
        return cliente;
    }

    @Override
    public String suscribirCliente(int dni, String tipoSuscripcion) {
        Cliente cliente = repositorioCliente.obtenerPorDni(dni);

        if (cliente == null) {
            cliente = new Cliente();
            cliente.setDni(dni);
            repositorioCliente.crear(cliente);
        }
        if (tipoSuscripcion.equals(cliente.getTipoSuscripcion())) {
            throw new RuntimeException("Cliente ya está suscrito al plan: " + tipoSuscripcion);

        }

        //cliente.setTipoSuscripcion(tipoSuscripcion);
        repositorioCliente.suscribir(dni, tipoSuscripcion);

        return "Suscripción ok";
    }

    @Override
    public List<Cliente> ObtenerporTipoSuscripcion(String plan) {
    List<Cliente> clientes = repositorioCliente.obtenerPorTipoSuscripcion(plan);
        if (clientes.isEmpty()) {
            throw new RuntimeException("No se encontraron clientes con tipo de suscripción: " + plan);
        }
        return clientes;
    }

    @Override
    public void eliminarSuscripcion(int dni) {
        Cliente cliente = repositorioCliente.obtenerPorDni(dni);

        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado con DNI: " + dni);
        }

        repositorioCliente.eliminar(dni);

    }

    @Override
    public List<Cliente> obtenerTodos() {
        List<Cliente> clientes = repositorioCliente.obtenerTodos();
        if (clientes.isEmpty()) {
            throw new RuntimeException("No se encontraron clientes registrados.");
        }
        return clientes;
    }


}
