package com.da.service;

import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Service
public class DaPatientService {
	
	
	@PostConstruct
	public void firebaseData() throws IOException {
		FileInputStream serviceAccount =
				  new FileInputStream("./dawhere-70aa2-firebase.json");

				FirebaseOptions options = new FirebaseOptions.Builder()
				  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
				  .setDatabaseUrl("https://dawhere-70aa2.firebaseio.com")
				  .build();

				FirebaseApp.initializeApp(options);
	}
	
	
}
