package com.da.service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.da.model.Patient;
import com.da.repository.PatientRepository;
import com.google.api.core.ApiFuture;


@Service
public class PatientService {
	
	private static Logger logger = LoggerFactory.getLogger(PatientService.class);
	
	@Autowired
	private PatientRepository patientRepository;
		
	public String savePatient(Patient patient) throws InterruptedException, ExecutionException {
		logger.info("savePatient service called");
		if(patient!=null) {
			//patient validation
			return patientRepository.savePatient(patient);
		}
		throw new InterruptedException("Patient is not valid");
	}
	
	public Patient getPatient(String fName) throws InterruptedException, ExecutionException {
		logger.info("getPatient service called");
		return patientRepository.getPatient(fName);
	}
	
	public List<Patient> getPatientList(int offset, int limit) throws InterruptedException, ExecutionException, TimeoutException {
		logger.info("savePatient service called");
		return patientRepository.getPatientList(offset, limit);
	}
	
	public String deletePatient(String name) throws InterruptedException, ExecutionException {
		logger.info("deletePatient service called");
		ApiFuture<?> deletePatient = patientRepository.deletePatient(name);
		if(deletePatient!=null)
			return "Document with Patient ID "+name+" has been deleted";
		return "Patient not found with name "+name;
    }
	
}
