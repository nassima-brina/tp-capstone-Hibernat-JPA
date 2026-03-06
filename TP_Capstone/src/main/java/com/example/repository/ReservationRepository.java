package com.example.repository;

import com.example.model.Reservation;
import com.example.model.StatutReservation;
import java.util.List;

public interface ReservationRepository {
    void save(Reservation reservation);
    Reservation findById(Long id);
    List<Reservation> findAll();
    List<Reservation> findByStatut(StatutReservation statut);
    void update(Reservation reservation);
    void delete(Long id);
}