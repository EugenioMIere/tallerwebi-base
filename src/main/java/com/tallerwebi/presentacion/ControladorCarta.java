package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioCartaImpl;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;


public class ControladorCarta {

    private final ServicioCartaImpl servicioCarta;
    public ControladorCarta(ServicioCartaImpl servicioCarta) {
        this.servicioCarta = servicioCarta;
    }

    public ModelAndView crearCarta(CartaDto carta) {
        ModelMap modelMap = new ModelMap();

        Boolean creada = servicioCarta.crear(carta);
        String mensaje = "Error al crear la carta";

        if (creada) {
            mensaje = "Carta creado correctamente";
        }

        modelMap.put("mensaje", mensaje);

        return new ModelAndView("crear-carta", modelMap);
    }
}
