package com.carbon.hospitalitysystem.service;

import com.carbon.hospitalitysystem.dto.ReservationDetailsDTO;

import java.util.List;

public interface ReservationService {
    ReservationDetailsDTO calculateOverStay(long reservationId);

    List<ReservationDetailsDTO> findAll();

}
