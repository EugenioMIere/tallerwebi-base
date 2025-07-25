package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Cliente;
import com.tallerwebi.dominio.RepositorioCliente;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class RepositorioClienteImpl implements RepositorioCliente {
    private final SessionFactory sessionFactory;

    public RepositorioClienteImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Boolean crear(Cliente cliente) {

        this.sessionFactory.getCurrentSession().save(cliente);
        return true;
    }

    @Override
    public Cliente obtenerPorDni(int dni) {
        String hql = "FROM Cliente WHERE dni=:dni";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("dni", dni);
        try {
            return (Cliente) query.getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Cliente> obtenerPorTipoSuscripcion(String tipoSuscripcion) {
        String hql = "FROM Cliente WHERE tipoSuscripcion=:tipoSuscripcion";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("tipoSuscripcion", tipoSuscripcion);
        List<Cliente> clientes = query.getResultList();
        return clientes;
    }

    @Override
    public List<Cliente> obtenerTodos() {
        String hql = "FROM Cliente";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        List<Cliente> clientes = query.getResultList();
        return clientes;
    }

    @Override
    public void eliminar(int dni) {
        String hql = "DELETE FROM Cliente WHERE dni = :dni";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("dni", dni);
        int cantidadDeEliminaciones = query.executeUpdate();
        if (cantidadDeEliminaciones == 0) {
            throw new RuntimeException("No se eliminó ningún cliente");
        }
    }

    @Override
    public void suscribir(int dni, String tipoSuscripcion) {
        Cliente cliente = this.obtenerPorDni(dni);
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado con DNI: " + dni);
        }
        cliente.setTipoSuscripcion(tipoSuscripcion);
        this.sessionFactory.getCurrentSession().update(cliente);

    }


}
