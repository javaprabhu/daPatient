package com.da.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Repository;

import com.da.model.Patient;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

@Repository
public class PatientRepository {

	private static final String COL_NAME = "Patient";
	
	public String savePatient(Patient patient) throws InterruptedException, ExecutionException {
		Firestore fireStore = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> apiFuture = fireStore.collection(COL_NAME).document(patient.getfName()).set(patient);
		return apiFuture.get().getUpdateTime().toString();
	}
	
	public Patient getPatient(String fName) throws InterruptedException, ExecutionException {
		Firestore fireStore = FirestoreClient.getFirestore();
		DocumentReference document = fireStore.collection(COL_NAME).document(fName);
		ApiFuture<DocumentSnapshot> apiFuture = document.get();
		DocumentSnapshot snapshot = apiFuture.get();
		Patient patient = null;
		if(snapshot.exists()) {
			patient = snapshot.toObject(Patient.class);
			if(patient.isDeleted()) {
				return null;
			}
		}
		return patient;
	}
	
	public List<Patient> getPatientList(int offset, int limit) throws InterruptedException, ExecutionException, TimeoutException {
		List<Patient> patients = new ArrayList<Patient>();
		Firestore fireStore = FirestoreClient.getFirestore();
		CollectionReference patCol = fireStore.collection(COL_NAME);
		Query firstPage = patCol.whereEqualTo("deleted",false).offset(offset).limit(limit);

		ApiFuture<QuerySnapshot> future = firstPage.get();
		List<QueryDocumentSnapshot> documents = future.get().getDocuments();
		Iterator<QueryDocumentSnapshot> iterator = documents.iterator();
		while(iterator.hasNext()) {
			QueryDocumentSnapshot next = iterator.next();
			if(next.exists()) {
				Patient object = next.toObject(Patient.class);
				patients.add(object);
			}
		}
		return patients;
	}
	
	public ApiFuture<?> deletePatient(String fName) throws InterruptedException, ExecutionException {
		Patient patient = getPatient(fName);
        Firestore fireStore = FirestoreClient.getFirestore();
        patient.setDeleted(true);
		ApiFuture<WriteResult> apiFuture = fireStore.collection(COL_NAME).document(fName).set(patient);
		return apiFuture;
	}
}
