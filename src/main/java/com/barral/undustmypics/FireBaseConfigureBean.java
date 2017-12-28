package com.barral.undustmypics;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;

@Component
public class FireBaseConfigureBean implements InitializingBean {
    @Value("${firebasekeyfile}")
    String firebaseKey;


    @Override
    public void afterPropertiesSet() throws Exception {
        // Fetch the service account key JSON file contents
        FileInputStream serviceAccount = new FileInputStream(firebaseKey);

        // Initialize the app with a service account, granting admin privileges
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                .setDatabaseUrl("https://undustpics.firebaseio.com")
                .build();
        FirebaseApp.initializeApp(options);

    }

    public DatabaseReference getDatabaseReference() {
        // As an admin, the app has access to read and write all data, regardless of Security Rules
        return FirebaseDatabase
                .getInstance()
                .getReference();//(path);
    }
}
