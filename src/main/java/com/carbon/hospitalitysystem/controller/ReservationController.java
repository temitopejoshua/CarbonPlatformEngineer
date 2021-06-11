package com.carbon.hospitalitysystem.controller;

import com.carbon.hospitalitysystem.dto.ReservationDetailsDTO;
import com.carbon.hospitalitysystem.response.APIResponse;
import com.carbon.hospitalitysystem.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@AllArgsConstructor
public class ReservationController {
    private ReservationService reservationService;

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<ReservationDetailsDTO>> getReservationDetails(@PathVariable("id") Long reservationId){
        APIResponse<ReservationDetailsDTO> response = new APIResponse<>("Reservation Details Fetched Successfully", reservationService.calculateOverStay(reservationId));
        return ResponseEntity.ok(response);
    }
    @GetMapping("/")
    public ResponseEntity<APIResponse<List<ReservationDetailsDTO>>> getAllReservations(){
        APIResponse<List<ReservationDetailsDTO>> response = new APIResponse<>("All Reservation Details Fetched Successfully", reservationService.findAll());
        return ResponseEntity.ok(response);
    }
}
