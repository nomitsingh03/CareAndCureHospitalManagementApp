package com.cac.controller;

import com.cac.model.Bill;

import com.cac.service.BillService;
import com.cac.service.EmailService;
import com.cac.exception.BillNotFoundException;
import com.cac.service.BillService;
import com.cac.service.EmailService;

import jakarta.mail.MessagingException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BillController {

	@Autowired
	private BillService billService;

	@CrossOrigin(origins = "http://localhost:8082")
	@GetMapping("/api/bills/{billId}")
	public Bill getBillByBillId(@PathVariable int billId) {
		return billService.findByBillId(billId);
	}

	@Autowired
	private EmailService emailNotificationService;

	@GetMapping("/welcome")
	public String displayWelcomeMessage() {

		String msg = billService.displayWelcomeMessage();
		return msg;
	}

	@PostMapping("/bills/{appointmentId}")
	public ResponseEntity<?> createBill(@RequestBody Bill bill, @PathVariable int appointmentId)
			throws BillNotFoundException, NullPointerException, IllegalArgumentException, MessagingException,
			Exception {

		System.out.println("Bill: " + bill);
		System.out.println("Appointment ID: " + appointmentId);
		Bill savedBill = billService.createBill(bill, appointmentId);

		// Send the email
		emailNotificationService.sendBillEmail(savedBill);

		return ResponseEntity.status(HttpStatus.CREATED).body(savedBill); // Return 201

	}
	// http://localhost:8082/bills/5

	// updatebillById
	@PutMapping("/bills/{billId}")
	public ResponseEntity<?> updateBillById(@RequestBody Bill bill, @PathVariable int billId)
			throws BillNotFoundException, NullPointerException, IllegalArgumentException, Exception {

		Bill b = billService.updatePaymentStatus(billId, bill.getPaymentstatus());
		return ResponseEntity.ok(b); // Return 200 Ok
	}

	// searchBillbyBillId
	@GetMapping("/bills/{billId}")
	public ResponseEntity<?> getBillById(@PathVariable int billId)  {
        try {
		Bill bill = billService.getBillById(billId);
		return ResponseEntity.ok(bill); // Return 200 OK
        }
        catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("An error occurred: " + ex.getMessage());
		}

	}
	// http://localhost:8082/bills/2

	// searchBillByPatientID
	@GetMapping("/bills/patient/{patientId}")
	public ResponseEntity<?> getBillsByPatientId(@PathVariable int patientId) {
		try {
			List<Bill> bills = billService.getBillsByPatientId(patientId);
			if (bills.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("No bills found for patient with ID: " + patientId);
			}
			return ResponseEntity.ok(bills); // Return 200 OK
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred: " + ex.getMessage());
		}
	}
	// http://localhost:8082/bills/patient/103

	// searchBillByDate
	@GetMapping("/bills/date/{billDate}")
	public ResponseEntity<?> getBillsByDate(@PathVariable String billDate) {
		try {
			// Convert the billDate string to LocalDate
			LocalDate parsedDate = LocalDate.parse(billDate);

			List<Bill> bills = billService.getBillsByDate(parsedDate);

			if (bills.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No bills found for date: " + billDate);
			}

			return ResponseEntity.ok(bills); // Return 200 OK
		} catch (DateTimeParseException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date format. Please use 'YYYY-MM-DD'.");
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred: " + ex.getMessage());
		}
	}
	// http://localhost:8082/bills/date/2024-12-20

}
