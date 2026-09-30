package com.skillverse.CommonFeatures;

import com.skillverse.CommonFeatures.User;

public class UserSession1 {
    public static synchronized void setCurrentUser(User user) {
        com.skillverse.CommonFeatures.UserSession.setCurrentUser(user);
    }

    public static synchronized User getCurrentUser() {
        return com.skillverse.CommonFeatures.UserSession.getCurrentUser();
    }

    public static synchronized void clearSession() {
        com.skillverse.CommonFeatures.UserSession.clearSession();
    }

    public static synchronized boolean isLoggedIn() {
        return com.skillverse.CommonFeatures.UserSession.isLoggedIn();
    }
}
