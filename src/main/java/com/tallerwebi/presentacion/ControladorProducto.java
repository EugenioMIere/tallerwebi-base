package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.ServicioProducto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorProducto {
    private ServicioProducto servicioProducto;
    public ControladorProducto(ServicioProducto servicioProducto) {
        this.servicioProducto = servicioProducto;
    }
    @RequestMapping("/productos")
    public ModelAndView irAPaginaProductos() {
        ModelMap modelo = new ModelMap();
        /*List<Producto> productos = this.servicioProducto.buscarPorCantidad(0); // Por defecto, mostrar todos los productos
        modelo.put("productos", productos); */
        return new ModelAndView("productos", modelo);
    }

    @RequestMapping("/productos/stock/{cantidad}")
    public ModelAndView buscarStockMayoresPorPathParam(@PathVariable int cantidad) {
        ModelMap modelo = new ModelMap();
        try {
            List<Producto> productos = this.servicioProducto.buscarPorCantidad(cantidad);
            modelo.put("productos", productos);
        }
        catch (RuntimeException e) {
            modelo.put("error", e.getMessage());
        }


        return new ModelAndView("productos", modelo);
    }

    @RequestMapping(value = "/stock", method = RequestMethod.POST)
    public ModelAndView buscarStockMayoresPorPost(@RequestParam int cantidad) {
        ModelMap modelo = new ModelMap();
        try {
            List<Producto> productos = this.servicioProducto.buscarPorCantidad(cantidad);
            modelo.put("productos", productos);
        }
        catch (RuntimeException e) {
            modelo.put("error", e.getMessage());
        }

        return new ModelAndView("productos", modelo);
    }
}
