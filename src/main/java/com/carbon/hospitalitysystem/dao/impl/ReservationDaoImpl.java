package com.carbon.hospitalitysystem.dao.impl;

import com.carbon.hospitalitysystem.dao.ReservationDao;
import com.carbon.hospitalitysystem.entities.ReservationEntity;
import com.carbon.hospitalitysystem.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationDaoImpl extends CrudDaoImpl<ReservationEntity, Long> implements ReservationDao {
    private ReservationRepository repository;
    public ReservationDaoImpl(ReservationRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public List<ReservationEntity> saveAll(Iterable<ReservationEntity> reservations) {
        return repository.saveAll(reservations);
    }

    @Override
    public List<ReservationEntity> findAll() {
        return repository.findAll();
    }
}
