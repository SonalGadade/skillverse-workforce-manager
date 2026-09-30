package com.skillverse.FirstScreen;

import com.skillverse.admin.view.AdminDashboard;
import com.skillverse.hr.view.HR;
import com.skillverse.manager.view.ManagerDashboard;
import com.skillverse.trainer.model.Trainer;
import com.skillverse.trainer.view.DashboardPage;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneNavigator {

    private static Stage primaryStage;
    private static String currentUserEmail;

    public static void init(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("SkillVerse AI - Talent & Workforce Ecosystem");
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setCurrentUserEmail(String email) {
        currentUserEmail = email;
    }

    public static String getCurrentUserEmail() {
        return currentUserEmail;
    }

    public static void loadScene(Scene scene) {
        if (primaryStage != null && scene != null) {
            primaryStage.setScene(scene);
        }
    }

    public static void showMainPortal() {
        MainPortalView portalView = new MainPortalView();
        Scene scene = portalView.createScene();
        primaryStage.setScene(scene);
        primaryStage.setTitle("SkillVerse AI - Main Portal & Role Selection");
        primaryStage.setWidth(1360);
        primaryStage.setHeight(840);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public static void showAdminLogin() {
        Scene scene = UnifiedLoginView.createLoginScene(
                "Admin",
                "Admin Platform",
                "Manage users, departments, system configurations and platform security settings.",
                "🛡️",
                "#6366F1",
                new String[]{"User Management", "System Settings", "Data Analytics", "Platform Security"},
                email -> showAdminDashboard()
        );
        loadScene(scene);
        primaryStage.setTitle("SkillVerse AI - Admin Login");
        primaryStage.show();
    }

    public static void showHRLogin() {
        Scene scene = UnifiedLoginView.createLoginScene(
                "HR",
                "HR Platform",
                "Manage employees, recruitment, payroll, leave management, and organizational growth.",
                "👥",
                "#1E60FF",
                new String[]{"Recruitment", "Payroll & PF", "Leave Tracking", "Performance"},
                email -> showHRDashboard()
        );
        loadScene(scene);
        primaryStage.setTitle("SkillVerse AI - HR Login");
        primaryStage.show();
    }

    public static void showEmployeeLogin() {
        Scene scene = UnifiedLoginView.createLoginScene(
                "Employee",
                "Employee Platform",
                "Access individual skills, track daily tasks, learning paths and career goals.",
                "👤",
                "#8B5CF6",
                new String[]{"Skill Development", "Task Tracking", "Learning Paths", "Goal Alignment"},
                email -> showEmployeeDashboard()
        );
        loadScene(scene);
        primaryStage.setTitle("SkillVerse AI - Employee Login");
        primaryStage.show();
    }

    public static void showTrainerLogin() {
        Scene scene = UnifiedLoginView.createLoginScene(
                "Trainer",
                "Trainer Platform",
                "Design interactive courses, evaluate assessments, schedule live seminars and inspire growth.",
                "🎓",
                "#10B981",
                new String[]{"Course Creation", "Learner Progress", "Skill Evaluation", "Live Seminars"},
                email -> showTrainerDashboard()
        );
        loadScene(scene);
        primaryStage.setTitle("SkillVerse AI - Trainer Login");
        primaryStage.show();
    }

    public static void showManagerLogin() {
        ManagerDashboard.managerStage = primaryStage;
        Scene scene = UnifiedLoginView.createLoginScene(
                "Manager",
                "Manager Platform",
                "Oversee team activities, project milestones, performance reviews, approvals and analytics.",
                "📊",
                "#2563EB",
                new String[]{"Team Management", "Project Tracking", "Performance Reviews", "Smart Analytics"},
                email -> showManagerDashboard()
        );
        loadScene(scene);
        primaryStage.setTitle("SkillVerse AI - Manager Login");
        primaryStage.show();
    }

    public static void showHRDashboard() {
        HR.HRstage = primaryStage;
        UnifiedDashboardView hrView = new UnifiedDashboardView("HR", "HR Manager - HR Portal", "👥");
        Scene scene = hrView.createDashboardScene();
        primaryStage.setScene(scene);
        primaryStage.setTitle("SkillVerse AI - HR Workspace");
        primaryStage.show();
    }

    public static void showEmployeeDashboard() {
        UnifiedDashboardView empView = new UnifiedDashboardView("Employee", "Employee Workspace", "👤");
        Scene scene = empView.createDashboardScene();
        primaryStage.setScene(scene);
        primaryStage.setTitle("SkillVerse AI - Employee Workspace");
        primaryStage.show();
    }

    public static void showManagerDashboard() {
        ManagerDashboard.managerStage = primaryStage;
        UnifiedDashboardView managerView = new UnifiedDashboardView("Manager", "Manager Workspace", "📊");
        Scene scene = managerView.createDashboardScene();
        primaryStage.setScene(scene);
        primaryStage.setTitle("SkillVerse AI - Manager Workspace");
        primaryStage.show();
    }

    public static void showAdminDashboard() {
        AdminDashboard dashboard = new AdminDashboard(primaryStage);
        Scene scene = dashboard.createScene();
        primaryStage.setScene(scene);
        primaryStage.setTitle("SkillVerse AI - Admin Dashboard");
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public static void showTrainerDashboard() {
        String userEmail = (currentUserEmail != null && !currentUserEmail.isBlank())
                ? currentUserEmail
                : "trainer@skillverse.com";
        Trainer defaultTrainer = new Trainer("T1", "Alex Morgan", userEmail, "Engineering", "555-0199");
        DashboardPage.show(primaryStage, defaultTrainer);
    }

    public static void navigateToDashboard(String roleName) {
        if (roleName == null) {
            showMainPortal();
            return;
        }
        String r = roleName.toUpperCase();
        if (r.contains("HR")) {
            showHRDashboard();
        } else if (r.contains("MANAGER")) {
            showManagerDashboard();
        } else if (r.contains("ADMIN")) {
            showAdminDashboard();
        } else if (r.contains("TRAINER")) {
            showTrainerDashboard();
        } else {
            showEmployeeDashboard();
        }
    }

    public static void confirmAndLogout(String roleName) {
        if (primaryStage != null) {
            com.skillverse.CommonFeatures.ModernLogoutDialog.show(primaryStage, roleName);
        } else if (ValidationUtil.confirmLogout(roleName)) {
            clearSession();
            showMainPortal();
        }
    }

    private static void clearSession() {
        currentUserEmail = null;
        HR.HRstage = null;
        ManagerDashboard.managerStage = null;
    }
}
