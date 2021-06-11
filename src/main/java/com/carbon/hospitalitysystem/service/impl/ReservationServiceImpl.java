package com.carbon.hospitalitysystem.service.impl;

import com.carbon.hospitalitysystem.dao.ReservationDao;
import com.carbon.hospitalitysystem.dao.RoomSettingsDao;
import com.carbon.hospitalitysystem.dto.ReservationDetailsDTO;
import com.carbon.hospitalitysystem.entities.ReservationEntity;
import com.carbon.hospitalitysystem.entities.RoomSettingsEntity;
import com.carbon.hospitalitysystem.enums.RoomType;
import com.carbon.hospitalitysystem.exception.BadRequestException;
import com.carbon.hospitalitysystem.exception.NotFoundException;
import com.carbon.hospitalitysystem.service.ReservationService;
import com.carbon.hospitalitysystem.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private ReservationDao reservationDao;
    private RoomSettingsDao roomSettingsDao;
    @Autowired
    public ReservationServiceImpl(ReservationDao reservationDao, RoomSettingsDao roomSettingsDao) {
        this.reservationDao = reservationDao;
        this.roomSettingsDao = roomSettingsDao;
    }

    @Override
    public ReservationDetailsDTO calculateOverStay(long reservationId) {
        ReservationEntity reservation = reservationDao.findById(reservationId).orElseThrow(() -> new NotFoundException("Reservation not found " + reservationId));
       BigDecimal overStayCharges = computeTotalCost(reservation.getExpectedCheckOutTime(), reservation.getActualCheckoutTime(), reservation.getRoomType(), reservation.getHourlyRate());
        BigDecimal actualCost = computeActualCost(reservation.getExpectedCheckInTime(),reservation.getExpectedCheckOutTime(),reservation.getHourlyRate());
        return ReservationDetailsDTO.builder().actualCost(actualCost)
                .totalCost(actualCost.add(overStayCharges))
                .overStayCost(overStayCharges)
                .checkinTime(reservation.getExpectedCheckInTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .expectedCheckoutTime(reservation.getExpectedCheckOutTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .chekoutTime(reservation.getActualCheckoutTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .roomType(reservation.getRoomType())
                .overStayDuration(String.format("%shrs", DateUtil.getOverstayTimeDifference(reservation.getExpectedCheckOutTime(), reservation.getActualCheckoutTime())))
                .actualStay(String.format("%shrs", DateUtil.getTimeDifferenceInHours(reservation.getExpectedCheckInTime(), reservation.getExpectedCheckOutTime())))
                .id(reservation.getId())
                .build();
    }
    public ReservationDetailsDTO calculateOverStay(ReservationEntity reservation) {
        BigDecimal overStayCharges = computeTotalCost(reservation.getExpectedCheckOutTime(), reservation.getActualCheckoutTime(), reservation.getRoomType(), reservation.getHourlyRate());
        BigDecimal actualCost = computeActualCost(reservation.getExpectedCheckInTime(),reservation.getExpectedCheckOutTime(),reservation.getHourlyRate());
        return ReservationDetailsDTO.builder().actualCost(actualCost)
                .totalCost(actualCost.add(overStayCharges))
                .overStayCost(overStayCharges)
                .checkinTime(reservation.getExpectedCheckInTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .expectedCheckoutTime(reservation.getExpectedCheckOutTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .chekoutTime(reservation.getActualCheckoutTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .roomType(reservation.getRoomType())
                .overStayDuration(String.format("%shrs", DateUtil.getOverstayTimeDifference(reservation.getExpectedCheckOutTime(), reservation.getActualCheckoutTime())))
                .actualStay(String.format("%shrs", DateUtil.getTimeDifferenceInHours(reservation.getExpectedCheckInTime(), reservation.getExpectedCheckOutTime())))
                .id(reservation.getId())
                .build();
    }

    @Override
    public List<ReservationDetailsDTO> findAll() {

        return reservationDao.findAll().stream().map((r) -> this.calculateOverStay(r)).collect(Collectors.toList());
    }

    public BigDecimal computeTotalCost(LocalDateTime expectedCheckoutTime, LocalDateTime actualCheckoutTime, RoomType roomType, BigDecimal hourlyRate){
        BigDecimal overStayAmount= BigDecimal.ZERO;
        RoomSettingsEntity roomSettingsEntity = roomSettingsDao.findByRoomType(roomType).orElseThrow( ()-> new BadRequestException(String.format("Invalid Room Type %s",roomType )));

        long overStayHours =  DateUtil.getOverstayTimeDifference(expectedCheckoutTime,actualCheckoutTime);
    log.info("---> Overstay hours " + overStayHours);
        if(actualCheckoutTime.isAfter(expectedCheckoutTime)){
            int counter = 1;
            while (counter <= overStayHours){
                expectedCheckoutTime = expectedCheckoutTime.plusHours(1);
                if(DateUtil.isDayOfTheWeek(expectedCheckoutTime.getDayOfWeek().name())){
                    overStayAmount= overStayAmount.add(calculateOverStay(roomSettingsEntity.getWeekDayRateIncrease(),hourlyRate,1));
                }else {
                    overStayAmount = overStayAmount.add(calculateOverStay(roomSettingsEntity.getWeekendRateIncrease(),hourlyRate,1));
                }
                counter++;
            }
        }

        return overStayAmount;
    }
    private BigDecimal computeActualCost(LocalDateTime checkInTime, LocalDateTime expectedCheckoutTime, BigDecimal hourlyRate){
        long noOfHours =  DateUtil.getTimeDifferenceInHours(checkInTime,expectedCheckoutTime);
        return BigDecimal.valueOf(hourlyRate.doubleValue() * noOfHours);
    }

    private BigDecimal calculateOverStay(double percentageIncrease, BigDecimal roomAmount, long numberOfHours){
        percentageIncrease = percentageIncrease/100;
        BigDecimal calcAmount = roomAmount.multiply(BigDecimal.valueOf(percentageIncrease));
        roomAmount = roomAmount.add(calcAmount);
        return roomAmount.multiply(BigDecimal.valueOf(numberOfHours));
    }

}
