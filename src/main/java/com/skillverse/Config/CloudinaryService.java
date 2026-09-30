package com.skillverse.Config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CloudinaryService {

    private static Cloudinary cloudinary;

    public static String CLOUD_NAME = "wkya3a11";
    public static String API_KEY = "659338157323147";
    public static String API_SECRET = "n-_uFNPNKK5iaoU-fx96aogoQpo";

    public static synchronized void initialize() {
        if (cloudinary == null) {
            try {
                cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", CLOUD_NAME,
                    "api_key", API_KEY,
                    "api_secret", API_SECRET,
                    "secure", true
                ));
                System.out.println("☁️ [CloudinaryService] Initialized successfully.");
            } catch (Exception e) {
                System.err.println("❌ [CloudinaryService] Failed to initialize: " + e.getMessage());
            }
        }
    }

    public static CompletableFuture<String> uploadImage(File file, String folderName) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                if (file == null || !file.exists()) {
                    System.err.println("❌ [CloudinaryService] File does not exist for upload.");
                    return null;
                }

                initialize();
                if (cloudinary == null) return null;

                Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.asMap(
                    "folder", "skillverse/" + (folderName != null ? folderName : "posts"),
                    "resource_type", "auto"
                ));

                String secureUrl = (String) uploadResult.get("secure_url");
                System.out.println("✅ [CloudinaryService] Image uploaded successfully: " + secureUrl);
                return secureUrl;
            } catch (Exception e) {
                System.err.println("❌ [CloudinaryService] Error uploading image to Cloudinary: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        });
    }
}
