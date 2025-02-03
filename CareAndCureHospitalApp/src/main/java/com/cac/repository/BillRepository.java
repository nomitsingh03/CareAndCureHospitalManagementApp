package com.cac.repository;

import com.cac.model.Bill;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {	

	 Bill findByBillId(int billId);
	
	 Bill findByAppointment_AppointmentId(int appointmentId);

	
	// List<Bill> findByAppointment_PatientId(int patientId);
	 List<Bill> findByAppointment_Patient_PatientId(int patientId);


	 List<Bill> findByBillDate(LocalDate billDate);
	
}