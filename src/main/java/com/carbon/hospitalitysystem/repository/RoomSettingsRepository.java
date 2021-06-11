package com.carbon.hospitalitysystem.repository;

import com.carbon.hospitalitysystem.entities.RoomSettingsEntity;
import com.carbon.hospitalitysystem.enums.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoomSettingsRepository extends JpaRepository<RoomSettingsEntity,Long> {
    Optional<RoomSettingsEntity> findByRoomType(RoomType roomType);
}
