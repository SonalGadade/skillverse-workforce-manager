package com.skillverse.Config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;

import java.io.InputStream;

public class FirebaseConfig {

    private static boolean initialized = false;
    private static Firestore firestore;
    private static FirebaseAuth auth;

    public static synchronized void initialize() {
        if (initialized) {
            return;
        }

        try {
            InputStream serviceAccount = FirebaseConfig.class.getResourceAsStream("/serviceAccountKey.json");
            if (serviceAccount == null) {
                serviceAccount = FirebaseConfig.class.getResourceAsStream("/assets/serviceAccountKey.json");
            }

            if (serviceAccount == null) {
                System.err.println("⚠️ [FirebaseConfig] serviceAccountKey.json not found in resources classpath!");
                return;
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
            System.out.println("[FIREBASE] Successfully connected to project: " + FirebaseApp.getInstance().getName());

            firestore = FirestoreClient.getFirestore();
            auth = FirebaseAuth.getInstance();
            initialized = true;

        } catch (Exception e) {
            System.err.println("❌ [FirebaseConfig] Failed to initialize Firebase Admin SDK: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Firestore getFirestore() {
        if (!initialized) {
            initialize();
        }
        return firestore;
    }

    public static FirebaseAuth getAuth() {
        if (!initialized) {
            initialize();
        }
        return auth;
    }

    public static boolean isInitialized() {
        return initialized;
    }
}
