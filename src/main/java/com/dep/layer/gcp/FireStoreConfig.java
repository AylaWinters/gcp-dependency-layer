package com.dep.layer.gcp;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class FireStoreConfig {

    private static final Firestore INSTANCE = initializeFirestore();

    private FireStoreConfig() {
    }

    @Bean
    public static Firestore initializeFirestore() {
        FirestoreOptions firestoreOptions;
        try {
            firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
                    .setProjectId(System.getenv("PROJECT_ID"))
                    .setCredentials(GoogleCredentials.getApplicationDefault())
                    .build();
            Firestore db = firestoreOptions.getService();
            return db;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Firestore getInstance() {
        return INSTANCE;
    }
}