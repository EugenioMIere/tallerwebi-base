package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Cliente;
import com.tallerwebi.dominio.ServicioSuscripcion;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public class ControladorSuscripcion {
    private  ServicioSuscripcion servicioSuscripcion;

    public ControladorSuscripcion(ServicioSuscripcion servicioSuscripcion) {
        this.servicioSuscripcion = servicioSuscripcion;
    }

    public ModelAndView suscribirCliente(int dni, String suscripcion) {
        this.servicioSuscripcion.suscribirCliente(dni, suscripcion);

        return new ModelAndView("historial-suscripcion");
    }

    public ModelAndView obtenerporTipoSuscripcion(String tipoSuscripcion) {

        List<Cliente> clientes = this.servicioSuscripcion.ObtenerporTipoSuscripcion(tipoSuscripcion);
        if (clientes == null || clientes.isEmpty()) {
            return new ModelAndView("historial-suscripcion", new ModelMap("error", "No se encontraron clientes con la suscripción: " + tipoSuscripcion));
        }
        ModelMap clienteModel = new ModelMap();
        clienteModel.put("clientes", clientes);

        return new ModelAndView("historial-suscripcion", clienteModel);

    }

    public ModelAndView eliminarSuscripcion(int dni) {
        this.servicioSuscripcion.eliminarSuscripcion(dni);
        ModelMap model = new ModelMap();
        model.put("mensaje", "Suscripción eliminada correctamente");
        return new ModelAndView("historial-suscripcion", model);
    }

    public ModelAndView obtenerTodos() {
        List<Cliente> clientes = this.servicioSuscripcion.obtenerTodos();
        if (clientes == null || clientes.isEmpty()) {
            return new ModelAndView("historial-suscripcion", new ModelMap("error", "No se encontraron clientes"));
        }
        ModelMap clienteModel = new ModelMap();
        clienteModel.put("clientes", clientes);

        return new ModelAndView("historial-suscripcion", clienteModel);
    }
}
