package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Cliente;
import com.tallerwebi.dominio.RepositorioCliente;
import com.tallerwebi.infraestructura.config.HibernateInfrastructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfrastructuraTestConfig.class})
@Transactional
public class RepositorioClienteTest {
    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioCliente repositorioCliente;


    @BeforeEach
    public void init() {
        this.repositorioCliente = new RepositorioClienteImpl(this.sessionFactory);
    }

    @Test
    public void cuandoCreoUnClienteConLosDatosCorrectosSeGuardeEnBD(){
        Cliente cliente = new Cliente();
        cliente.setDni(12345678);

        Boolean guardado = this.repositorioCliente.crear(cliente);
        assertThat(guardado, is(true));

    }

    @Test
    public void dadoQueExistaUnDniAsociadoAUnClienteCuandoLoBusqueMeLoDevuelve() {
        Cliente cliente = new Cliente();
        cliente.setDni(12345678);
        this.sessionFactory.getCurrentSession().save(cliente);

        Cliente obtenido = this.repositorioCliente.obtenerPorDni(cliente.getDni());

        assertThat(obtenido, is(cliente));

    }

    @Test
    public void dadoQueNoExistaUnDniAsociadoAUnClienteCuandoLoBusqueMeDevuelveExcepcion() {
        int dniInexistente = 87654321;

        try {
            this.repositorioCliente.obtenerPorDni(dniInexistente);
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), is("No entity found for query"));
        }
    }

    @Test
    public void dadoQueExistaUnaListaDeClientesConTipoDeSuscripcionPremiumMeDevuelvaUnaListaDeTodosLosPremim() {

        Cliente cliente = new Cliente();
        cliente.setDni(12345678);
        cliente.setTipoSuscripcion("Premium");
        this.sessionFactory.getCurrentSession().save(cliente);

        Cliente cliente2 = new Cliente();
        cliente2.setDni(12345679);
        cliente2.setTipoSuscripcion("Premium");
        this.sessionFactory.getCurrentSession().save(cliente2);

        var clientes = this.repositorioCliente.obtenerPorTipoSuscripcion("Premium");
        assertThat(clientes.size(), is(2));
        assertThat(clientes.contains(cliente), is(true));
        assertThat(clientes.contains(cliente2), is(true));
    }
    @Test
    public void dadoQueExistaUnaListaDeClientesConTipoDeSuscripcionBasicoMeDevuelvaUnaListaDeTodosLosBasico() {

        Cliente cliente = new Cliente();
        cliente.setDni(12345678);
        cliente.setTipoSuscripcion("Basico");
        this.sessionFactory.getCurrentSession().save(cliente);

        Cliente cliente2 = new Cliente();
        cliente2.setDni(12345679);
        cliente2.setTipoSuscripcion("Basico");
        this.sessionFactory.getCurrentSession().save(cliente2);

        Cliente cliente3 = new Cliente();
        cliente3.setDni(12345689);
        cliente3.setTipoSuscripcion("Premium");
        this.sessionFactory.getCurrentSession().save(cliente3);

        var clientes = this.repositorioCliente.obtenerPorTipoSuscripcion("Basico");
        assertThat(clientes.size(), is(2));
        assertThat(clientes.contains(cliente), is(true));
        assertThat(clientes.contains(cliente2), is(true));
        assertThat(clientes.contains(cliente3), is(false));
    }

    @Test
    public void dadoQueExistaUnaListaDeClientesCuandoBusqueTodosMeTraigaTodos() {

        Cliente cliente = new Cliente();
        cliente.setDni(12345678);
        cliente.setTipoSuscripcion("Basico");
        this.sessionFactory.getCurrentSession().save(cliente);

        Cliente cliente2 = new Cliente();
        cliente2.setDni(12345679);
        cliente2.setTipoSuscripcion("Premium");
        this.sessionFactory.getCurrentSession().save(cliente2);

        var clientes = this.repositorioCliente.obtenerTodos();
        assertThat(clientes.size(), is(2));
        assertThat(clientes.contains(cliente), is(true));
        assertThat(clientes.contains(cliente2), is(true));
    }
    @Test
    public void dadoQueNoExistaUnaListaDeClientesCuandoBusqueTodosMeTraigaUnaListaVacia() {

        var clientes = this.repositorioCliente.obtenerTodos();
        assertThat(clientes.size(), is(0));
    }

    @Test
    public void dadoQueExistaUnClienteCuandoLoBusquePorDniMeDevuelveElClienteCorrecto() {
        Cliente cliente = new Cliente();
        cliente.setDni(12345678);
        this.sessionFactory.getCurrentSession().save(cliente);

        Cliente obtenido = this.repositorioCliente.obtenerPorDni(cliente.getDni());

        assertThat(obtenido, is(cliente));
    }
    @Test
    public void cuandoEliminoUnClienteEntoncesSeEliminaCorrectamente() {
        Cliente cliente = new Cliente();
        cliente.setDni(12345678);
        this.sessionFactory.getCurrentSession().save(cliente);
        assertThat(this.repositorioCliente.obtenerTodos().size(), is(1));

        this.repositorioCliente.eliminar(cliente.getDni());

        var clientes = this.repositorioCliente.obtenerTodos();
        assertThat(clientes.size(), is(0));
    }

    @Test
    public void cuandoEliminoUnClienteQueNoExisteEntoncesLanzaExcepcion() {


        try {
            this.repositorioCliente.eliminar(11223344); // Intento eliminar un ID que no existe
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), is("No se eliminó ningún cliente"));
        }
    }
    @Test
    public void cuandoEliminoUnClienteQueExistePeroNoSeEliminaEntoncesLanzaExcepcion() {
        Cliente cliente = new Cliente();
        cliente.setDni(12345678);
        this.sessionFactory.getCurrentSession().save(cliente);

        try {
            this.repositorioCliente.eliminar(cliente.getDni() + 1); // Intento eliminar un ID que no existe
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), is("No se eliminó ningún cliente"));
        }
    }
    @Test
    public void dadoQueExistaUnClientePuedaSuscribirse() {
        Cliente cliente = new Cliente();
        cliente.setDni(12345678);
        cliente.setTipoSuscripcion("Básica");
        this.sessionFactory.getCurrentSession().save(cliente);

        this.repositorioCliente.suscribir(cliente.getDni(), "Premium");

        Cliente clienteActualizado = this.repositorioCliente.obtenerPorDni(cliente.getDni());
        assertThat(clienteActualizado.getTipoSuscripcion(), is("Premium"));
    }
}
