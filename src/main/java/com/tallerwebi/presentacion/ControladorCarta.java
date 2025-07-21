package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioCarta;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;


public class ControladorCarta {

    private final ServicioCarta servicioCarta;
    public ControladorCarta(ServicioCarta servicioCarta) {
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
