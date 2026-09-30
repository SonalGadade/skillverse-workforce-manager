package com.skillverse.CommonFeatures;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

public class EmailService1 {
    public static final String SENDER_EMAIL = "arundhateepawar@gmail.com";
    public static final String APP_PASSWORD = "jraa dpic ekyo ogma";

    public static CompletableFuture<Boolean> sendOtpEmail(String recipientEmail, String otpCode, String userName) {
        return CompletableFuture.supplyAsync(() -> {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SENDER_EMAIL, APP_PASSWORD);
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(SENDER_EMAIL, "SkillVerse Team"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
                message.setSubject("SkillVerse Account Verification - OTP Code");

                String htmlContent = "<div style='font-family:Arial,sans-serif;padding:20px;color:#333;'>"
                        + "<h2 style='color:#4F46E5;'>SkillVerse Verification Code</h2>"
                        + "<p>Hello <b>" + userName + "</b>,</p>"
                        + "<p>Thank you for signing up with SkillVerse. Please use the following 6-digit OTP code to complete your registration:</p>"
                        + "<div style='font-size:26px;font-weight:bold;letter-spacing:5px;color:#111;padding:12px 24px;background:#F3F4F6;display:inline-block;border-radius:8px;margin:16px 0;'>"
                        + otpCode + "</div>"
                        + "<p style='color:#6B7280;font-size:13px;'>This code is valid for 5 minutes. Please do not share it with anyone.</p>"
                        + "<hr style='border:none;border-top:1px solid #E5E7EB;margin:20px 0;'/>"
                        + "<p style='font-size:12px;color:#9CA3AF;'>SkillVerse Enterprise System</p></div>";

                message.setContent(htmlContent, "text/html; charset=utf-8");
                Transport.send(message);
                System.out.println("✅ [EMAIL SENT] OTP successfully sent to: " + recipientEmail);
                return true;
            } catch (Exception e) {
                System.err.println("❌ [EMAIL ERROR] Failed to send OTP email: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        });
    }
}
