package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Cliente;
import com.tallerwebi.dominio.ServicioSuscripcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorSuscripcion {
    private ServicioSuscripcion servicioSuscripcion;

    @Autowired
    public ControladorSuscripcion(ServicioSuscripcion servicioSuscripcion) {
        this.servicioSuscripcion = servicioSuscripcion;
    }

    @RequestMapping("/suscripcion")
    public ModelAndView irAPaginaSuscripcion(@RequestParam(value = "exito", required = false) String exito) {
        ModelMap modelo = new ModelMap();
        if (exito != null) {
            modelo.put("mensaje", "Suscripción exitosa");
        }
        return new ModelAndView("suscripcion", modelo);
    }

    @RequestMapping(value = "/suscribir-cliente", method = RequestMethod.POST)
    public ModelAndView suscribirCliente(int dni, String suscripcion) {
        try {
            servicioSuscripcion.suscribirCliente(dni, suscripcion);
            return new ModelAndView("redirect:/suscripcion?exito");
        } catch (RuntimeException e) {
            ModelMap modelo = new ModelMap();
            modelo.put("error", e.getMessage());
            modelo.put("dni", dni);
            modelo.put("suscripcionSeleccionada", suscripcion);
            return new ModelAndView("suscripcion", modelo);
        }
    }

    @RequestMapping(value = "/tipo-suscripcion", method = RequestMethod.POST)
    public ModelAndView obtenerporTipoSuscripcion(String tipoSuscripcion) {
        ModelMap model = new ModelMap();
        try {
            List<Cliente> clientes = this.servicioSuscripcion.ObtenerporTipoSuscripcion(tipoSuscripcion);
            model.put("clientes", clientes);
        } catch (RuntimeException e) {
            model.put("error", e.getMessage());
        }
        if (tipoSuscripcion == null) {
            return new ModelAndView("redirect:/obtener-todos");
        }
        return new ModelAndView("historial-suscripcion", model);

    }

    @RequestMapping(value = "/eliminar-suscripcion", method = RequestMethod.POST)
    public ModelAndView eliminarSuscripcion(int dni) {
        this.servicioSuscripcion.eliminarSuscripcion(dni);
        return new ModelAndView("redirect:/obtener-todos");
    }

    @RequestMapping("/obtener-todos")
    public ModelAndView obtenerTodos() {
        ModelMap modelo = new ModelMap();
        try {
            List<Cliente> clientes = this.servicioSuscripcion.obtenerTodos();
            modelo.put("clientes", clientes);
        } catch (RuntimeException e) {
            modelo.put("error", e.getMessage());
        }
        return new ModelAndView("historial-suscripcion", modelo);
    }
}
