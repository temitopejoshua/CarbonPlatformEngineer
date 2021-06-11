package com.carbon.hospitalitysystem.dto;

import com.carbon.hospitalitysystem.enums.RoomType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ReservationDetailsDTO {
    private String chekoutTime;
    private String checkinTime;
    private String expectedCheckoutTime;
    private BigDecimal actualCost;
    private BigDecimal overStayCost;
    private BigDecimal totalCost;
    private RoomType roomType;
    private String  overStayDuration;
    private String actualStay;
    private Long id;

}
