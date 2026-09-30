package com.skillverse.CommonFeatures;

import com.skillverse.Dao.FirebaseDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository {

    private static UserRepository instance;
    private final Map<String, User> userStore = new ConcurrentHashMap<>();

    private UserRepository() {
        initDefaultUsers();
        syncWithFirestoreAsync();
    }

    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    private void initDefaultUsers() {
        addUserLocal(new User("System Admin", "admin@skillverse.com", "admin123", "Admin", "Administration"));
        addUserLocal(new User("HR Manager", "hr@skillverse.com", "hr123", "HR", "Human Resources"));
        addUserLocal(new User("Department Manager", "manager@skillverse.com", "manager123", "Manager", "Engineering"));
        addUserLocal(new User("Alex Morgan", "employee@skillverse.com", "emp123", "Employee", "Product Development"));
        addUserLocal(new User("Lead Trainer", "trainer@skillverse.com", "trainer123", "Trainer", "Learning & Development"));
    }

    private void addUserLocal(User u) {
        if (u != null && u.getEmail() != null) {
            userStore.put(u.getEmail().trim().toLowerCase(), u);
        }
    }

    private void syncWithFirestoreAsync() {
        CompletableFuture.runAsync(() -> {
            try {
                for (User defaultUser : userStore.values()) {
                    User existing = FirebaseDAO.getUserByEmail(defaultUser.getEmail());
                    if (existing == null) {
                        FirebaseDAO.getInstance().saveUser(defaultUser);
                    }
                }

                List<User> remoteUsers = FirebaseDAO.getAllUsers();
                for (User u : remoteUsers) {
                    addUserLocal(u);
                }
            } catch (Exception e) {
                System.err.println("⚠️ [UserRepository] Async sync with Firestore failed: " + e.getMessage());
            }
        });
    }

    public synchronized boolean registerUser(String fullName, String email, String password, String role, String department) {
        if (email == null || email.trim().isEmpty()) return false;
        String key = email.trim().toLowerCase();
        User newUser = new User(fullName, key, password, role, department);
        userStore.put(key, newUser);

        CompletableFuture.runAsync(() -> FirebaseDAO.getInstance().saveUser(newUser));
        return true;
    }

    public synchronized boolean validateCredentials(String email, String password, String role) {
        if (email == null || password == null || email.trim().isEmpty()) return false;
        String key = email.trim().toLowerCase();

        User user = userStore.get(key);
        if (user != null) {
            boolean validPass = user.getPassword().equals(password);
            boolean validRole = (role == null || user.getRole() == null || user.getRole().equalsIgnoreCase(role));
            if (validPass && validRole) {
                System.out.println("✅ [AUTH SUCCESS] Cached user logged in: " + key);
                return true;
            }
        }

        try {
            User remoteUser = FirebaseDAO.getInstance().authenticateUser(key, password, role).get();
            if (remoteUser != null) {
                addUserLocal(remoteUser);
                return true;
            }
        } catch (Exception e) {
            System.err.println("❌ [AUTH ERROR] Exception during Firestore authentication lookup: " + e.getMessage());
        }

        return false;
    }

    public synchronized boolean userExists(String email) {
        if (email == null) return false;
        String key = email.trim().toLowerCase();
        if (userStore.containsKey(key)) return true;

        User remote = FirebaseDAO.getUserByEmail(key);
        if (remote != null) {
            addUserLocal(remote);
            return true;
        }
        return false;
    }

    public synchronized User findUser(String email) {
        if (email == null) return null;
        String key = email.trim().toLowerCase();
        User local = userStore.get(key);
        if (local != null) return local;

        User remote = FirebaseDAO.getUserByEmail(key);
        if (remote != null) {
            addUserLocal(remote);
            return remote;
        }
        return null;
    }
}
