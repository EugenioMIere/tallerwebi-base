package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.RepositorioProducto;
import com.tallerwebi.infraestructura.config.HibernateInfrastructuraTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfrastructuraTestConfig.class})
@Transactional
public class RepositorioProductoTest {
    private RepositorioProducto repositorioProducto;
    @Autowired
    private SessionFactory sessionFactory;

    @BeforeEach
    public void init() {
        this.repositorioProducto = new RepositorioProductoImpl(sessionFactory);

    }
    @Test
    @Rollback
    public void queSePuedanBuscarProductosPorCantidad() {
        Producto producto = new Producto();
        producto.setNombre("Producto de prueba");
        producto.setStock(11);

        Producto producto1 = new Producto();
        producto1.setNombre("Producto de prueba1");
        producto1.setStock(5);

        Producto producto2 = new Producto();
        producto2.setNombre("Producto de prueba2");
        producto2.setStock(16);


        this.sessionFactory.getCurrentSession().save(producto);
        this.sessionFactory.getCurrentSession().save(producto1);
        this.sessionFactory.getCurrentSession().save(producto2);
        this.sessionFactory.getCurrentSession().flush();


        List<Producto> productosConStockMayorA10 = this.repositorioProducto.buscarPorCantidad(10);
        assertThat(productosConStockMayorA10.size(), is(2));
        assertThat(productosConStockMayorA10.get(0), is(producto));
        assertThat(productosConStockMayorA10.get(1), is(producto2));

    }
}
