package com.carbon.hospitalitysystem.dao;

import com.carbon.hospitalitysystem.entities.ReservationEntity;

import java.util.List;


public interface ReservationDao extends CrudDao<ReservationEntity, Long> {
    List<ReservationEntity> saveAll(Iterable<ReservationEntity> reservations);
    List<ReservationEntity> findAll();

}
