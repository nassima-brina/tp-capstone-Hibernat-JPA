package com.example.repository;

import com.example.model.Salle;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class SalleRepositoryImpl implements SalleRepository {

    private final EntityManager em;

    public SalleRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(Salle salle) {
        em.persist(salle);
    }

    @Override
    public Salle findById(Long id) {
        return em.find(Salle.class, id);
    }

    @Override
    public List<Salle> findAll() {
        return em.createQuery("SELECT s FROM Salle s", Salle.class).getResultList();
    }

    @Override
    public List<Salle> findAvailableRooms(LocalDateTime debut, LocalDateTime fin) {
        return em.createQuery(
                        "SELECT DISTINCT s FROM Salle s WHERE s.id NOT IN (" +
                                "SELECT r.salle.id FROM Reservation r " +
                                "WHERE r.statut = com.example.model.StatutReservation.CONFIRMEE " +
                                "AND r.dateDebut < :fin AND r.dateFin > :debut)", Salle.class)
                .setParameter("debut", debut)
                .setParameter("fin", fin)
                .getResultList();
    }

    @Override
    public List<Salle> searchRooms(Map<String, Object> criteres) {
        StringBuilder jpql = new StringBuilder("SELECT DISTINCT s FROM Salle s");

        if (criteres.containsKey("equipement")) {
            jpql.append(" JOIN s.equipements e");
        }

        jpql.append(" WHERE 1=1");

        if (criteres.containsKey("capaciteMin")) {
            jpql.append(" AND s.capacite >= :capaciteMin");
        }
        if (criteres.containsKey("capaciteMax")) {
            jpql.append(" AND s.capacite <= :capaciteMax");
        }
        if (criteres.containsKey("batiment")) {
            jpql.append(" AND s.batiment = :batiment");
        }
        if (criteres.containsKey("etage")) {
            jpql.append(" AND s.etage = :etage");
        }
        if (criteres.containsKey("equipement")) {
            jpql.append(" AND e.id = :equipement");
        }

        TypedQuery<Salle> query = em.createQuery(jpql.toString(), Salle.class);

        if (criteres.containsKey("capaciteMin"))
            query.setParameter("capaciteMin", criteres.get("capaciteMin"));
        if (criteres.containsKey("capaciteMax"))
            query.setParameter("capaciteMax", criteres.get("capaciteMax"));
        if (criteres.containsKey("batiment"))
            query.setParameter("batiment", criteres.get("batiment"));
        if (criteres.containsKey("etage"))
            query.setParameter("etage", criteres.get("etage"));
        if (criteres.containsKey("equipement"))
            query.setParameter("equipement", criteres.get("equipement"));

        return query.getResultList();
    }

    @Override
    public List<Salle> getPaginatedRooms(int page, int pageSize) {
        return em.createQuery("SELECT s FROM Salle s ORDER BY s.id", Salle.class)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    @Override
    public long countRooms() {
        return em.createQuery("SELECT COUNT(s) FROM Salle s", Long.class)
                .getSingleResult();
    }

    @Override
    public void update(Salle salle) {
        em.merge(salle);
    }

    @Override
    public void delete(Long id) {
        Salle salle = findById(id);
        if (salle != null) em.remove(salle);
    }
}