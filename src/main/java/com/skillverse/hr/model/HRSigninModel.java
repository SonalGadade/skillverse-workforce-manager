package com.skillverse.hr.model;

public class HRSigninModel {

    private String email;

    private String phoneNumber;

    private boolean otpVerification;

    private String otp;

    public HRSigninModel() {

        this.email = "";

        this.phoneNumber = "";

        this.otpVerification = false;

        this.otp = "";
    }

    public HRSigninModel(
        String email,
        String phoneNumber,
        boolean otpVerification,
        String otp
    ) {

        this.email = email;

        this.phoneNumber = phoneNumber;

        this.otpVerification = otpVerification;

        this.otp = otp;
    }

    public String getEmail() {

        return email;
    }

    public String getPhoneNumber() {

        return phoneNumber;
    }

    public boolean isOtpVerification() {

        return otpVerification;
    }

    public String getOtp() {

        return otp;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {

        this.phoneNumber = phoneNumber;
    }

    public void setOtpVerification(
        boolean otpVerification
    ) {

        this.otpVerification = otpVerification;
    }

    public void setOtp(String otp) {

        this.otp = otp;
    }

    public boolean isEmailValid() {

        if (
            email == null ||
            email.trim().isEmpty()
        ) {

            return false;
        }

        return email.matches(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        );
    }

    public boolean isPhoneValid() {

        if (
            phoneNumber == null ||
            phoneNumber.trim().isEmpty()
        ) {

            return false;
        }

        return phoneNumber.matches(
            "^[0-9]{10}$"
        );
    }

    public boolean isOtpValid() {

        if (
            otp == null ||
            otp.trim().isEmpty()
        ) {

            return false;
        }

        return otp.matches(
            "^[0-9]{6}$"
        );
    }

    public boolean isValid() {

        if (
            !isEmailValid() ||
            !isPhoneValid()
        ) {

            return false;
        }

        if (otpVerification) {

            return isOtpValid();
        }

        return true;
    }

    public void clear() {

        email = "";

        phoneNumber = "";

        otpVerification = false;

        otp = "";
    }
}