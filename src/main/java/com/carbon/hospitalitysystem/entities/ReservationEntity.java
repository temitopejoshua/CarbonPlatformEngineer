package com.carbon.hospitalitysystem.entities;

import com.carbon.hospitalitysystem.enums.PaymentStatus;
import com.carbon.hospitalitysystem.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservation")
public class ReservationEntity extends AbstractBaseEntity<Long>{

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @ManyToOne
    private CustomerEntity customer;

    private LocalDateTime expectedCheckInTime;

    private LocalDateTime expectedCheckOutTime;

    private LocalDateTime actualCheckoutTime;

    private BigDecimal hourlyRate;

    private PaymentStatus paymentStatus;


}
