package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLote;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

public class ControladorLote {
    private  ServicioLote servicioLote;

    public ControladorLote(ServicioLote servicioLote) {
        this.servicioLote = servicioLote;
    }

    @RequestMapping("/irACrearLote")
    public ModelAndView irACrearLote() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("crearLote");
        return modelAndView;
    }

    public ModelAndView crearLote(LoteDto loteDto) {
        ModelAndView modelAndView = new ModelAndView();
        if (loteDto.getCodigo() == null || loteDto.getCodigo().isEmpty()) {
            modelAndView.setViewName("crearLote");
            modelAndView.addObject("mensaje", "Error al crear el lote: el código no puede estar vacío");
            return modelAndView;
        }

        try {
            servicioLote.crearLote(loteDto.obtenerEntidad());
            modelAndView.setViewName("todosLosLotes");
            modelAndView.addObject("mensaje", "Lote creado correctamente");
        } catch (Exception e) {
            modelAndView.setViewName("crearLote");
            modelAndView.addObject("mensaje", "Error al crear el lote: " + e.getMessage());
        }
        return modelAndView;
    }
}
