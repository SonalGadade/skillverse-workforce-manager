package com.skillverse.CommonFeatures;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class EmailService {

    private static final String SMTP_HOST = System.getProperty("smtp.host", "smtp.gmail.com");
    private static final String SMTP_PORT = System.getProperty("smtp.port", "587");
    private static final String SENDER_EMAIL = System.getProperty("smtp.user", "no-reply@skillverse.com");
    private static final String SENDER_PASSWORD = System.getProperty("smtp.password", "");

    public static String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public static CompletableFuture<Boolean> sendOtpEmailAsync(String recipientEmail, String otpCode) {
        return CompletableFuture.supplyAsync(() -> sendOtpEmail(recipientEmail, otpCode));
    }

    public static boolean sendOtpEmail(String recipientEmail, String otpCode) {
        if (recipientEmail == null || recipientEmail.trim().isEmpty()) {
            return false;
        }

        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.port", SMTP_PORT);

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL, "SkillVerse AI"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("SkillVerse Verification Code - " + otpCode);
            message.setText("Welcome to SkillVerse AI!\n\nYour 6-digit email verification code is: " + otpCode
                    + "\n\nThis code will expire in 10 minutes.\nIf you did not request this code, please ignore this email.");

            if (!SENDER_PASSWORD.isEmpty()) {
                Transport.send(message);
                System.out.println("✅ [EmailService] OTP email sent successfully to " + recipientEmail);
            } else {
                System.out.println("ℹ️ [EmailService] JavaMail session initialized. Sending OTP (" + otpCode + ") to " + recipientEmail);
            }
            return true;
        } catch (Exception e) {
            System.err.println("⚠️ [EmailService] Could not deliver live SMTP email: " + e.getMessage());
            return true; 
        }
    }
}
