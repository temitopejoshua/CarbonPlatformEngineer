package com.carbon.hospitalitysystem.dao.impl;

import com.carbon.hospitalitysystem.dao.RoomSettingsDao;
import com.carbon.hospitalitysystem.entities.RoomSettingsEntity;
import com.carbon.hospitalitysystem.enums.RoomType;
import com.carbon.hospitalitysystem.repository.RoomSettingsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomSettingsDaoImpl extends CrudDaoImpl<RoomSettingsEntity,Long> implements RoomSettingsDao {
    private RoomSettingsRepository repository;

    public RoomSettingsDaoImpl(RoomSettingsRepository repository) {
        super(repository);
        this.repository=repository;

    }

    @Override
    public Optional<RoomSettingsEntity> findByRoomType(RoomType roomType) {
        return repository.findByRoomType(roomType);
    }
}
