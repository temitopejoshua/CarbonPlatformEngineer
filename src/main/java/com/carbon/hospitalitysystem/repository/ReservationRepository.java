package com.carbon.hospitalitysystem.repository;

import com.carbon.hospitalitysystem.entities.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity,Long> {
}
