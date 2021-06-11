package com.carbon.hospitalitysystem.entities;

import com.carbon.hospitalitysystem.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "room_settings")
public class RoomSettingsEntity extends AbstractBaseEntity<Long>{

    @Column(unique = true)
    private RoomType roomType;

    private double weekDayRateIncrease;

    private double weekendRateIncrease;
}
