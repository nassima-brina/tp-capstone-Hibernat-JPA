package com.example.service;

import com.example.model.Reservation;
import com.example.model.StatutReservation;
import com.example.repository.ReservationRepository;
import javax.persistence.EntityManager;
import java.util.List;

public class ReservationServiceImpl implements ReservationService {

    private final EntityManager em;
    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(EntityManager em, ReservationRepository reservationRepository) {
        this.em = em;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void save(Reservation reservation) {
        em.getTransaction().begin();
        reservationRepository.save(reservation);
        em.getTransaction().commit();
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id);
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public List<Reservation> findByStatut(StatutReservation statut) {
        return reservationRepository.findByStatut(statut);
    }

    @Override
    public void update(Reservation reservation) {
        em.getTransaction().begin();
        reservationRepository.update(reservation);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Long id) {
        em.getTransaction().begin();
        reservationRepository.delete(id);
        em.getTransaction().commit();
    }
}
