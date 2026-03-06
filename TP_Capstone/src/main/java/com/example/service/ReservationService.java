package com.example.service;

import com.example.model.Reservation;
import com.example.model.StatutReservation;
import java.util.List;

public interface ReservationService {
    void save(Reservation reservation);
    Reservation findById(Long id);
    List<Reservation> findAll();
    List<Reservation> findByStatut(StatutReservation statut);
    void update(Reservation reservation);
    void delete(Long id);
}