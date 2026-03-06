package com.example.repository;

import com.example.model.Reservation;
import com.example.model.StatutReservation;
import javax.persistence.EntityManager;
import java.util.List;

public class ReservationRepositoryImpl implements ReservationRepository {

    private final EntityManager em;

    public ReservationRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(Reservation reservation) {
        em.persist(reservation);
    }

    @Override
    public Reservation findById(Long id) {
        return em.find(Reservation.class, id);
    }

    @Override
    public List<Reservation> findAll() {
        return em.createQuery("SELECT r FROM Reservation r", Reservation.class)
                .getResultList();
    }

    @Override
    public List<Reservation> findByStatut(StatutReservation statut) {
        return em.createQuery(
                        "SELECT r FROM Reservation r WHERE r.statut = :statut", Reservation.class)
                .setParameter("statut", statut)
                .getResultList();
    }

    @Override
    public void update(Reservation reservation) {
        em.merge(reservation);
    }

    @Override
    public void delete(Long id) {
        Reservation r = findById(id);
        if (r != null) em.remove(r);
    }
}