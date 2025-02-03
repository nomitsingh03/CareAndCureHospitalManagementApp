package com.cac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cac.exception.UserNotFoundException;
import com.cac.model.Patient;
import com.cac.service.PatientService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

	@Autowired
    private PatientService patientService;

    @PostMapping("/registerPatient") 
	public ResponseEntity<Patient> registerPatient(@Valid @RequestBody Patient patient) throws UserNotFoundException{
		return new ResponseEntity<Patient>(patientService.createPatient(patient), HttpStatus.CREATED);
	}
	
	@PutMapping("/updatePatient/{id}")
	public ResponseEntity<Patient> updatePatient(@PathVariable int id,@Valid @RequestBody Patient patient) throws Exception{
		return new ResponseEntity<Patient>(patientService.updatePatient(id, patient), HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/deactivatePatient/{id}")
	public ResponseEntity<Patient> changePatientActive(@PathVariable int id) throws Exception{
		return new ResponseEntity<Patient>(patientService.changeActive(id), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/viewPatient/{id}")
	public ResponseEntity<Patient> getPatientById(@PathVariable int id) throws UserNotFoundException{
		Patient patient = patientService.getPatientById(id);
		return ResponseEntity.ok(patient);
	}
	
	@GetMapping("/viewPatientByName/{name}")
	public ResponseEntity<List<Patient>> getPatientByName(@PathVariable String name) throws Exception{
		List<Patient> patientList = patientService.getPatientsByName(name);
		return ResponseEntity.ok(patientList);
	}
	
	@GetMapping("/viewAllPatient")
	public ResponseEntity<List<Patient>> getAllPatient(){
		List<Patient> patientList = patientService.getAllPatients();
		return new ResponseEntity<List<Patient>>(patientList, HttpStatus.OK);
	}
	

	@GetMapping("/viewAllPatientByStatus")
	public ResponseEntity<List<Patient>> getAllPatientByStatus(@RequestParam boolean active){
		List<Patient> patientList = patientService.getAllPatientByStatus(active);
		return new ResponseEntity<List<Patient>>(patientList, HttpStatus.OK);
	}

    // Get patient details for display
    @GetMapping("/{patientId}/details")
    public ResponseEntity<Patient> getPatientDetailsForDisplay(@PathVariable int patientId) {
        Optional<Patient> patient = patientService.getPatientDetailsForDisplay(patientId);
        if (patient.isPresent()) {
            return ResponseEntity.ok(patient.get());
        }
        return ResponseEntity.notFound().build();
    }

	//get patient lisit by insuranceprovider
	@GetMapping("/viewAllByInsuranceProvider")
	public ResponseEntity<List<Patient>> getPatientsByInsuranceProvider(@RequestParam String insuranceProvider) {
		return new ResponseEntity<>(patientService.getPatientsByInsuranceProvider(insuranceProvider), HttpStatus.OK);
	}
}
