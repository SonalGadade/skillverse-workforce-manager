package com.skillverse.CommonFeatures;

import com.skillverse.Dao.FirebaseDAO;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class SpeakUpRepository {

    private static SpeakUpRepository instance;
    private final List<SpeakUpTicket> tickets = new CopyOnWriteArrayList<>();
    private final List<Runnable> listeners = new CopyOnWriteArrayList<>();

    private SpeakUpRepository() {
        initSampleData();
        syncWithFirestoreAsync();
    }

    public static synchronized SpeakUpRepository getInstance() {
        if (instance == null) {
            instance = new SpeakUpRepository();
        }
        return instance;
    }

    private void initSampleData() {
        tickets.add(new SpeakUpTicket(
                "SPK-1001",
                "EMPLOYEE",
                "Alex Morgan",
                "employee@skillverse.com",
                "Workplace Culture",
                "HIGH",
                "Unreasonable weekend sprint pressure & notification overload",
                "Over the past three sprints, team members have received urgent ping alerts late Sunday evening regarding non-critical feature backlog items.",
                "UNDER_REVIEW",
                "Reviewing team allocation and off-hours messaging policy with engineering leads.",
                "Today at 10:15 AM",
                false
        ));

        tickets.add(new SpeakUpTicket(
                "SPK-1002",
                "EMPLOYEE",
                "Anonymous Employee",
                "employee@skillverse.com",
                "Management & Support",
                "URGENT",
                "Lack of hardware upgrade support for local ML model testing",
                "My current workstation lacks GPU acceleration required for benchmarking the AI skill recommendation engine, causing severe development delays.",
                "PENDING",
                null,
                "Yesterday at 4:30 PM",
                true
        ));

        tickets.add(new SpeakUpTicket(
                "SPK-1003",
                "EMPLOYEE",
                "Alex Morgan",
                "employee@skillverse.com",
                "Compensation/Benefits",
                "MEDIUM",
                "Reimbursement delay for cloud certification exam fee",
                "Submitted receipt for Spring Boot Certification exam on Aug 10, but status remains pending in expense portal.",
                "RESOLVED",
                "Approved reimbursement request. Finance team has processed payout in Payroll run.",
                "2026-08-20",
                false
        ));

        tickets.add(new SpeakUpTicket(
                "SPK-2001",
                "HR",
                "Alice Johnson (HR Lead)",
                "hr@skillverse.com",
                "Process Non-Compliance",
                "HIGH",
                "Delayed quarterly performance review submissions for Team Delta",
                "Three senior engineers in Team Delta have not completed self-appraisal submissions past the official HR cutoff deadline.",
                "UNDER_REVIEW",
                "Sent reminder to team members. Reviews will be finalized by end of week.",
                "Today at 11:00 AM",
                false
        ));

        tickets.add(new SpeakUpTicket(
                "SPK-2002",
                "HR",
                "Alice Johnson (HR Lead)",
                "hr@skillverse.com",
                "Policy Violation",
                "URGENT",
                "Inter-departmental resource allocation dispute",
                "Escalating conflicting resource allocation requests between Frontend UI team and Core Backend microservices project.",
                "PENDING",
                null,
                "2026-08-26",
                false
        ));
    }

    private void syncWithFirestoreAsync() {
        CompletableFuture.runAsync(() -> {
            try {
                for (SpeakUpTicket t : tickets) {
                    FirebaseDAO.saveSpeakUpTicket(t);
                }

                List<SpeakUpTicket> remoteTickets = FirebaseDAO.getAllSpeakUpTickets();
                if (remoteTickets != null && !remoteTickets.isEmpty()) {
                    for (SpeakUpTicket remote : remoteTickets) {
                        boolean exists = tickets.stream().anyMatch(t -> t.getTicketId().equals(remote.getTicketId()));
                        if (!exists) {
                            tickets.add(0, remote);
                        }
                    }
                    notifyListeners();
                }
            } catch (Exception e) {
                System.err.println("⚠️ [SpeakUpRepository] Async sync with Firestore failed: " + e.getMessage());
            }
        });
    }

    public synchronized void addListener(Runnable listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public synchronized void removeListener(Runnable listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
    }

    private void notifyListeners() {
        for (Runnable listener : listeners) {
            Platform.runLater(listener);
        }
    }

    public synchronized void addTicket(SpeakUpTicket ticket) {
        if (ticket != null) {
            tickets.add(0, ticket);
            notifyListeners();
            CompletableFuture.runAsync(() -> FirebaseDAO.saveSpeakUpTicket(ticket));
        }
    }

    public synchronized void updateTicketStatus(String ticketId, String newStatus, String resolutionNote) {
        for (SpeakUpTicket ticket : tickets) {
            if (ticket.getTicketId().equals(ticketId)) {
                ticket.setStatus(newStatus);
                if (resolutionNote != null && !resolutionNote.trim().isEmpty()) {
                    ticket.setManagerResolutionNote(resolutionNote.trim());
                }
                notifyListeners();

                CompletableFuture.runAsync(() -> FirebaseDAO.updateSpeakUpTicketStatus(ticketId, newStatus, resolutionNote));
                break;
            }
        }
    }

    public synchronized List<SpeakUpTicket> getTicketsForEmployee(String email) {
        return tickets.stream()
                .filter(t -> "EMPLOYEE".equalsIgnoreCase(t.getSenderRole()))
                .filter(t -> email == null || email.trim().isEmpty() || t.getSenderEmail().equalsIgnoreCase(email.trim()))
                .collect(Collectors.toList());
    }

    public synchronized List<SpeakUpTicket> getTicketsForHR(String email) {
        return tickets.stream()
                .filter(t -> "HR".equalsIgnoreCase(t.getSenderRole()))
                .collect(Collectors.toList());
    }

    public synchronized List<SpeakUpTicket> getEmployeeTicketsForManager() {
        return tickets.stream()
                .filter(t -> "EMPLOYEE".equalsIgnoreCase(t.getSenderRole()))
                .collect(Collectors.toList());
    }

    public synchronized List<SpeakUpTicket> getHREscalationsForManager() {
        return tickets.stream()
                .filter(t -> "HR".equalsIgnoreCase(t.getSenderRole()))
                .collect(Collectors.toList());
    }
}
