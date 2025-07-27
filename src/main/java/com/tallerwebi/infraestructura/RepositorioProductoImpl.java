package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.RepositorioProducto;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class RepositorioProductoImpl implements RepositorioProducto {
    private SessionFactory sessionFactory;

    public RepositorioProductoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Producto> buscarPorCantidad(int cantidad) {
        String hql = "FROM Producto p WHERE p.stock > :stock";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("stock", cantidad);
        return query.getResultList();
    }
}
