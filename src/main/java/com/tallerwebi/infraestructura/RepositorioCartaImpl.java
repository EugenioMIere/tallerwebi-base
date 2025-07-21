package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Carta;
import com.tallerwebi.dominio.RepositorioCarta;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class RepositorioCartaImpl implements RepositorioCarta {

    private final SessionFactory sessionFactory;
    public RepositorioCartaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Boolean crear(Carta carta) {
        this.sessionFactory.getCurrentSession().save(carta);
        return true;
    }

    @Override
    public Carta obtenerPorId(Long id) {
        String hql = "FROM Carta WHERE id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", id);
        return (Carta)query.getSingleResult();
    }

    @Override
    public List<Carta> obtenerTodasLasCartas() {
        String hql = "FROM Carta";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        return query.getResultList();
    }

    @Override
    public void actualizar(Carta carta) {
        String hql = "UPDATE Carta SET nombre = :nombre WHERE id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", carta.getId());
        query.setParameter("nombre", carta.getNombre());
        int cantidadDeActualizaciones = query.executeUpdate();
        if (cantidadDeActualizaciones > 1) {
            throw new RuntimeException("Se actualizaron más de una carta");
        }
    }
    @Override
    public void eliminar(Carta carta) {
        String hql = "DELETE FROM Carta WHERE id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", carta.getId());
        int cantidadDeEliminaciones = query.executeUpdate();
        if (cantidadDeEliminaciones == 0) {
            throw new RuntimeException("No se eliminó ninguna carta");
        }
        if (cantidadDeEliminaciones > 1) {
            throw new RuntimeException("Se eliminaron más de una carta");
        }
    }
}
