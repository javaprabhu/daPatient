package com.da.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.da.model.Patient;
import com.da.service.PatientService;

@RestController
@RequestMapping("/patient")
public class PatientController {

	private static final Logger logger = LoggerFactory.getLogger(PatientController.class);
	
	@Autowired
	private PatientService patientService;
	
	@PostMapping("/savePatient")
	public String savePatient(@RequestBody Patient patient) throws InterruptedException, ExecutionException {
		logger.info("savePatient invoked with param "+patient);
		return patientService.savePatient(patient);
	}
	
	@GetMapping("/{fName}")
	public Patient getPatient(@PathVariable("fName")String fName) throws InterruptedException, ExecutionException {
		logger.info("getPatient invoked with fName "+fName);
		return patientService.getPatient(fName);
	}
	
	@GetMapping("/list")
	public List<Patient> patientList(@RequestParam("offset")Integer offset, @RequestParam("limit")Integer limit) throws InterruptedException, ExecutionException, TimeoutException {
		logger.info("patientList invoked with param "+offset+"and"+limit);
		return patientService.getPatientList(offset, limit);
	}
	
	@DeleteMapping("/{fName}")
	public String deletePatientWithName(@PathVariable("fName")String fName) throws InterruptedException, ExecutionException {
		logger.info("deletePatientWithName invoked with param "+fName);
		return patientService.deletePatient(fName);
	}
	
}
