package com.carbon.hospitalitysystem.dao;

import com.carbon.hospitalitysystem.entities.RoomSettingsEntity;
import com.carbon.hospitalitysystem.enums.RoomType;

import java.util.Optional;

public interface RoomSettingsDao extends CrudDao<RoomSettingsEntity,Long> {

    Optional<RoomSettingsEntity> findByRoomType(RoomType roomType);
}
