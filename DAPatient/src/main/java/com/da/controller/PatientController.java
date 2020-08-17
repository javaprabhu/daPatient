package com.da.controller;

import java.util.Collections;
import java.util.List;

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
	public String savePatient(@RequestBody Patient patient) {
		logger.info("savePatient invoked with param "+patient);
		try {
			return patientService.savePatient(patient);
		}catch(Exception e) {
			logger.error(e.getMessage());
			return "patient did not saved";
		}
	}
	
	@GetMapping("/{fName}")
	public Patient getPatient(@PathVariable("fName")String fName) {
		logger.info("getPatient invoked with fName "+fName);
		try {
			return patientService.getPatient(fName);
		}catch(Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@GetMapping("/list")
	public List<Patient> patientList(@RequestParam("offset")Integer offset, @RequestParam("limit")Integer limit) {
		logger.info("patientList invoked with param "+offset+"and"+limit);
		try {
			return patientService.getPatientList(offset, limit);
		}catch(Exception e) {
			logger.error(e.getMessage());
			return Collections.emptyList();
		}
	}
	
	@DeleteMapping("/{fName}")
	public String deletePatientWithName(@PathVariable("fName")String fName) {
		logger.info("deletePatientWithName invoked with param "+fName);
		try {
			return patientService.deletePatient(fName);
		}catch(Exception e) {
			logger.error(e.getMessage());
			return "Patient not found with name "+fName;
		}
	}
	
}
