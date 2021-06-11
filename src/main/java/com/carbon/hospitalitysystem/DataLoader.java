package com.carbon.hospitalitysystem;

import com.carbon.hospitalitysystem.dao.CustomerDao;
import com.carbon.hospitalitysystem.dao.ReservationDao;
import com.carbon.hospitalitysystem.dao.RoomSettingsDao;
import com.carbon.hospitalitysystem.entities.CustomerEntity;
import com.carbon.hospitalitysystem.entities.ReservationEntity;
import com.carbon.hospitalitysystem.entities.RoomSettingsEntity;
import com.carbon.hospitalitysystem.enums.CutomerType;
import com.carbon.hospitalitysystem.enums.PaymentStatus;
import com.carbon.hospitalitysystem.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class DataLoader {
    private CustomerDao customerDao;
    private ReservationDao reservationDao;
    private RoomSettingsDao roomSettingsDao;

    @Bean
    CommandLineRunner myCommandLineRunner(){

        return (args) -> {
            SecureRandom secureRandom = new SecureRandom();
            roomSettingsDao.saveRecord(RoomSettingsEntity.builder().roomType(RoomType.REGULAR).weekDayRateIncrease(7).weekendRateIncrease(10).build());
            roomSettingsDao.saveRecord(RoomSettingsEntity.builder().roomType(RoomType.DELUXE).weekDayRateIncrease(8.5).weekendRateIncrease(12).build());
            roomSettingsDao.saveRecord(RoomSettingsEntity.builder().roomType(RoomType.PALATIAL).weekDayRateIncrease(11).weekendRateIncrease(16).build());


            CustomerEntity customer1 = CustomerEntity.builder().customerType(CutomerType.INDIVIDUAL).name("Jane Doe").build();
            CustomerEntity customer2 = CustomerEntity.builder().customerType(CutomerType.INDIVIDUAL).name("Joe Bloggs").build();
            customerDao.saveAll(List.of(customer1,customer2));
            LocalDateTime checkinTime = LocalDateTime.of(2021,06,11,10,00);

            ReservationEntity reservationEntity1 = ReservationEntity.builder().customer(customer1).expectedCheckInTime(checkinTime).expectedCheckOutTime(checkinTime.plusHours(8)).actualCheckoutTime(checkinTime.plusHours(secureRandom.nextInt(20)).plusMinutes(1)).roomType(RoomType.REGULAR).hourlyRate(BigDecimal.valueOf(150000)).paymentStatus(PaymentStatus.PAID).build();
            ReservationEntity reservationEntity2 = ReservationEntity.builder().customer(customer1).expectedCheckInTime(checkinTime).expectedCheckOutTime(checkinTime.plusHours(8)).actualCheckoutTime(checkinTime.plusHours(secureRandom.nextInt(40))).roomType(RoomType.REGULAR).hourlyRate(BigDecimal.valueOf(200000)).paymentStatus(PaymentStatus.PAID).build();
            ReservationEntity reservationEntity3 = ReservationEntity.builder().customer(customer2).expectedCheckInTime(checkinTime).expectedCheckOutTime(checkinTime.plusHours(8)).actualCheckoutTime(checkinTime.plusHours(secureRandom.nextInt(20))).roomType(RoomType.DELUXE).hourlyRate(BigDecimal.valueOf(230000)).paymentStatus(PaymentStatus.PAID).build();
            ReservationEntity reservationEntity4 = ReservationEntity.builder().customer(customer2).expectedCheckInTime(checkinTime).expectedCheckOutTime(checkinTime.plusHours(8)).actualCheckoutTime(checkinTime.plusHours(secureRandom.nextInt(20))).roomType(RoomType.PALATIAL).hourlyRate(BigDecimal.valueOf(560000)).paymentStatus(PaymentStatus.PAID).build();

            List<ReservationEntity> res = reservationDao.saveAll(List.of(reservationEntity1,reservationEntity2,reservationEntity3,reservationEntity4));
        };
    }

}
