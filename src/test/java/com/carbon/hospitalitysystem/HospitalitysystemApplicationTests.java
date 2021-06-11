package com.carbon.hospitalitysystem;

import com.carbon.hospitalitysystem.dao.CustomerDao;
import com.carbon.hospitalitysystem.dao.ReservationDao;
import com.carbon.hospitalitysystem.dao.RoomSettingsDao;
import com.carbon.hospitalitysystem.dto.ReservationDetailsDTO;
import com.carbon.hospitalitysystem.entities.CustomerEntity;
import com.carbon.hospitalitysystem.entities.ReservationEntity;
import com.carbon.hospitalitysystem.enums.CutomerType;
import com.carbon.hospitalitysystem.enums.PaymentStatus;
import com.carbon.hospitalitysystem.enums.RoomType;
import com.carbon.hospitalitysystem.service.ReservationService;
import com.carbon.hospitalitysystem.utils.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class HospitalitysystemApplicationTests {

	private CustomerDao customerDao;
	private ReservationDao reservationDao;
	private RoomSettingsDao roomSettingsDao;
	private ReservationService reservationService;
@Autowired
	HospitalitysystemApplicationTests(CustomerDao customerDao, ReservationDao reservationDao, RoomSettingsDao roomSettingsDao, ReservationService reservationService) {
		this.customerDao = customerDao;
		this.reservationDao = reservationDao;
		this.roomSettingsDao = roomSettingsDao;
	this.reservationService = reservationService;
}
	@BeforeEach	void setup(){


		CustomerEntity customer1 = CustomerEntity.builder().customerType(CutomerType.INDIVIDUAL).name("Jane Doe").build();
		CustomerEntity customer2 = CustomerEntity.builder().customerType(CutomerType.INDIVIDUAL).name("Joe Bloggs").build();
		customerDao.saveAll(List.of(customer1,customer2));
		LocalDateTime checkinTime = LocalDateTime.of(2021,06,11,10,00);

		ReservationEntity reservationEntity1 = ReservationEntity.builder().customer(customer1).expectedCheckInTime(checkinTime).expectedCheckOutTime(checkinTime.plusHours(8)).actualCheckoutTime(checkinTime.plusHours(8).plusMinutes(1)).roomType(RoomType.REGULAR).hourlyRate(BigDecimal.valueOf(180)).paymentStatus(PaymentStatus.PAID).build();
		ReservationEntity reservationEntity2 = ReservationEntity.builder().customer(customer1).expectedCheckInTime(checkinTime).expectedCheckOutTime(checkinTime.plusHours(8)).actualCheckoutTime(checkinTime.plusHours(20)).roomType(RoomType.REGULAR).hourlyRate(BigDecimal.valueOf(200000)).paymentStatus(PaymentStatus.PAID).build();
		ReservationEntity reservationEntity3 = ReservationEntity.builder().customer(customer2).expectedCheckInTime(checkinTime).expectedCheckOutTime(checkinTime.plusHours(8)).actualCheckoutTime(checkinTime.plusHours(20)).roomType(RoomType.DELUXE).hourlyRate(BigDecimal.valueOf(230000)).paymentStatus(PaymentStatus.PAID).build();
		ReservationEntity reservationEntity4 = ReservationEntity.builder().customer(customer2).expectedCheckInTime(checkinTime).expectedCheckOutTime(checkinTime.plusHours(8)).actualCheckoutTime(checkinTime.plusHours(20)).roomType(RoomType.PALATIAL).hourlyRate(BigDecimal.valueOf(560000)).paymentStatus(PaymentStatus.PAID).build();

		List<ReservationEntity> res = reservationDao.saveAll(List.of(reservationEntity1,reservationEntity2,reservationEntity3,reservationEntity4));

	}

	@Test
	void overStayIsCalculatedPerHourTest(){
		CustomerEntity customer1 = CustomerEntity.builder().customerType(CutomerType.INDIVIDUAL).name("Jane Doe").build();
		customerDao.saveRecord(customer1);
		LocalDateTime checkinTime = LocalDateTime.of(2021,06,11,10,00);
		LocalDateTime expectedCheckoutTime = checkinTime.plusHours(6);
		LocalDateTime actualCheckOutTime = checkinTime.plusHours(9);

		BigDecimal hourlyRate = BigDecimal.valueOf(230000);

		ReservationEntity reservation = ReservationEntity.builder().customer(customer1).expectedCheckInTime(checkinTime).expectedCheckOutTime(expectedCheckoutTime).actualCheckoutTime(actualCheckOutTime).roomType(RoomType.REGULAR).hourlyRate(hourlyRate).paymentStatus(PaymentStatus.PAID).build();
		reservationDao.saveRecord(reservation);
		long hourDifference = DateUtil.getOverstayTimeDifference(expectedCheckoutTime,actualCheckOutTime);
		BigDecimal overStayIncrement = hourlyRate.multiply(BigDecimal.valueOf(0.07));

		BigDecimal overStayHourlyRate = hourlyRate.add(overStayIncrement);
		ReservationDetailsDTO response = reservationService.calculateOverStay(reservation.getId());

		assertEquals(0, response.getOverStayCost().compareTo(overStayHourlyRate.multiply(BigDecimal.valueOf(hourDifference))));

	}


	@Test
	void contextLoads() {
	}

}
