package com.skillverse.CommonFeatures;

public class UserSession {
    private static User currentUser;

    public static synchronized void setCurrentUser(User user) {
        currentUser = user;
        System.out.println("👤 [UserSession] Active Session Set For: " + 
                (user != null ? user.getName() + " (" + user.getEmail() + " | " + user.getRole() + ")" : "NULL"));
    }

    public static synchronized User getCurrentUser() {
        return currentUser;
    }

    public static synchronized void clearSession() {
        currentUser = null;
        System.out.println("🔒 [UserSession] Session cleared on logout.");
    }

    public static synchronized boolean isLoggedIn() {
        return currentUser != null;
    }
}
