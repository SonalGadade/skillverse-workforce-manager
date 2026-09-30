package com.skillverse.Dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.skillverse.CommonFeatures.Feedback;
import com.skillverse.CommonFeatures.SpeakUpTicket;
import com.skillverse.CommonFeatures.User;
import com.skillverse.CommonFeatures.UserSession;
import com.skillverse.Config.FirebaseConfig;
import com.skillverse.CommonFeatures.AppNotification;
import com.skillverse.CommonFeatures.AuditLog;
import com.skillverse.CommonFeatures.EmployeeFeedback;
import com.skillverse.CommonFeatures.EmployeeSkill;
import com.skillverse.CommonFeatures.EmployeeTask;
import com.skillverse.CommonFeatures.FeedPost;
import com.skillverse.CommonFeatures.JobApplication;
import com.skillverse.CommonFeatures.JobPosting;
import com.skillverse.CommonFeatures.MasterSkill;
import com.skillverse.CommonFeatures.PostComment;
import com.skillverse.CommonFeatures.SeminarEvent;
import com.skillverse.CommonFeatures.SystemApproval;
import com.skillverse.CommonFeatures.TrainerAnnouncement;
import com.skillverse.CommonFeatures.TrainerAssessment;
import com.skillverse.CommonFeatures.TrainingEnrollment;
import com.skillverse.CommonFeatures.TrainingProgram;
import com.skillverse.CommonFeatures.SocialPost;
import com.skillverse.CommonFeatures.LeaveRequest;
import com.skillverse.employee.model.AIChatMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Set;

import java.util.concurrent.CompletableFuture;

public class FirebaseDAO {

    private static FirebaseDAO instance;
    private static final String USERS_COLLECTION = "users";
    private static final String EMPLOYEES_COLLECTION = "employees";
    private static final String JOB_POSTINGS_COLLECTION = "job_postings";
    private static final String JOB_APPLICATIONS_COLLECTION = "job_applications";
    private static final String TRAINING_PROGRAMS_COLLECTION = "training_programs";
    private static final String TRAINING_ENROLLMENTS_COLLECTION = "training_enrollments";
    private static final String TRAINING_COURSES_COLLECTION = "training_courses";
    private static final String PERFORMANCE_REVIEWS_COLLECTION = "performance_reviews";
    private static final String SPEAK_UP_COLLECTION = "speak_up_tickets";
    private static final String FEEDBACKS_COLLECTION = "feedbacks";
    private static final String POSTS_COLLECTION = "posts";
    private static final String FEED_POSTS_COLLECTION = "feed_posts";
    private static final String SEMINAR_EVENTS_COLLECTION = "seminar_events";
    private static final String TRAINER_ASSESSMENTS_COLLECTION = "trainer_assessments";
    private static final String TRAINER_ANNOUNCEMENTS_COLLECTION = "trainer_announcements";
    private static final String EMPLOYEE_TASKS_COLLECTION = "employee_tasks";
    private static final String EMPLOYEE_SKILLS_COLLECTION = "employee_skills";
    private static final String EMPLOYEE_FEEDBACK_COLLECTION = "employee_feedback";
    private static final String LEAVE_REQUESTS_COLLECTION = "leave_requests";
    private static final List<LeaveRequest> memoryLeaveRequests = new ArrayList<>();

    public static synchronized FirebaseDAO getInstance() {
        if (instance == null) {
            instance = new FirebaseDAO();
        }
        return instance;
    }

    public static boolean saveUserStatic(User user) {
        return getInstance().saveUser(user);
    }

   

    public boolean saveUser(User user) {
        try {
            Firestore db = FirebaseConfig.getFirestore();
            if (db == null || user == null || user.getEmail() == null) {
                return false;
            }
            String docId = user.getEmail().trim().toLowerCase();

            Map<String, Object> userData = new HashMap<>();
            userData.put("fullName", user.getFullName());
            userData.put("email", docId);
            userData.put("password", user.getPassword());
            userData.put("role", user.getRole() != null ? user.getRole().toUpperCase() : "EMPLOYEE");
            userData.put("department", user.getDepartment());
            userData.put("createdAt", FieldValue.serverTimestamp());

            db.collection(USERS_COLLECTION).document(docId).set(userData).get();
            System.out.println("✅ User saved successfully to Firestore: " + docId);
            return true;
        } catch (Exception e) {
            System.err.println("❌ [FirebaseDAO] Error saving user to Firestore: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public CompletableFuture<User> authenticateUser(String email, String rawPassword, String role) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return null;

                DocumentSnapshot doc = db.collection(USERS_COLLECTION).document(email.trim().toLowerCase()).get().get();
                if (!doc.exists()) {
                    System.out.println("❌ [AUTH FAILED] User not found: " + email);
                    return null;
                }

                String storedPassword = doc.getString("password");
                String storedRole = doc.getString("role");

                if (storedPassword != null && storedPassword.equals(rawPassword)) {
                    if (role == null || storedRole.equalsIgnoreCase(role)) {
                        User user = new User();
                        user.setEmail(doc.getString("email"));
                        user.setFullName(doc.getString("fullName"));
                        user.setPassword(storedPassword);
                        user.setRole(storedRole);
                        user.setDepartment(doc.getString("department"));
                        user.setProfileImageUrl(doc.getString("profileImageUrl"));
                        System.out.println("✅ [AUTH SUCCESS] Logged in as: " + email + " (" + storedRole + ")");
                        return user;
                    } else {
                        System.out.println("❌ [AUTH FAILED] Role mismatch. Expected: " + role + ", Stored: " + storedRole);
                    }
                } else {
                    System.out.println("❌ [AUTH FAILED] Incorrect password for: " + email);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public CompletableFuture<Boolean> isEmailRegistered(String email) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || email == null) return false;

                DocumentSnapshot doc = db.collection(USERS_COLLECTION).document(email.trim().toLowerCase()).get().get();
                return doc.exists();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        });
    }

    public CompletableFuture<Boolean> updateUserPassword(String email, String newPassword) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || email == null || newPassword == null) return false;

                db.collection(USERS_COLLECTION).document(email.trim().toLowerCase())
                  .update("password", newPassword, "updatedAt", FieldValue.serverTimestamp())
                  .get();
                System.out.println("✅ [PASSWORD RESET] Password successfully updated for: " + email);
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error updating user password: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        });
    }

    public static User getUserByEmail(String email) {
        try {
            Firestore db = FirebaseConfig.getFirestore();
            if (db == null || email == null) {
                return null;
            }
            String docId = email.trim().toLowerCase();
            DocumentSnapshot snapshot = db.collection(USERS_COLLECTION).document(docId).get().get();

            if (snapshot.exists()) {
                return snapshot.toObject(User.class);
            }
        } catch (Exception e) {
            System.err.println("❌ [FirebaseDAO] Error fetching user from Firestore: " + e.getMessage());
        }
        return null;
    }

    public static List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try {
            Firestore db = FirebaseConfig.getFirestore();
            if (db == null) return list;

            QuerySnapshot querySnapshot = db.collection(USERS_COLLECTION).get().get();
            for (QueryDocumentSnapshot doc : querySnapshot.getDocuments()) {
                User u = doc.toObject(User.class);
                if (u != null) {
                    list.add(u);
                }
            }
        } catch (Exception e) {
            System.err.println("❌ [FirebaseDAO] Error fetching all users: " + e.getMessage());
        }
        return list;
    }



    public static boolean saveSpeakUpTicket(SpeakUpTicket ticket) {
        try {
            Firestore db = FirebaseConfig.getFirestore();
            if (db == null || ticket == null || ticket.getTicketId() == null) {
                return false;
            }
            String docId = ticket.getTicketId();

            ApiFuture<WriteResult> result = db.collection(SPEAK_UP_COLLECTION).document(docId).set(ticket);
            result.get();
            System.out.println("✅ [FirebaseDAO] SpeakUpTicket saved to Firestore: " + docId);
            return true;
        } catch (Exception e) {
            System.err.println("❌ [FirebaseDAO] Error saving ticket to Firestore: " + e.getMessage());
            return false;
        }
    }

    public static List<SpeakUpTicket> getAllSpeakUpTickets() {
        List<SpeakUpTicket> list = new ArrayList<>();
        try {
            Firestore db = FirebaseConfig.getFirestore();
            if (db == null) return list;

            QuerySnapshot querySnapshot = db.collection(SPEAK_UP_COLLECTION).get().get();
            for (QueryDocumentSnapshot doc : querySnapshot.getDocuments()) {
                SpeakUpTicket t = doc.toObject(SpeakUpTicket.class);
                if (t != null) {
                    list.add(t);
                }
            }
        } catch (Exception e) {
            System.err.println("❌ [FirebaseDAO] Error fetching speak_up_tickets: " + e.getMessage());
        }
        return list;
    }

    public static boolean updateSpeakUpTicketStatus(String ticketId, String newStatus, String resolutionNote) {
        try {
            Firestore db = FirebaseConfig.getFirestore();
            if (db == null || ticketId == null) {
                return false;
            }
            DocumentReference docRef = db.collection(SPEAK_UP_COLLECTION).document(ticketId);

            Map<String, Object> updates = new HashMap<>();
            updates.put("status", newStatus);
            if (resolutionNote != null && !resolutionNote.trim().isEmpty()) {
                updates.put("managerResolutionNote", resolutionNote.trim());
            }

            docRef.update(updates).get();
            System.out.println("✅ [FirebaseDAO] Ticket updated in Firestore: " + ticketId);
            return true;
        } catch (Exception e) {
            System.err.println("❌ [FirebaseDAO] Error updating ticket status: " + e.getMessage());
            return false;
        }
    }



    public static boolean saveFeedback(Feedback feedback) {
        try {
            Firestore db = FirebaseConfig.getFirestore();
            if (db == null || feedback == null || feedback.getFeedbackId() == null) {
                return false;
            }
            String docId = feedback.getFeedbackId();

            ApiFuture<WriteResult> result = db.collection(FEEDBACKS_COLLECTION).document(docId).set(feedback);
            result.get();
            System.out.println("✅ [FirebaseDAO] Feedback saved to Firestore: " + docId);
            return true;
        } catch (Exception e) {
            System.err.println("❌ [FirebaseDAO] Error saving feedback to Firestore: " + e.getMessage());
            return false;
        }
    }

    public static List<Feedback> getAllCommonFeedbacks() {
        List<Feedback> list = new ArrayList<>();
        try {
            Firestore db = FirebaseConfig.getFirestore();
            if (db == null) return list;

            QuerySnapshot querySnapshot = db.collection(FEEDBACKS_COLLECTION).get().get();
            for (QueryDocumentSnapshot doc : querySnapshot.getDocuments()) {
                Feedback f = doc.toObject(Feedback.class);
                if (f != null) {
                    list.add(f);
                }
            }
        } catch (Exception e) {
            System.err.println("❌ [FirebaseDAO] Error fetching feedbacks: " + e.getMessage());
        }
        return list;
    }

    public static boolean acknowledgeFeedback(String feedbackId) {
        try {
            Firestore db = FirebaseConfig.getFirestore();
            if (db == null || feedbackId == null) {
                return false;
            }
            DocumentReference docRef = db.collection(FEEDBACKS_COLLECTION).document(feedbackId);
            docRef.update("acknowledged", true).get();
            System.out.println("✅ [FirebaseDAO] Feedback acknowledged in Firestore: " + feedbackId);
            return true;
        } catch (Exception e) {
            System.err.println("❌ [FirebaseDAO] Error acknowledging feedback: " + e.getMessage());
            return false;
        }
    }


    public static boolean savePost(SocialPost post) {
        try {
            Firestore db = FirebaseConfig.getFirestore();
            if (db == null || post == null) return false;

            if (post.getPostId() == null || post.getPostId().isEmpty()) {
                post.setPostId(db.collection(POSTS_COLLECTION).document().getId());
            }

            db.collection(POSTS_COLLECTION).document(post.getPostId()).set(post).get();
            System.out.println("✅ [FirebaseDAO] Post saved to Firestore: " + post.getPostId());
            return true;
        } catch (Exception e) {
            System.err.println("❌ [FirebaseDAO] Error saving post to Firestore: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static List<SocialPost> getAllPosts() {
        List<SocialPost> list = new ArrayList<>();
        try {
            Firestore db = FirebaseConfig.getFirestore();
            if (db == null) return list;

            QuerySnapshot snapshot = db.collection(POSTS_COLLECTION).get().get();
            for (QueryDocumentSnapshot doc : snapshot.getDocuments()) {
                SocialPost post = doc.toObject(SocialPost.class);
                if (post != null) {
                    list.add(post);
                }
            }
            list.sort((p1, p2) -> Long.compare(p2.getTimestamp(), p1.getTimestamp()));
        } catch (Exception e) {
            System.err.println("❌ [FirebaseDAO] Error fetching posts from Firestore: " + e.getMessage());
        }
        return list;
    }

    public static boolean toggleLikePost(String postId, String userEmail) {
        try {
            Firestore db = FirebaseConfig.getFirestore();
            if (db == null || postId == null || userEmail == null) return false;

            DocumentReference docRef = db.collection(POSTS_COLLECTION).document(postId);
            DocumentSnapshot snapshot = docRef.get().get();
            if (snapshot.exists()) {
                SocialPost post = snapshot.toObject(SocialPost.class);
                if (post != null) {
                    List<String> likes = post.getLikedByEmails();
                    if (likes.contains(userEmail)) {
                        likes.remove(userEmail);
                    } else {
                        likes.add(userEmail);
                    }
                    docRef.update("likedByEmails", likes).get();
                    return true;
                }
            }
        } catch (Exception e) {
            System.err.println("❌ [FirebaseDAO] Error toggling like: " + e.getMessage());
        }
        return false;
    }

    public static boolean addCommentToPost(String postId, SocialPost.Comment comment) {
        try {
            Firestore db = FirebaseConfig.getFirestore();
            if (db == null || postId == null || comment == null) return false;

            DocumentReference docRef = db.collection(POSTS_COLLECTION).document(postId);
            DocumentSnapshot snapshot = docRef.get().get();
            if (snapshot.exists()) {
                SocialPost post = snapshot.toObject(SocialPost.class);
                if (post != null) {
                    List<SocialPost.Comment> comments = post.getComments();
                    comments.add(comment);
                    docRef.update("comments", comments).get();
                    return true;
                }
            }
        } catch (Exception e) {
            System.err.println("❌ [FirebaseDAO] Error adding comment: " + e.getMessage());
        }
        return false;
    }

    

    public CompletableFuture<Boolean> createPost(FeedPost post) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || post == null) return false;

                DocumentReference docRef = db.collection(FEED_POSTS_COLLECTION).document();
                post.setId(docRef.getId());

                Map<String, Object> postMap = new HashMap<>();
                postMap.put("id", post.getId());
                postMap.put("authorName", post.getAuthorName());
                postMap.put("authorEmail", post.getAuthorEmail());
                postMap.put("authorRole", post.getAuthorRole());
                postMap.put("authorDepartment", post.getAuthorDepartment());
                postMap.put("content", post.getContent());
                postMap.put("postType", post.getPostType() != null ? post.getPostType() : "GENERAL");
                postMap.put("imageUrl", post.getImageUrl());
                postMap.put("likesCount", post.getLikesCount());
                postMap.put("likedBy", post.getLikedBy() != null ? post.getLikedBy() : new ArrayList<>());
                postMap.put("createdAt", FieldValue.serverTimestamp());

                docRef.set(postMap).get();
                System.out.println("✅ [FirebaseDAO] FeedPost created successfully in Firestore: " + post.getId());
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error creating post: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        });
    }

    public CompletableFuture<List<FeedPost>> getAllFeedPosts() {
        return CompletableFuture.supplyAsync(() -> {
            List<FeedPost> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return list;

                QuerySnapshot snapshot = db.collection(FEED_POSTS_COLLECTION)
                        .orderBy("createdAt", Query.Direction.DESCENDING)
                        .get().get();

                for (QueryDocumentSnapshot doc : snapshot.getDocuments()) {
                    FeedPost post = doc.toObject(FeedPost.class);
                    if (post != null) {
                        list.add(post);
                    }
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching feed_posts from Firestore: " + e.getMessage());
            }
            return list;
        });
    }

    public CompletableFuture<Boolean> toggleLike(String postId, String userEmail) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || postId == null || userEmail == null) return false;

                DocumentReference docRef = db.collection(FEED_POSTS_COLLECTION).document(postId);
                DocumentSnapshot doc = docRef.get().get();

                if (doc.exists()) {
                    List<String> likedBy = (List<String>) doc.get("likedBy");
                    if (likedBy == null) likedBy = new ArrayList<>();

                    if (likedBy.contains(userEmail)) {
                        docRef.update("likedBy", FieldValue.arrayRemove(userEmail), "likesCount", FieldValue.increment(-1)).get();
                    } else {
                        docRef.update("likedBy", FieldValue.arrayUnion(userEmail), "likesCount", FieldValue.increment(1)).get();
                    }
                    return true;
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error toggling like: " + e.getMessage());
            }
            return false;
        });
    }

    public CompletableFuture<Boolean> addComment(String postId, PostComment comment) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || postId == null || comment == null) return false;

                if (comment.getCommentId() == null || comment.getCommentId().isEmpty()) {
                    comment.setCommentId(db.collection(FEED_POSTS_COLLECTION).document(postId).collection("comments").document().getId());
                }
                comment.setPostId(postId);

                Map<String, Object> commentMap = new HashMap<>();
                commentMap.put("commentId", comment.getCommentId());
                commentMap.put("postId", postId);
                commentMap.put("authorName", comment.getAuthorName());
                commentMap.put("authorEmail", comment.getAuthorEmail());
                commentMap.put("authorRole", comment.getAuthorRole());
                commentMap.put("text", comment.getText());
                commentMap.put("createdAt", FieldValue.serverTimestamp());

                db.collection(FEED_POSTS_COLLECTION).document(postId)
                  .collection("comments").document(comment.getCommentId())
                  .set(commentMap).get();

                db.collection(FEED_POSTS_COLLECTION).document(postId)
                  .update("commentsCount", FieldValue.increment(1)).get();

                System.out.println("✅ [FirebaseDAO] Comment added to Firestore post: " + postId);
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error adding comment: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        });
    }

    public CompletableFuture<List<PostComment>> getComments(String postId) {
        return CompletableFuture.supplyAsync(() -> {
            List<PostComment> commentsList = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || postId == null) return commentsList;

                QuerySnapshot snap = db.collection(FEED_POSTS_COLLECTION).document(postId)
                        .collection("comments").orderBy("createdAt", Query.Direction.ASCENDING).get().get();

                for (DocumentSnapshot doc : snap.getDocuments()) {
                    PostComment c = doc.toObject(PostComment.class);
                    if (c != null) commentsList.add(c);
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching comments from Firestore: " + e.getMessage());
            }
            return commentsList;
        });
    }



    public CompletableFuture<Boolean> updateUserProfileAsync(String email, String fullName, String phone, String department, String profileImageUrl) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || email == null) return false;
                String docId = email.trim().toLowerCase();

                Map<String, Object> updates = new HashMap<>();
                if (fullName != null) updates.put("fullName", fullName);
                if (phone != null) updates.put("phone", phone);
                if (department != null) updates.put("department", department);
                if (profileImageUrl != null) updates.put("profileImageUrl", profileImageUrl);
                updates.put("updatedAt", FieldValue.serverTimestamp());

                db.collection(USERS_COLLECTION).document(docId).set(updates, SetOptions.merge()).get();
                System.out.println("✅ [FirebaseDAO] User profile updated in Firestore: " + docId);
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error updating user profile: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        });
    }

    public CompletableFuture<List<User>> getAllEmployeesAsync() {
        return CompletableFuture.supplyAsync(() -> {
            List<User> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return list;

                QuerySnapshot snap = db.collection(USERS_COLLECTION).get().get();
                for (QueryDocumentSnapshot doc : snap.getDocuments()) {
                    User u = new User();
                    u.setEmail(doc.getString("email") != null ? doc.getString("email") : doc.getId());
                    u.setFullName(doc.getString("fullName") != null ? doc.getString("fullName") : "Employee");
                    u.setRole(doc.getString("role") != null ? doc.getString("role") : "EMPLOYEE");
                    u.setDepartment(doc.getString("department") != null ? doc.getString("department") : "General");
                    u.setProfileImageUrl(doc.getString("profileImageUrl"));
                    u.setPhone(doc.getString("phone") != null ? doc.getString("phone") : "+1 (555) 019-2834");
                    
                    String st = doc.getString("status");
                    u.setStatus(st != null ? st : "Active");

                    String jd = doc.getString("joiningDate");
                    if (jd == null || jd.isBlank()) jd = "2024-01-15";
                    u.setJoiningDate(jd);

                    list.add(u);
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching employees: " + e.getMessage());
            }
            return list;
        });
    }

    public CompletableFuture<Boolean> addEmployeeAsync(User employee) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || employee == null || employee.getEmail() == null) return false;
                String docId = employee.getEmail().trim().toLowerCase();

                Map<String, Object> data = new HashMap<>();
                data.put("fullName", employee.getFullName());
                data.put("email", docId);
                data.put("role", employee.getRole() != null ? employee.getRole().toUpperCase() : "EMPLOYEE");
                data.put("department", employee.getDepartment());
                data.put("phone", employee.getPhone() != null ? employee.getPhone() : "");
                data.put("status", employee.getStatus() != null ? employee.getStatus() : "Active");
                data.put("joiningDate", employee.getJoiningDate() != null ? employee.getJoiningDate() : "2026-08-28");
                data.put("password", employee.getPassword() != null ? employee.getPassword() : "password123");
                data.put("createdAt", FieldValue.serverTimestamp());

                db.collection(USERS_COLLECTION).document(docId).set(data, SetOptions.merge()).get();
                db.collection(EMPLOYEES_COLLECTION).document(docId).set(data, SetOptions.merge()).get();
                System.out.println("✅ [FirebaseDAO] Employee saved to Firestore: " + docId);
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error adding employee: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<Boolean> updateEmployeeStatusAndDeptAsync(String email, String status, String department, String role) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || email == null) return false;
                String docId = email.trim().toLowerCase();

                Map<String, Object> updates = new HashMap<>();
                if (status != null) updates.put("status", status);
                if (department != null) updates.put("department", department);
                if (role != null) updates.put("role", role.toUpperCase());
                updates.put("updatedAt", FieldValue.serverTimestamp());

                db.collection(USERS_COLLECTION).document(docId).set(updates, SetOptions.merge()).get();
                db.collection(EMPLOYEES_COLLECTION).document(docId).set(updates, SetOptions.merge()).get();
                System.out.println("✅ [FirebaseDAO] Employee updated in Firestore: " + docId);
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error updating employee: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<Boolean> offboardEmployeeAsync(String email) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || email == null) return false;
                String docId = email.trim().toLowerCase();

                Map<String, Object> updates = new HashMap<>();
                updates.put("status", "OFFBOARDED");
                updates.put("updatedAt", FieldValue.serverTimestamp());

                db.collection(USERS_COLLECTION).document(docId).set(updates, SetOptions.merge()).get();
                db.collection(EMPLOYEES_COLLECTION).document(docId).set(updates, SetOptions.merge()).get();
                System.out.println("✅ [FirebaseDAO] Employee offboarded in Firestore: " + docId);
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error offboarding employee: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<Boolean> deleteUserPermanentlyAsync(String email) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || email == null) return false;
                String docId = email.trim().toLowerCase();

                db.collection(USERS_COLLECTION).document(docId).delete().get();
                try {
                    db.collection(EMPLOYEES_COLLECTION).document(docId).delete().get();
                } catch (Exception ex) {}
                System.out.println("✅ [FirebaseDAO] User permanently deleted from Firestore: " + docId);
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error deleting user permanently: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<Boolean> createJobPostingAsync(Map<String, Object> jobData) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || jobData == null) return false;

                jobData.put("createdAt", FieldValue.serverTimestamp());
                if (!jobData.containsKey("status")) {
                    jobData.put("status", "OPEN");
                }

                DocumentReference docRef = db.collection(JOB_POSTINGS_COLLECTION).document();
                jobData.put("id", docRef.getId());
                docRef.set(jobData).get();
                System.out.println("✅ [FirebaseDAO] Job Posting created in Firestore: " + docRef.getId());
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error creating job posting: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<List<Map<String, Object>>> getAllJobPostingsAsync() {
        return CompletableFuture.supplyAsync(() -> {
            List<Map<String, Object>> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return list;

                QuerySnapshot snap = db.collection(JOB_POSTINGS_COLLECTION).get().get();
                for (QueryDocumentSnapshot doc : snap.getDocuments()) {
                    Map<String, Object> data = doc.getData();
                    data.put("id", doc.getId());
                    list.add(data);
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching job postings: " + e.getMessage());
            }
            return list;
        });
    }

    public CompletableFuture<Boolean> addJobApplicationAsync(Map<String, Object> appData) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || appData == null) return false;

                appData.put("createdAt", FieldValue.serverTimestamp());
                if (!appData.containsKey("stage")) {
                    appData.put("stage", "Applied");
                }

                DocumentReference docRef = db.collection(JOB_APPLICATIONS_COLLECTION).document();
                appData.put("id", docRef.getId());
                docRef.set(appData).get();
                System.out.println("✅ [FirebaseDAO] Job Application created in Firestore: " + docRef.getId());
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error adding job application: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<List<Map<String, Object>>> getAllJobApplicationsAsync() {
        return CompletableFuture.supplyAsync(() -> {
            List<Map<String, Object>> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return list;

                QuerySnapshot snap = db.collection(JOB_APPLICATIONS_COLLECTION).get().get();
                for (QueryDocumentSnapshot doc : snap.getDocuments()) {
                    Map<String, Object> data = doc.getData();
                    data.put("id", doc.getId());
                    list.add(data);
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching candidate applications: " + e.getMessage());
            }
            return list;
        });
    }

    public CompletableFuture<Boolean> updateCandidateStageAsync(String candidateId, String newStage) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || candidateId == null) return false;

                db.collection(JOB_APPLICATIONS_COLLECTION).document(candidateId)
                  .update("stage", newStage, "updatedAt", FieldValue.serverTimestamp())
                  .get();
                System.out.println("✅ [FirebaseDAO] Candidate stage updated in Firestore: " + candidateId + " -> " + newStage);
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error updating candidate stage: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<Boolean> createJobPosting(JobPosting job) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || job == null) return false;

                DocumentReference docRef = db.collection(JOB_POSTINGS_COLLECTION).document();
                String jId = docRef.getId();
                job.setJobId(jId);

                Map<String, Object> data = new HashMap<>();
                data.put("jobId", jId);
                data.put("id", jId);
                data.put("title", job.getTitle());
                data.put("department", job.getDepartment());
                data.put("experience", job.getExperience());
                data.put("location", job.getLocation() != null ? job.getLocation() : "Remote / Hybrid");
                data.put("jobType", job.getJobType() != null ? job.getJobType() : "Full-time");
                data.put("salaryRange", job.getSalaryRange());
                data.put("description", job.getDescription());
                data.put("skillsRequired", job.getSkillsRequired() != null ? job.getSkillsRequired() : "");
                data.put("postedByEmail", job.getPostedByEmail() != null ? job.getPostedByEmail() : "");
                data.put("status", job.getStatus() != null ? job.getStatus().toUpperCase() : "OPEN");
                data.put("createdAt", FieldValue.serverTimestamp());

                docRef.set(data).get();
                System.out.println("✅ [FirebaseDAO] Job Posting created: " + jId);
                logActivity("Created job opening: " + job.getTitle(), "Jobs", "SUCCESS");
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error creating job posting: " + e.getMessage());
                logActivity("Failed creating job opening: " + (job != null ? job.getTitle() : "Unknown"), "Jobs", "FAILED");
                return false;
            }
        });
    }

    public CompletableFuture<List<JobPosting>> getActiveJobs() {
        return CompletableFuture.supplyAsync(() -> {
            List<JobPosting> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return list;

                QuerySnapshot snap = db.collection(JOB_POSTINGS_COLLECTION).get().get();
                for (QueryDocumentSnapshot doc : snap.getDocuments()) {
                    String st = doc.getString("status");
                    if ("CLOSED".equalsIgnoreCase(st)) continue;

                    JobPosting jp = new JobPosting();
                    jp.setJobId(doc.getString("jobId") != null ? doc.getString("jobId") : doc.getId());
                    jp.setTitle(doc.getString("title") != null ? doc.getString("title") : "Open Position");
                    jp.setDepartment(doc.getString("department") != null ? doc.getString("department") : "General");
                    jp.setExperience(doc.getString("experience") != null ? doc.getString("experience") : "1-3 Years");
                    jp.setLocation(doc.getString("location") != null ? doc.getString("location") : "Remote / Hybrid");
                    jp.setJobType(doc.getString("jobType") != null ? doc.getString("jobType") : "Full-time");
                    jp.setSalaryRange(doc.getString("salaryRange") != null ? doc.getString("salaryRange") : "Competitive");
                    jp.setDescription(doc.getString("description") != null ? doc.getString("description") : "");
                    jp.setSkillsRequired(doc.getString("skillsRequired") != null ? doc.getString("skillsRequired") : "");
                    jp.setPostedByEmail(doc.getString("postedByEmail") != null ? doc.getString("postedByEmail") : "");
                    jp.setStatus(st != null ? st : "OPEN");
                    jp.setCreatedAt(doc.get("createdAt") != null ? doc.get("createdAt").toString() : "");

                    list.add(jp);
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching active jobs: " + e.getMessage());
            }
            return list;
        });
    }

    public CompletableFuture<Boolean> closeJobPostingAsync(String jobId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || jobId == null) return false;

                db.collection(JOB_POSTINGS_COLLECTION).document(jobId)
                  .update("status", "CLOSED", "updatedAt", FieldValue.serverTimestamp())
                  .get();
                System.out.println("✅ [FirebaseDAO] Job Posting closed: " + jobId);
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error closing job posting: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<Boolean> applyForJob(JobApplication app) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || app == null) return false;

                DocumentReference docRef = db.collection(JOB_APPLICATIONS_COLLECTION).document();
                String appId = docRef.getId();
                app.setApplicationId(appId);

                Map<String, Object> data = new HashMap<>();
                data.put("applicationId", appId);
                data.put("id", appId);
                data.put("jobId", app.getJobId() != null ? app.getJobId() : "");
                data.put("jobTitle", app.getJobTitle() != null ? app.getJobTitle() : "");
                data.put("applicantName", app.getApplicantName() != null ? app.getApplicantName() : "");
                data.put("applicantEmail", app.getApplicantEmail() != null ? app.getApplicantEmail().toLowerCase().trim() : "");
                data.put("applicantRole", app.getApplicantRole() != null ? app.getApplicantRole() : "EMPLOYEE");
                data.put("applicantDepartment", app.getApplicantDepartment() != null ? app.getApplicantDepartment() : "");
                data.put("experienceYears", app.getExperienceYears() != null ? app.getExperienceYears() : "");
                data.put("coverNote", app.getCoverNote() != null ? app.getCoverNote() : "");
                data.put("status", app.getStatus() != null ? app.getStatus() : "APPLIED");
                data.put("stage", app.getStatus() != null ? app.getStatus() : "APPLIED");
                data.put("createdAt", FieldValue.serverTimestamp());

                docRef.set(data).get();
                System.out.println("✅ [FirebaseDAO] Job Application submitted: " + appId);
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error applying for job: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<List<JobApplication>> getApplicationsForJob(String jobId) {
        return CompletableFuture.supplyAsync(() -> {
            List<JobApplication> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return list;

                QuerySnapshot snap = db.collection(JOB_APPLICATIONS_COLLECTION).get().get();
                for (QueryDocumentSnapshot doc : snap.getDocuments()) {
                    String jId = doc.getString("jobId");
                    if (jobId != null && !jobId.isBlank() && !jobId.equalsIgnoreCase(jId)) {
                        continue;
                    }
                    JobApplication ja = new JobApplication();
                    ja.setApplicationId(doc.getString("applicationId") != null ? doc.getString("applicationId") : doc.getId());
                    ja.setJobId(jId != null ? jId : "");
                    ja.setJobTitle(doc.getString("jobTitle") != null ? doc.getString("jobTitle") : "Position");
                    ja.setApplicantName(doc.getString("applicantName") != null ? doc.getString("applicantName") : "Applicant");
                    ja.setApplicantEmail(doc.getString("applicantEmail") != null ? doc.getString("applicantEmail") : "");
                    ja.setApplicantRole(doc.getString("applicantRole") != null ? doc.getString("applicantRole") : "EMPLOYEE");
                    ja.setApplicantDepartment(doc.getString("applicantDepartment") != null ? doc.getString("applicantDepartment") : "");
                    ja.setExperienceYears(doc.getString("experienceYears") != null ? doc.getString("experienceYears") : "");
                    ja.setCoverNote(doc.getString("coverNote") != null ? doc.getString("coverNote") : "");

                    String st = doc.getString("status");
                    if (st == null || st.isBlank()) st = doc.getString("stage");
                    if (st == null || st.isBlank()) st = "APPLIED";
                    ja.setStatus(st);
                    ja.setAppliedAt(doc.get("createdAt") != null ? doc.get("createdAt").toString() : "");

                    list.add(ja);
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching applications: " + e.getMessage());
            }
            return list;
        });
    }

    public CompletableFuture<Boolean> updateApplicationStatus(String applicationId, String newStatus) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || applicationId == null) return false;

                db.collection(JOB_APPLICATIONS_COLLECTION).document(applicationId)
                  .update("status", newStatus, "stage", newStatus, "updatedAt", FieldValue.serverTimestamp())
                  .get();
                System.out.println("✅ [FirebaseDAO] Application status updated: " + applicationId + " -> " + newStatus);
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error updating application status: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<List<String>> getAppliedJobIdsForUserAsync(String email) {
        return CompletableFuture.supplyAsync(() -> {
            List<String> jobIds = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || email == null) return jobIds;

                QuerySnapshot snap = db.collection(JOB_APPLICATIONS_COLLECTION).get().get();
                String targetEmail = email.trim().toLowerCase();
                for (QueryDocumentSnapshot doc : snap.getDocuments()) {
                    String appEmail = doc.getString("applicantEmail");
                    if (targetEmail.equalsIgnoreCase(appEmail != null ? appEmail.trim().toLowerCase() : "")) {
                        String jId = doc.getString("jobId");
                        if (jId != null && !jId.isBlank()) {
                            jobIds.add(jId);
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching applied job IDs: " + e.getMessage());
            }
            return jobIds;
        });
    }

    public CompletableFuture<List<JobApplication>> getMyApplications(String userEmail) {
        return CompletableFuture.supplyAsync(() -> {
            List<JobApplication> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || userEmail == null) return list;

                String targetEmail = userEmail.trim().toLowerCase();
                QuerySnapshot snap = db.collection(JOB_APPLICATIONS_COLLECTION).get().get();

                for (QueryDocumentSnapshot doc : snap.getDocuments()) {
                    String appEmail = doc.getString("applicantEmail");
                    if (targetEmail.equalsIgnoreCase(appEmail != null ? appEmail.trim().toLowerCase() : "")) {
                        JobApplication ja = new JobApplication();
                        ja.setApplicationId(doc.getString("applicationId") != null ? doc.getString("applicationId") : doc.getId());
                        ja.setJobId(doc.getString("jobId") != null ? doc.getString("jobId") : "");
                        ja.setJobTitle(doc.getString("jobTitle") != null ? doc.getString("jobTitle") : "Position");
                        ja.setApplicantName(doc.getString("applicantName") != null ? doc.getString("applicantName") : "");
                        ja.setApplicantEmail(appEmail);
                        ja.setApplicantRole(doc.getString("applicantRole") != null ? doc.getString("applicantRole") : "EMPLOYEE");
                        ja.setApplicantDepartment(doc.getString("applicantDepartment") != null ? doc.getString("applicantDepartment") : "");
                        ja.setExperienceYears(doc.getString("experienceYears") != null ? doc.getString("experienceYears") : "");
                        ja.setCoverNote(doc.getString("coverNote") != null ? doc.getString("coverNote") : "");

                        String st = doc.getString("status");
                        if (st == null || st.isBlank()) st = doc.getString("stage");
                        if (st == null || st.isBlank()) st = "APPLIED";
                        ja.setStatus(st);

                        Object ca = doc.get("createdAt");
                        ja.setAppliedAt(ca != null ? ca.toString() : "");

                        list.add(ja);
                    }
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching my applications: " + e.getMessage());
            }
            return list;
        });
    }

    public CompletableFuture<Boolean> createTrainingCourseAsync(Map<String, Object> courseData) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || courseData == null) return false;

                courseData.put("createdAt", FieldValue.serverTimestamp());
                DocumentReference docRef = db.collection(TRAINING_COURSES_COLLECTION).document();
                courseData.put("id", docRef.getId());
                docRef.set(courseData).get();
                System.out.println("✅ [FirebaseDAO] Training course created in Firestore: " + docRef.getId());
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error creating training course: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<List<Map<String, Object>>> getAllTrainingCoursesAsync() {
        return CompletableFuture.supplyAsync(() -> {
            List<Map<String, Object>> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return list;

                QuerySnapshot snap = db.collection(TRAINING_COURSES_COLLECTION).get().get();
                for (QueryDocumentSnapshot doc : snap.getDocuments()) {
                    Map<String, Object> data = doc.getData();
                    data.put("id", doc.getId());
                    list.add(data);
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching training courses: " + e.getMessage());
            }
            return list;
        });
    }

    public CompletableFuture<List<Map<String, Object>>> getAllPerformanceReviewsAsync() {
        return CompletableFuture.supplyAsync(() -> {
            List<Map<String, Object>> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return list;

                QuerySnapshot snap = db.collection(PERFORMANCE_REVIEWS_COLLECTION).get().get();
                for (QueryDocumentSnapshot doc : snap.getDocuments()) {
                    Map<String, Object> data = doc.getData();
                    data.put("id", doc.getId());
                    list.add(data);
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching performance reviews: " + e.getMessage());
            }
            return list;
        });
    }

    public CompletableFuture<Boolean> addPerformanceReviewAsync(Map<String, Object> reviewData) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || reviewData == null) return false;

                reviewData.put("createdAt", FieldValue.serverTimestamp());
                DocumentReference docRef = db.collection(PERFORMANCE_REVIEWS_COLLECTION).document();
                reviewData.put("id", docRef.getId());
                docRef.set(reviewData).get();
                System.out.println("✅ [FirebaseDAO] Performance review added to Firestore: " + docRef.getId());
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error adding performance review: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<Map<String, Object>> getHRReportStatsAsync() {
        return CompletableFuture.supplyAsync(() -> {
            Map<String, Object> stats = new HashMap<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return stats;

                QuerySnapshot usersSnap = db.collection(USERS_COLLECTION).get().get();
                stats.put("totalHeadcount", usersSnap.size());

                QuerySnapshot jobsSnap = db.collection(JOB_POSTINGS_COLLECTION).get().get();
                int openJobs = 0;
                for (DocumentSnapshot doc : jobsSnap.getDocuments()) {
                    String st = doc.getString("status");
                    if (st == null || "OPEN".equalsIgnoreCase(st)) {
                        openJobs++;
                    }
                }
                stats.put("openJobsCount", openJobs);

                QuerySnapshot trainSnap = db.collection(TRAINING_COURSES_COLLECTION).get().get();
                double totalProgress = 0;
                int trainCount = 0;
                for (DocumentSnapshot doc : trainSnap.getDocuments()) {
                    Double p = doc.getDouble("completionRate");
                    if (p != null) {
                        totalProgress += p;
                        trainCount++;
                    }
                }
                double avgTrainPct = trainCount > 0 ? (totalProgress / trainCount) * 100.0 : 89.0;
                stats.put("trainingCompletionRate", (int) Math.round(avgTrainPct));

                QuerySnapshot appSnap = db.collection(JOB_APPLICATIONS_COLLECTION).get().get();
                stats.put("totalApplicants", appSnap.size());

            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error calculating HR report stats: " + e.getMessage());
            }
            return stats;
        });
    }



    public CompletableFuture<List<User>> getAllTrainersAsync() {
        return CompletableFuture.supplyAsync(() -> {
            List<User> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return list;

                QuerySnapshot snap = db.collection(USERS_COLLECTION).get().get();
                for (QueryDocumentSnapshot doc : snap.getDocuments()) {
                    String r = doc.getString("role");
                    if ("TRAINER".equalsIgnoreCase(r)) {
                        User u = new User();
                        u.setEmail(doc.getString("email") != null ? doc.getString("email") : doc.getId());
                        u.setFullName(doc.getString("fullName") != null ? doc.getString("fullName") : "Trainer");
                        u.setDepartment(doc.getString("department") != null ? doc.getString("department") : "L&D");
                        list.add(u);
                    }
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching trainers: " + e.getMessage());
            }
            return list;
        });
    }

    public CompletableFuture<Boolean> createTrainingProgram(TrainingProgram program) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || program == null) return false;

                DocumentReference docRef = db.collection(TRAINING_PROGRAMS_COLLECTION).document();
                String pId = docRef.getId();
                program.setProgramId(pId);

                Map<String, Object> data = new HashMap<>();
                data.put("programId", pId);
                data.put("id", pId);
                data.put("title", program.getTitle());
                data.put("description", program.getDescription() != null ? program.getDescription() : "");
                data.put("assignedTrainerEmail", program.getAssignedTrainerEmail() != null ? program.getAssignedTrainerEmail().toLowerCase().trim() : "");
                data.put("assignedTrainerName", program.getAssignedTrainerName() != null ? program.getAssignedTrainerName() : "Trainer");
                data.put("department", program.getDepartment() != null ? program.getDepartment() : "General");
                data.put("startDate", program.getStartDate() != null ? program.getStartDate() : "");
                data.put("endDate", program.getEndDate() != null ? program.getEndDate() : "");
                data.put("durationWeeks", program.getDurationWeeks() != null ? program.getDurationWeeks() : "4");
                data.put("status", program.getStatus() != null ? program.getStatus().toUpperCase() : "ACTIVE");
                data.put("createdAt", FieldValue.serverTimestamp());

                docRef.set(data).get();
                System.out.println("✅ [FirebaseDAO] Training Program created: " + pId);
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error creating training program: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<List<TrainingProgram>> getAllTrainingPrograms() {
        return CompletableFuture.supplyAsync(() -> {
            List<TrainingProgram> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return list;

                QuerySnapshot snap = db.collection(TRAINING_PROGRAMS_COLLECTION).get().get();
                for (QueryDocumentSnapshot doc : snap.getDocuments()) {
                    TrainingProgram tp = new TrainingProgram();
                    tp.setProgramId(doc.getString("programId") != null ? doc.getString("programId") : doc.getId());
                    tp.setTitle(doc.getString("title") != null ? doc.getString("title") : "Training Program");
                    tp.setDescription(doc.getString("description") != null ? doc.getString("description") : "");
                    tp.setAssignedTrainerEmail(doc.getString("assignedTrainerEmail") != null ? doc.getString("assignedTrainerEmail") : "");
                    tp.setAssignedTrainerName(doc.getString("assignedTrainerName") != null ? doc.getString("assignedTrainerName") : "Trainer");
                    tp.setDepartment(doc.getString("department") != null ? doc.getString("department") : "General");
                    tp.setStartDate(doc.getString("startDate") != null ? doc.getString("startDate") : "");
                    tp.setEndDate(doc.getString("endDate") != null ? doc.getString("endDate") : "");
                    tp.setDurationWeeks(doc.getString("durationWeeks") != null ? doc.getString("durationWeeks") : "4");
                    tp.setStatus(doc.getString("status") != null ? doc.getString("status") : "ACTIVE");
                    list.add(tp);
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching training programs: " + e.getMessage());
            }
            return list;
        });
    }

    public CompletableFuture<List<TrainingProgram>> getTrainingsForTrainer(String trainerEmail) {
        return CompletableFuture.supplyAsync(() -> {
            List<TrainingProgram> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return list;

                String targetEmail = trainerEmail != null ? trainerEmail.trim().toLowerCase() : "";
                QuerySnapshot snap = db.collection(TRAINING_PROGRAMS_COLLECTION).get().get();
                for (QueryDocumentSnapshot doc : snap.getDocuments()) {
                    String tEmail = doc.getString("assignedTrainerEmail");
                    String normTEmail = tEmail != null ? tEmail.trim().toLowerCase() : "";

                    boolean isMatch = normTEmail.isEmpty() || "all".equalsIgnoreCase(normTEmail)
                            || targetEmail.isEmpty()
                            || targetEmail.equalsIgnoreCase(normTEmail)
                            || normTEmail.contains(targetEmail)
                            || targetEmail.contains(normTEmail);

                    if (isMatch) {
                        TrainingProgram tp = new TrainingProgram();
                        tp.setProgramId(doc.getString("programId") != null ? doc.getString("programId") : doc.getId());
                        tp.setTitle(doc.getString("title") != null ? doc.getString("title") : "Training Program");
                        tp.setDescription(doc.getString("description") != null ? doc.getString("description") : "");
                        tp.setAssignedTrainerEmail(normTEmail);
                        tp.setAssignedTrainerName(doc.getString("assignedTrainerName") != null ? doc.getString("assignedTrainerName") : "Trainer");
                        tp.setDepartment(doc.getString("department") != null ? doc.getString("department") : "General");
                        tp.setStartDate(doc.getString("startDate") != null ? doc.getString("startDate") : "");
                        tp.setEndDate(doc.getString("endDate") != null ? doc.getString("endDate") : "");
                        tp.setDurationWeeks(doc.getString("durationWeeks") != null ? doc.getString("durationWeeks") : "4");
                        tp.setStatus(doc.getString("status") != null ? doc.getString("status") : "ACTIVE");
                        list.add(tp);
                    }
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching trainer programs: " + e.getMessage());
            }
            return list;
        });
    }

    public CompletableFuture<Boolean> updateTrainingProgramStatusAsync(String programId, String newStatus) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || programId == null) return false;

                db.collection(TRAINING_PROGRAMS_COLLECTION).document(programId)
                  .update("status", newStatus.toUpperCase(), "updatedAt", FieldValue.serverTimestamp())
                  .get();
                System.out.println("✅ [FirebaseDAO] Training Program status updated: " + programId + " -> " + newStatus);
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error updating training program status: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<Boolean> enrollEmployee(TrainingEnrollment enrollment) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || enrollment == null) return false;

                DocumentReference docRef = db.collection(TRAINING_ENROLLMENTS_COLLECTION).document();
                String eId = docRef.getId();
                enrollment.setEnrollmentId(eId);

                Map<String, Object> data = new HashMap<>();
                data.put("enrollmentId", eId);
                data.put("id", eId);
                data.put("programId", enrollment.getProgramId());
                data.put("programTitle", enrollment.getProgramTitle());
                data.put("employeeEmail", enrollment.getEmployeeEmail() != null ? enrollment.getEmployeeEmail().toLowerCase().trim() : "");
                data.put("employeeName", enrollment.getEmployeeName());
                data.put("employeeDepartment", enrollment.getEmployeeDepartment());
                data.put("attendancePercentage", enrollment.getAttendancePercentage());
                data.put("assessmentScore", enrollment.getAssessmentScore());
                data.put("completionStatus", enrollment.getCompletionStatus() != null ? enrollment.getCompletionStatus() : "ENROLLED");
                data.put("completionDate", enrollment.getCompletionDate() != null ? enrollment.getCompletionDate() : "");
                data.put("enrolledAt", FieldValue.serverTimestamp());

                docRef.set(data).get();
                System.out.println("✅ [FirebaseDAO] Employee enrolled in training: " + eId);
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error enrolling employee: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<Boolean> updateTrainerGrading(String enrollmentId, double attendance, double assessmentScore, String status, String completionDate) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || enrollmentId == null) return false;

                Map<String, Object> updates = new HashMap<>();
                updates.put("attendancePercentage", attendance);
                updates.put("assessmentScore", assessmentScore);
                if (status != null) updates.put("completionStatus", status.toUpperCase());
                if (completionDate != null) updates.put("completionDate", completionDate);
                updates.put("updatedAt", FieldValue.serverTimestamp());

                db.collection(TRAINING_ENROLLMENTS_COLLECTION).document(enrollmentId)
                  .set(updates, SetOptions.merge())
                  .get();
                System.out.println("✅ [FirebaseDAO] Trainer grading updated for enrollment: " + enrollmentId);
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error updating trainer grading: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<List<TrainingEnrollment>> getEnrollmentsForProgram(String programId) {
        return CompletableFuture.supplyAsync(() -> {
            List<TrainingEnrollment> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || programId == null) return list;

                QuerySnapshot snap = db.collection(TRAINING_ENROLLMENTS_COLLECTION).get().get();
                for (QueryDocumentSnapshot doc : snap.getDocuments()) {
                    String pId = doc.getString("programId");
                    if (programId.equalsIgnoreCase(pId)) {
                        TrainingEnrollment te = new TrainingEnrollment();
                        te.setEnrollmentId(doc.getString("enrollmentId") != null ? doc.getString("enrollmentId") : doc.getId());
                        te.setProgramId(pId);
                        te.setProgramTitle(doc.getString("programTitle") != null ? doc.getString("programTitle") : "Training");
                        te.setEmployeeEmail(doc.getString("employeeEmail") != null ? doc.getString("employeeEmail") : "");
                        te.setEmployeeName(doc.getString("employeeName") != null ? doc.getString("employeeName") : "Employee");
                        te.setEmployeeDepartment(doc.getString("employeeDepartment") != null ? doc.getString("employeeDepartment") : "");

                        Double att = doc.getDouble("attendancePercentage");
                        te.setAttendancePercentage(att != null ? att : 0.0);

                        Double sc = doc.getDouble("assessmentScore");
                        te.setAssessmentScore(sc != null ? sc : 0.0);

                        te.setCompletionStatus(doc.getString("completionStatus") != null ? doc.getString("completionStatus") : "ENROLLED");
                        te.setCompletionDate(doc.getString("completionDate") != null ? doc.getString("completionDate") : "");
                        list.add(te);
                    }
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching program enrollments: " + e.getMessage());
            }
            return list;
        });
    }

    public CompletableFuture<List<TrainingEnrollment>> getEnrollmentsForTrainerCourses(String trainerEmail) {
        return CompletableFuture.supplyAsync(() -> {
            List<TrainingEnrollment> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return list;

                List<TrainingProgram> programs = getTrainingsForTrainer(trainerEmail).get();
                Set<String> programIds = new HashSet<>();
                for (TrainingProgram p : programs) {
                    if (p.getProgramId() != null) programIds.add(p.getProgramId().toLowerCase());
                }

                QuerySnapshot snap = db.collection(TRAINING_ENROLLMENTS_COLLECTION).get().get();
                for (QueryDocumentSnapshot doc : snap.getDocuments()) {
                    String pId = doc.getString("programId");
                    boolean match = programIds.isEmpty() || (pId != null && programIds.contains(pId.toLowerCase()));
                    if (match) {
                        TrainingEnrollment te = new TrainingEnrollment();
                        te.setEnrollmentId(doc.getString("enrollmentId") != null ? doc.getString("enrollmentId") : doc.getId());
                        te.setProgramId(pId);
                        te.setProgramTitle(doc.getString("programTitle") != null ? doc.getString("programTitle") : "Training");
                        te.setEmployeeEmail(doc.getString("employeeEmail") != null ? doc.getString("employeeEmail") : "");
                        te.setEmployeeName(doc.getString("employeeName") != null ? doc.getString("employeeName") : "Employee");
                        te.setEmployeeDepartment(doc.getString("employeeDepartment") != null ? doc.getString("employeeDepartment") : "");

                        Double att = doc.getDouble("attendancePercentage");
                        te.setAttendancePercentage(att != null ? att : 0.0);

                        Double sc = doc.getDouble("assessmentScore");
                        te.setAssessmentScore(sc != null ? sc : 0.0);

                        te.setCompletionStatus(doc.getString("completionStatus") != null ? doc.getString("completionStatus") : "ENROLLED");
                        te.setCompletionDate(doc.getString("completionDate") != null ? doc.getString("completionDate") : "");
                        list.add(te);
                    }
                }
                if (list.isEmpty() && !snap.getDocuments().isEmpty()) {
                    for (QueryDocumentSnapshot doc : snap.getDocuments()) {
                        TrainingEnrollment te = new TrainingEnrollment();
                        te.setEnrollmentId(doc.getString("enrollmentId") != null ? doc.getString("enrollmentId") : doc.getId());
                        te.setProgramId(doc.getString("programId"));
                        te.setProgramTitle(doc.getString("programTitle") != null ? doc.getString("programTitle") : "Training");
                        te.setEmployeeEmail(doc.getString("employeeEmail") != null ? doc.getString("employeeEmail") : "");
                        te.setEmployeeName(doc.getString("employeeName") != null ? doc.getString("employeeName") : "Employee");
                        te.setEmployeeDepartment(doc.getString("employeeDepartment") != null ? doc.getString("employeeDepartment") : "");

                        Double att = doc.getDouble("attendancePercentage");
                        te.setAttendancePercentage(att != null ? att : 0.0);

                        Double sc = doc.getDouble("assessmentScore");
                        te.setAssessmentScore(sc != null ? sc : 0.0);

                        te.setCompletionStatus(doc.getString("completionStatus") != null ? doc.getString("completionStatus") : "ENROLLED");
                        te.setCompletionDate(doc.getString("completionDate") != null ? doc.getString("completionDate") : "");
                        list.add(te);
                    }
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching enrollments for trainer: " + e.getMessage());
            }
            return list;
        });
    }

    public CompletableFuture<List<Map<String, String>>> getUpcomingSessions(String trainerEmail) {
        return CompletableFuture.supplyAsync(() -> {
            List<Map<String, String>> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return list;

                QuerySnapshot snap = db.collection("seminars").get().get();
                for (QueryDocumentSnapshot doc : snap.getDocuments()) {
                    Map<String, String> session = new HashMap<>();
                    session.put("id", doc.getId());
                    session.put("title", doc.getString("title") != null ? doc.getString("title") : "Live Technical Workshop");
                    session.put("date", doc.getString("date") != null ? doc.getString("date") : "2026-09-01");
                    session.put("time", doc.getString("time") != null ? doc.getString("time") : "10:00 AM");
                    session.put("location", doc.getString("location") != null ? doc.getString("location") : "Virtual Room A");
                    list.add(session);
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching upcoming sessions: " + e.getMessage());
            }
            return list;
        });
    }

    public CompletableFuture<List<TrainingEnrollment>> getEmployeeTrainingProgress(String employeeEmail) {
        return CompletableFuture.supplyAsync(() -> {
            List<TrainingEnrollment> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || employeeEmail == null) return list;

                String targetEmail = employeeEmail.trim().toLowerCase();
                QuerySnapshot snap = db.collection(TRAINING_ENROLLMENTS_COLLECTION).get().get();
                for (QueryDocumentSnapshot doc : snap.getDocuments()) {
                    String eEmail = doc.getString("employeeEmail");
                    if (targetEmail.equalsIgnoreCase(eEmail != null ? eEmail.trim().toLowerCase() : "")) {
                        TrainingEnrollment te = new TrainingEnrollment();
                        te.setEnrollmentId(doc.getString("enrollmentId") != null ? doc.getString("enrollmentId") : doc.getId());
                        te.setProgramId(doc.getString("programId") != null ? doc.getString("programId") : "");
                        te.setProgramTitle(doc.getString("programTitle") != null ? doc.getString("programTitle") : "Training");
                        te.setEmployeeEmail(targetEmail);
                        te.setEmployeeName(doc.getString("employeeName") != null ? doc.getString("employeeName") : "");
                        te.setEmployeeDepartment(doc.getString("employeeDepartment") != null ? doc.getString("employeeDepartment") : "");
                        
                        Double att = doc.getDouble("attendancePercentage");
                        te.setAttendancePercentage(att != null ? att : 0.0);
                        
                        Double sc = doc.getDouble("assessmentScore");
                        te.setAssessmentScore(sc != null ? sc : 0.0);
                        
                        te.setCompletionStatus(doc.getString("completionStatus") != null ? doc.getString("completionStatus") : "ENROLLED");
                        te.setCompletionDate(doc.getString("completionDate") != null ? doc.getString("completionDate") : "");
                        list.add(te);
                    }
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching employee training progress: " + e.getMessage());
            }
            return list;
        });
    }

    public CompletableFuture<List<TrainingEnrollment>> getAllTrainingMetricsForHR() {
        return CompletableFuture.supplyAsync(() -> {
            List<TrainingEnrollment> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return list;

                QuerySnapshot snap = db.collection(TRAINING_ENROLLMENTS_COLLECTION).get().get();
                for (QueryDocumentSnapshot doc : snap.getDocuments()) {
                    TrainingEnrollment te = new TrainingEnrollment();
                    te.setEnrollmentId(doc.getString("enrollmentId") != null ? doc.getString("enrollmentId") : doc.getId());
                    te.setProgramId(doc.getString("programId") != null ? doc.getString("programId") : "");
                    te.setProgramTitle(doc.getString("programTitle") != null ? doc.getString("programTitle") : "Training");
                    te.setEmployeeEmail(doc.getString("employeeEmail") != null ? doc.getString("employeeEmail") : "");
                    te.setEmployeeName(doc.getString("employeeName") != null ? doc.getString("employeeName") : "Employee");
                    te.setEmployeeDepartment(doc.getString("employeeDepartment") != null ? doc.getString("employeeDepartment") : "General");
                    
                    Double att = doc.getDouble("attendancePercentage");
                    te.setAttendancePercentage(att != null ? att : 0.0);
                    
                    Double sc = doc.getDouble("assessmentScore");
                    te.setAssessmentScore(sc != null ? sc : 0.0);
                    
                    te.setCompletionStatus(doc.getString("completionStatus") != null ? doc.getString("completionStatus") : "ENROLLED");
                    te.setCompletionDate(doc.getString("completionDate") != null ? doc.getString("completionDate") : "");
                    list.add(te);
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching all training metrics for HR: " + e.getMessage());
            }
            return list;
        });
    }




    public CompletableFuture<Boolean> createSeminarEvent(SeminarEvent event) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || event == null) return false;

                DocumentReference docRef = db.collection(SEMINAR_EVENTS_COLLECTION).document();
                String id = (event.getEventId() != null && !event.getEventId().trim().isEmpty()) ? event.getEventId().trim() : docRef.getId();
                docRef = db.collection(SEMINAR_EVENTS_COLLECTION).document(id);
                event.setEventId(id);

                Map<String, Object> data = new HashMap<>();
                data.put("eventId", id);
                data.put("title", event.getTitle() != null ? event.getTitle() : "");
                data.put("topic", event.getTopic() != null ? event.getTopic() : "");
                data.put("trainerEmail", event.getTrainerEmail() != null ? event.getTrainerEmail().toLowerCase().trim() : "");
                data.put("trainerName", event.getTrainerName() != null ? event.getTrainerName() : "");
                data.put("eventDate", event.getEventDate() != null ? event.getEventDate() : "");
                data.put("eventTime", event.getEventTime() != null ? event.getEventTime() : "");
                data.put("meetingLinkOrVenue", event.getMeetingLinkOrVenue() != null ? event.getMeetingLinkOrVenue() : "");
                data.put("targetDepartment", event.getTargetDepartment() != null ? event.getTargetDepartment() : "ALL");
                data.put("createdAt", FieldValue.serverTimestamp());

                docRef.set(data).get();
                System.out.println("✅ [FirebaseDAO] Seminar event created in Firestore: " + id);
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error creating seminar event: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<List<SeminarEvent>> getAllSeminarEvents() {
        return CompletableFuture.supplyAsync(() -> {
            List<SeminarEvent> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return list;

                QuerySnapshot snap = db.collection(SEMINAR_EVENTS_COLLECTION).get().get();
                for (QueryDocumentSnapshot doc : snap.getDocuments()) {
                    SeminarEvent se = new SeminarEvent();
                    se.setEventId(doc.getString("eventId") != null ? doc.getString("eventId") : doc.getId());
                    se.setTitle(doc.getString("title") != null ? doc.getString("title") : "");
                    se.setTopic(doc.getString("topic") != null ? doc.getString("topic") : "");
                    se.setTrainerEmail(doc.getString("trainerEmail") != null ? doc.getString("trainerEmail") : "");
                    se.setTrainerName(doc.getString("trainerName") != null ? doc.getString("trainerName") : "");
                    se.setEventDate(doc.getString("eventDate") != null ? doc.getString("eventDate") : "");
                    se.setEventTime(doc.getString("eventTime") != null ? doc.getString("eventTime") : "");
                    se.setMeetingLinkOrVenue(doc.getString("meetingLinkOrVenue") != null ? doc.getString("meetingLinkOrVenue") : "");
                    se.setTargetDepartment(doc.getString("targetDepartment") != null ? doc.getString("targetDepartment") : "ALL");
                    se.setCreatedAt(doc.getDate("createdAt"));
                    list.add(se);
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching all seminar events: " + e.getMessage());
            }
            return list;
        });
    }


    public CompletableFuture<Boolean> createAssessment(TrainerAssessment assessment) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || assessment == null) return false;

                DocumentReference docRef = db.collection(TRAINER_ASSESSMENTS_COLLECTION).document();
                String id = (assessment.getAssessmentId() != null && !assessment.getAssessmentId().trim().isEmpty()) ? assessment.getAssessmentId().trim() : docRef.getId();
                docRef = db.collection(TRAINER_ASSESSMENTS_COLLECTION).document(id);
                assessment.setAssessmentId(id);

                Map<String, Object> data = new HashMap<>();
                data.put("assessmentId", id);
                data.put("title", assessment.getTitle() != null ? assessment.getTitle() : "");
                data.put("courseName", assessment.getCourseName() != null ? assessment.getCourseName() : "");
                data.put("trainerEmail", assessment.getTrainerEmail() != null ? assessment.getTrainerEmail().toLowerCase().trim() : "");
                data.put("durationMinutes", assessment.getDurationMinutes() != null ? assessment.getDurationMinutes() : "");
                data.put("totalMarks", assessment.getTotalMarks());
                data.put("targetDepartment", assessment.getTargetDepartment() != null ? assessment.getTargetDepartment() : "ALL");
                data.put("deadlineDate", assessment.getDeadlineDate() != null ? assessment.getDeadlineDate() : "");
                data.put("createdAt", FieldValue.serverTimestamp());

                docRef.set(data).get();
                System.out.println("✅ [FirebaseDAO] Trainer assessment created in Firestore: " + id);
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error creating assessment: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<List<TrainerAssessment>> getAssessmentsForEmployee(String department) {
        return CompletableFuture.supplyAsync(() -> {
            List<TrainerAssessment> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return list;

                QuerySnapshot snap = db.collection(TRAINER_ASSESSMENTS_COLLECTION).get().get();
                for (QueryDocumentSnapshot doc : snap.getDocuments()) {
                    String targetDept = doc.getString("targetDepartment");
                    boolean matches = (department == null || department.trim().isEmpty() || "ALL".equalsIgnoreCase(department.trim()))
                            || (targetDept == null || targetDept.trim().isEmpty() || "ALL".equalsIgnoreCase(targetDept.trim()) || targetDept.trim().equalsIgnoreCase(department.trim()));
                    if (matches) {
                        TrainerAssessment ta = new TrainerAssessment();
                        ta.setAssessmentId(doc.getString("assessmentId") != null ? doc.getString("assessmentId") : doc.getId());
                        ta.setTitle(doc.getString("title") != null ? doc.getString("title") : "");
                        ta.setCourseName(doc.getString("courseName") != null ? doc.getString("courseName") : "");
                        ta.setTrainerEmail(doc.getString("trainerEmail") != null ? doc.getString("trainerEmail") : "");
                        ta.setDurationMinutes(doc.getString("durationMinutes") != null ? doc.getString("durationMinutes") : "");
                        Long tm = doc.getLong("totalMarks");
                        ta.setTotalMarks(tm != null ? tm.intValue() : 0);
                        ta.setTargetDepartment(targetDept != null ? targetDept : "ALL");
                        ta.setDeadlineDate(doc.getString("deadlineDate") != null ? doc.getString("deadlineDate") : "");
                        ta.setCreatedAt(doc.getDate("createdAt"));
                        list.add(ta);
                    }
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching assessments for employee: " + e.getMessage());
            }
            return list;
        });
    }


    public CompletableFuture<Boolean> createAnnouncement(TrainerAnnouncement ann) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || ann == null) return false;

                DocumentReference docRef = db.collection(TRAINER_ANNOUNCEMENTS_COLLECTION).document();
                String id = (ann.getAnnouncementId() != null && !ann.getAnnouncementId().trim().isEmpty()) ? ann.getAnnouncementId().trim() : docRef.getId();
                docRef = db.collection(TRAINER_ANNOUNCEMENTS_COLLECTION).document(id);
                ann.setAnnouncementId(id);

                Map<String, Object> data = new HashMap<>();
                data.put("announcementId", id);
                data.put("title", ann.getTitle() != null ? ann.getTitle() : "");
                data.put("message", ann.getMessage() != null ? ann.getMessage() : "");
                data.put("trainerName", ann.getTrainerName() != null ? ann.getTrainerName() : "");
                data.put("trainerEmail", ann.getTrainerEmail() != null ? ann.getTrainerEmail().toLowerCase().trim() : "");
                data.put("priority", ann.getPriority() != null ? ann.getPriority() : "NORMAL");
                data.put("targetAudience", ann.getTargetAudience() != null ? ann.getTargetAudience() : "ALL");
                data.put("createdAt", FieldValue.serverTimestamp());

                docRef.set(data).get();
                System.out.println("✅ [FirebaseDAO] Trainer announcement created in Firestore: " + id);
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error creating announcement: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<List<TrainerAnnouncement>> getRecentAnnouncements() {
        return CompletableFuture.supplyAsync(() -> {
            List<TrainerAnnouncement> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return list;

                QuerySnapshot snap = db.collection(TRAINER_ANNOUNCEMENTS_COLLECTION).get().get();
                for (QueryDocumentSnapshot doc : snap.getDocuments()) {
                    TrainerAnnouncement ta = new TrainerAnnouncement();
                    ta.setAnnouncementId(doc.getString("announcementId") != null ? doc.getString("announcementId") : doc.getId());
                    ta.setTitle(doc.getString("title") != null ? doc.getString("title") : "");
                    ta.setMessage(doc.getString("message") != null ? doc.getString("message") : "");
                    ta.setTrainerName(doc.getString("trainerName") != null ? doc.getString("trainerName") : "");
                    ta.setTrainerEmail(doc.getString("trainerEmail") != null ? doc.getString("trainerEmail") : "");
                    ta.setPriority(doc.getString("priority") != null ? doc.getString("priority") : "NORMAL");
                    ta.setTargetAudience(doc.getString("targetAudience") != null ? doc.getString("targetAudience") : "ALL");
                    ta.setCreatedAt(doc.getDate("createdAt"));
                    list.add(ta);
                }
                list.sort((a, b) -> {
                    if (a.getCreatedAt() == null && b.getCreatedAt() == null) return 0;
                    if (a.getCreatedAt() == null) return 1;
                    if (b.getCreatedAt() == null) return -1;
                    return b.getCreatedAt().compareTo(a.getCreatedAt());
                });
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching recent announcements: " + e.getMessage());
            }
            return list;
        });
    }


    public CompletableFuture<Boolean> assignLearnerToCourse(TrainingEnrollment enrollment) {
        return enrollEmployee(enrollment);
    }

    public CompletableFuture<List<TrainingEnrollment>> getEnrolledLearnersForTrainer(String trainerEmail) {
        return getEnrollmentsForTrainerCourses(trainerEmail);
    }

   

    public CompletableFuture<List<EmployeeTask>> getTasksForEmployee(String employeeEmail) {
        return CompletableFuture.supplyAsync(() -> {
            List<EmployeeTask> tasks = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || employeeEmail == null || employeeEmail.isBlank()) return tasks;

                QuerySnapshot snap = db.collection(EMPLOYEE_TASKS_COLLECTION)
                        .whereEqualTo("employeeEmail", employeeEmail.toLowerCase().trim())
                        .get().get();

                for (QueryDocumentSnapshot doc : snap.getDocuments()) {
                    EmployeeTask t = new EmployeeTask();
                    t.setTaskId(doc.getString("taskId") != null ? doc.getString("taskId") : doc.getId());
                    t.setEmployeeEmail(doc.getString("employeeEmail") != null ? doc.getString("employeeEmail") : "");
                    t.setTitle(doc.getString("title") != null ? doc.getString("title") : "");
                    t.setDescription(doc.getString("description") != null ? doc.getString("description") : "");
                    t.setPriority(doc.getString("priority") != null ? doc.getString("priority") : "NORMAL");
                    t.setDueDate(doc.getString("dueDate") != null ? doc.getString("dueDate") : "");
                    t.setStatus(doc.getString("status") != null ? doc.getString("status") : "PENDING");
                    t.setCreatedAt(doc.getDate("createdAt"));
                    tasks.add(t);
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching employee tasks: " + e.getMessage());
            }
            return tasks;
        });
    }

    public CompletableFuture<Boolean> updateTaskStatus(String taskId, String newStatus) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || taskId == null) return false;

                db.collection(EMPLOYEE_TASKS_COLLECTION).document(taskId)
                        .update("status", newStatus != null ? newStatus : "PENDING").get();
                System.out.println("✅ [FirebaseDAO] Updated task status: " + taskId + " -> " + newStatus);
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error updating task status: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<Boolean> createEmployeeTask(EmployeeTask task) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || task == null) return false;

                DocumentReference docRef = task.getTaskId() != null && !task.getTaskId().isBlank()
                        ? db.collection(EMPLOYEE_TASKS_COLLECTION).document(task.getTaskId())
                        : db.collection(EMPLOYEE_TASKS_COLLECTION).document();

                String id = docRef.getId();
                Map<String, Object> data = new HashMap<>();
                data.put("taskId", id);
                data.put("employeeEmail", task.getEmployeeEmail() != null ? task.getEmployeeEmail().toLowerCase().trim() : "");
                data.put("managerEmail", task.getManagerEmail() != null ? task.getManagerEmail().toLowerCase().trim() : "");
                data.put("title", task.getTitle() != null ? task.getTitle() : "");
                data.put("description", task.getDescription() != null ? task.getDescription() : "");
                data.put("metric", task.getMetric() != null ? task.getMetric() : "");
                data.put("priority", task.getPriority() != null ? task.getPriority() : "NORMAL");
                data.put("dueDate", task.getDueDate() != null ? task.getDueDate() : "");
                data.put("status", task.getStatus() != null ? task.getStatus() : "PENDING");
                data.put("createdAt", FieldValue.serverTimestamp());

                docRef.set(data).get();
                System.out.println("✅ [FirebaseDAO] Created employee task: " + id);
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error creating task: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<List<EmployeeTask>> getTasksAssignedByManager(String managerEmail) {
        return CompletableFuture.supplyAsync(() -> {
            List<EmployeeTask> tasks = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || managerEmail == null || managerEmail.isBlank()) return tasks;

                QuerySnapshot snap = db.collection(EMPLOYEE_TASKS_COLLECTION)
                        .whereEqualTo("managerEmail", managerEmail.toLowerCase().trim())
                        .get().get();

                for (QueryDocumentSnapshot doc : snap.getDocuments()) {
                    EmployeeTask t = new EmployeeTask();
                    t.setTaskId(doc.getString("taskId") != null ? doc.getString("taskId") : doc.getId());
                    t.setEmployeeEmail(doc.getString("employeeEmail") != null ? doc.getString("employeeEmail") : "");
                    t.setManagerEmail(doc.getString("managerEmail") != null ? doc.getString("managerEmail") : "");
                    t.setTitle(doc.getString("title") != null ? doc.getString("title") : "");
                    t.setDescription(doc.getString("description") != null ? doc.getString("description") : "");
                    t.setMetric(doc.getString("metric") != null ? doc.getString("metric") : "");
                    t.setPriority(doc.getString("priority") != null ? doc.getString("priority") : "NORMAL");
                    t.setDueDate(doc.getString("dueDate") != null ? doc.getString("dueDate") : "");
                    t.setStatus(doc.getString("status") != null ? doc.getString("status") : "PENDING");
                    t.setCreatedAt(doc.getDate("createdAt"));
                    tasks.add(t);
                }

                if (tasks.isEmpty()) {
                    return getTasksForEmployee(managerEmail).get();
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching tasks assigned by manager: " + e.getMessage());
            }
            return tasks;
        });
    }

    public CompletableFuture<Boolean> deleteEmployeeTask(String taskId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || taskId == null || taskId.isBlank()) return false;
                db.collection(EMPLOYEE_TASKS_COLLECTION).document(taskId.trim()).delete().get();
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error deleting task: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<List<EmployeeSkill>> getSkillsAndGaps(String employeeEmail) {
        return CompletableFuture.supplyAsync(() -> {
            List<EmployeeSkill> skills = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || employeeEmail == null || employeeEmail.isBlank()) return skills;

                QuerySnapshot snap = db.collection(EMPLOYEE_SKILLS_COLLECTION)
                        .whereEqualTo("employeeEmail", employeeEmail.toLowerCase().trim())
                        .get().get();

                for (QueryDocumentSnapshot doc : snap.getDocuments()) {
                    EmployeeSkill s = new EmployeeSkill();
                    s.setSkillId(doc.getString("skillId") != null ? doc.getString("skillId") : doc.getId());
                    s.setEmployeeEmail(doc.getString("employeeEmail") != null ? doc.getString("employeeEmail") : "");
                    s.setSkillName(doc.getString("skillName") != null ? doc.getString("skillName") : "");
                    s.setCurrentLevel(doc.getString("currentLevel") != null ? doc.getString("currentLevel") : "Beginner");
                    s.setProficiencyPercentage(doc.getLong("proficiencyPercentage") != null ? doc.getLong("proficiencyPercentage").intValue() : 50);
                    s.setRequiredLevel(doc.getString("requiredLevel") != null ? doc.getString("requiredLevel") : "Advanced");
                    s.setGapPercentage(doc.getLong("gapPercentage") != null ? doc.getLong("gapPercentage").intValue() : 25);
                    s.setRecommendedCourseId(doc.getString("recommendedCourseId") != null ? doc.getString("recommendedCourseId") : "");
                    s.setRecommendedCourseName(doc.getString("recommendedCourseName") != null ? doc.getString("recommendedCourseName") : "");
                    skills.add(s);
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching employee skills: " + e.getMessage());
            }
            return skills;
        });
    }

    public CompletableFuture<List<EmployeeFeedback>> getFeedbackForEmployee(String employeeEmail) {
        return CompletableFuture.supplyAsync(() -> {
            List<EmployeeFeedback> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || employeeEmail == null || employeeEmail.isBlank()) return list;

                QuerySnapshot snap = db.collection("employee_feedback")
                        .whereEqualTo("employeeEmail", employeeEmail.toLowerCase().trim())
                        .get().get();

                for (DocumentSnapshot doc : snap.getDocuments()) {
                    EmployeeFeedback fb = doc.toObject(EmployeeFeedback.class);
                    if (fb != null) {
                        fb.setFeedbackId(doc.getId());
                        list.add(fb);
                    } else {
                        EmployeeFeedback f = new EmployeeFeedback();
                        f.setFeedbackId(doc.getString("feedbackId") != null ? doc.getString("feedbackId") : doc.getId());
                        f.setEmployeeEmail(doc.getString("employeeEmail") != null ? doc.getString("employeeEmail") : "");
                        f.setReviewerName(doc.getString("reviewerName") != null ? doc.getString("reviewerName") : "Manager");
                        f.setReviewerRole(doc.getString("reviewerRole") != null ? doc.getString("reviewerRole") : "Manager");
                        f.setFeedbackText(doc.getString("feedbackText") != null ? doc.getString("feedbackText") : "");
                        f.setRating(doc.getLong("rating") != null ? doc.getLong("rating").intValue() : 5);
                        f.setQuarterOrDate(doc.getString("quarterOrDate") != null ? doc.getString("quarterOrDate") : "");
                        f.setCreatedAt(doc.getDate("createdAt"));
                        list.add(f);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        });
    }


    public CompletableFuture<Integer> getUserLeaveBalance(String employeeEmail) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || employeeEmail == null || employeeEmail.isBlank()) return 18;

                DocumentSnapshot doc = db.collection(USERS_COLLECTION).document(employeeEmail.trim().toLowerCase()).get().get();
                if (doc.exists()) {
                    Long lb = doc.getLong("leaveBalance");
                    if (lb != null) return lb.intValue();
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching leave balance from Firestore: " + e.getMessage());
            }
            return 18;
        });
    }

    public CompletableFuture<Boolean> applyForLeave(LeaveRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                if (request == null) return false;
                if (request.getLeaveId() == null || request.getLeaveId().isBlank()) {
                    request.setLeaveId("leave_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 6));
                }
                if (request.getStatus() == null || request.getStatus().isBlank()) {
                    request.setStatus("PENDING");
                }
                if (request.getAppliedAt() == null || request.getAppliedAt().isBlank()) {
                    request.setAppliedAt(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
                }

                synchronized (memoryLeaveRequests) {
                    memoryLeaveRequests.removeIf(l -> l.getLeaveId().equals(request.getLeaveId()));
                    memoryLeaveRequests.add(0, request);
                }

                Firestore db = FirebaseConfig.getFirestore();
                if (db != null) {
                    db.collection(LEAVE_REQUESTS_COLLECTION).document(request.getLeaveId()).set(request).get();
                }
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error applying for leave: " + e.getMessage());
                return true; 
            }
        });
    }

    public CompletableFuture<List<LeaveRequest>> getLeaveRequestsForEmployee(String employeeEmail) {
        return CompletableFuture.supplyAsync(() -> {
            List<LeaveRequest> list = new ArrayList<>();
            try {
                String targetEmail = employeeEmail != null ? employeeEmail.trim().toLowerCase() : "";
                Firestore db = FirebaseConfig.getFirestore();
                if (db != null) {
                    QuerySnapshot snap = db.collection(LEAVE_REQUESTS_COLLECTION)
                            .whereEqualTo("employeeEmail", targetEmail)
                            .get().get();
                    for (DocumentSnapshot doc : snap.getDocuments()) {
                        LeaveRequest lr = doc.toObject(LeaveRequest.class);
                        if (lr != null) {
                            if (lr.getLeaveId() == null) lr.setLeaveId(doc.getId());
                            list.add(lr);
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching employee leave requests from Firestore: " + e.getMessage());
            }

            synchronized (memoryLeaveRequests) {
                if (employeeEmail != null && !employeeEmail.isBlank()) {
                    for (LeaveRequest lr : memoryLeaveRequests) {
                        if (employeeEmail.trim().equalsIgnoreCase(lr.getEmployeeEmail()) && list.stream().noneMatch(x -> x.getLeaveId().equals(lr.getLeaveId()))) {
                            list.add(lr);
                        }
                    }
                }
            }

            if (list.isEmpty()) {
                LeaveRequest sample = new LeaveRequest(
                    "leave_init_1",
                    employeeEmail != null && !employeeEmail.isBlank() ? employeeEmail : "employee@skillverse.com",
                    "Employee User",
                    "Casual Leave",
                    "2026-09-10",
                    "2026-09-12",
                    2,
                    "Family event and personal development",
                    "APPROVED",
                    "2026-09-01 10:00",
                    "manager@skillverse.com",
                    "Approved by Manager"
                );
                list.add(sample);
            }
            return list;
        });
    }

    public CompletableFuture<List<LeaveRequest>> getPendingLeaveRequestsForManager(String managerEmail) {
        return CompletableFuture.supplyAsync(() -> {
            List<LeaveRequest> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db != null) {
                    QuerySnapshot snap = db.collection(LEAVE_REQUESTS_COLLECTION)
                            .whereEqualTo("status", "PENDING")
                            .get().get();
                    for (DocumentSnapshot doc : snap.getDocuments()) {
                        LeaveRequest lr = doc.toObject(LeaveRequest.class);
                        if (lr != null) {
                            if (lr.getLeaveId() == null) lr.setLeaveId(doc.getId());
                            list.add(lr);
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching pending leaves from Firestore: " + e.getMessage());
            }

            synchronized (memoryLeaveRequests) {
                for (LeaveRequest lr : memoryLeaveRequests) {
                    if ("PENDING".equalsIgnoreCase(lr.getStatus()) && list.stream().noneMatch(x -> x.getLeaveId().equals(lr.getLeaveId()))) {
                        list.add(lr);
                    }
                }
            }
            return list;
        });
    }

    public CompletableFuture<Boolean> updateLeaveStatus(String leaveId, String status, String managerComment) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                if (leaveId == null || leaveId.isBlank()) return false;
                synchronized (memoryLeaveRequests) {
                    for (LeaveRequest lr : memoryLeaveRequests) {
                        if (leaveId.equals(lr.getLeaveId())) {
                            lr.setStatus(status);
                            if (managerComment != null) lr.setManagerComment(managerComment);
                        }
                    }
                }

                Firestore db = FirebaseConfig.getFirestore();
                if (db != null) {
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("status", status);
                    if (managerComment != null) updates.put("managerComment", managerComment);
                    db.collection(LEAVE_REQUESTS_COLLECTION).document(leaveId).update(updates).get();
                }
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error updating leave status: " + e.getMessage());
                return true;
            }
        });
    }

    public CompletableFuture<List<User>> getEmployeesForManager(String managerEmail) {
        return CompletableFuture.supplyAsync(() -> {
            List<User> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || managerEmail == null || managerEmail.isBlank()) return list;

                QuerySnapshot snap = db.collection(USERS_COLLECTION)
                        .whereEqualTo("managerEmail", managerEmail.toLowerCase().trim())
                        .get().get();

                for (DocumentSnapshot doc : snap.getDocuments()) {
                    User u = doc.toObject(User.class);
                    if (u != null) {
                        String role = u.getRole() != null ? u.getRole().trim().toUpperCase() : "";
                        if ("EMPLOYEE".equals(role) || "HR".equals(role)) {
                            if (u.getEmail() == null || u.getEmail().isBlank()) u.setEmail(doc.getId());
                            list.add(u);
                        }
                    }
                }

                if (list.isEmpty()) {
                    QuerySnapshot allSnap = db.collection(USERS_COLLECTION).get().get();
                    for (DocumentSnapshot doc : allSnap.getDocuments()) {
                        User u = doc.toObject(User.class);
                        if (u != null) {
                            String role = u.getRole() != null ? u.getRole().trim().toUpperCase() : "";
                            if ("EMPLOYEE".equals(role) || "HR".equals(role)) {
                                if (u.getEmail() == null || u.getEmail().isBlank()) u.setEmail(doc.getId());
                                list.add(u);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching employees for manager: " + e.getMessage());
            }
            return list;
        });
    }

    public CompletableFuture<List<User>> getAllEmployees() {
        return CompletableFuture.supplyAsync(() -> {
            List<User> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return list;
                QuerySnapshot snap = db.collection(USERS_COLLECTION).get().get();
                for (DocumentSnapshot doc : snap.getDocuments()) {
                    User u = doc.toObject(User.class);
                    if (u != null) {
                        if (u.getEmail() == null || u.getEmail().isBlank()) u.setEmail(doc.getId());
                        list.add(u);
                    }
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching all employees: " + e.getMessage());
            }
            return list;
        });
    }

    public CompletableFuture<List<TrainingEnrollment>> getEnrollmentsForEmployee(String employeeEmail) {
        return CompletableFuture.supplyAsync(() -> {
            List<TrainingEnrollment> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || employeeEmail == null || employeeEmail.isBlank()) return list;

                QuerySnapshot snap = db.collection("training_enrollments")
                        .whereEqualTo("employeeEmail", employeeEmail.toLowerCase().trim())
                        .get().get();

                for (DocumentSnapshot doc : snap.getDocuments()) {
                    TrainingEnrollment te = doc.toObject(TrainingEnrollment.class);
                    if (te != null) {
                        if (te.getEnrollmentId() == null || te.getEnrollmentId().isBlank()) {
                            te.setEnrollmentId(doc.getId());
                        }
                        list.add(te);
                    }
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching enrollments for employee: " + e.getMessage());
            }
            return list;
        });
    }

    public CompletableFuture<List<User>> getTeamMembers(String managerEmail) {
        return getEmployeesForManager(managerEmail);
    }

    public CompletableFuture<List<TrainingEnrollment>> getAllEnrollments() {
        return CompletableFuture.supplyAsync(() -> {
            List<TrainingEnrollment> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return list;

                QuerySnapshot snap = db.collection("training_enrollments").get().get();
                for (DocumentSnapshot doc : snap.getDocuments()) {
                    TrainingEnrollment te = doc.toObject(TrainingEnrollment.class);
                    if (te != null) {
                        if (te.getEnrollmentId() == null || te.getEnrollmentId().isBlank()) {
                            te.setEnrollmentId(doc.getId());
                        }
                        list.add(te);
                    }
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching all enrollments: " + e.getMessage());
            }
            return list;
        });
    }

    public CompletableFuture<Boolean> sendFeedback(EmployeeFeedback fb) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || fb == null) return false;

                DocumentReference docRef = db.collection("employee_feedback").document();
                String id = docRef.getId();

                Map<String, Object> data = new HashMap<>();
                data.put("feedbackId", id);
                data.put("employeeEmail", fb.getEmployeeEmail() != null ? fb.getEmployeeEmail().toLowerCase().trim() : "");
                data.put("reviewerEmail", fb.getReviewerEmail() != null ? fb.getReviewerEmail().toLowerCase().trim() : "");
                data.put("reviewerName", fb.getReviewerName() != null ? fb.getReviewerName() : "Manager");
                data.put("reviewerRole", fb.getReviewerRole() != null ? fb.getReviewerRole() : "Manager");
                data.put("categoryTag", fb.getCategoryTag() != null ? fb.getCategoryTag() : "PERFORMANCE");
                data.put("feedbackText", fb.getFeedbackText() != null ? fb.getFeedbackText() : "");
                data.put("rating", fb.getRating());
                data.put("acknowledged", fb.isAcknowledged());
                data.put("createdAt", FieldValue.serverTimestamp());

                docRef.set(data).get();
                System.out.println("✅ [FirebaseDAO] Sent feedback: " + id);
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error sending feedback: " + e.getMessage());
                return false;
            }
        });
    }

  
    public CompletableFuture<List<EmployeeFeedback>> getAllManagerFeedbacks() {
        return CompletableFuture.supplyAsync(() -> {
            List<EmployeeFeedback> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return list;
                QuerySnapshot snap = db.collection("employee_feedback")
                        .orderBy("createdAt", com.google.cloud.firestore.Query.Direction.DESCENDING)
                        .get().get();
                for (DocumentSnapshot doc : snap.getDocuments()) {
                    EmployeeFeedback fb = doc.toObject(EmployeeFeedback.class);
                    if (fb != null) {
                        if (fb.getFeedbackId() == null || fb.getFeedbackId().isBlank()) {
                            fb.setFeedbackId(doc.getId());
                        }
                        list.add(fb);
                    }
                }
                System.out.println("✅ [FirebaseDAO] Loaded " + list.size() + " feedback records for HR.");
            } catch (Exception e) {
                try {
                    Firestore db2 = FirebaseConfig.getFirestore();
                    if (db2 == null) return list;
                    QuerySnapshot snap2 = db2.collection("employee_feedback").get().get();
                    for (DocumentSnapshot doc : snap2.getDocuments()) {
                        EmployeeFeedback fb = doc.toObject(EmployeeFeedback.class);
                        if (fb != null) {
                            if (fb.getFeedbackId() == null || fb.getFeedbackId().isBlank()) {
                                fb.setFeedbackId(doc.getId());
                            }
                            list.add(fb);
                        }
                    }
                } catch (Exception ex) {
                    System.err.println("❌ [FirebaseDAO] Error fetching all feedbacks: " + ex.getMessage());
                }
            }
            return list;
        });
    }

    public CompletableFuture<List<EmployeeFeedback>> getFeedbackSentByManager(String managerEmail) {
        return CompletableFuture.supplyAsync(() -> {
            List<EmployeeFeedback> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || managerEmail == null || managerEmail.isBlank()) return list;

                QuerySnapshot snap = db.collection("employee_feedback")
                        .whereEqualTo("reviewerEmail", managerEmail.toLowerCase().trim())
                        .get().get();

                for (DocumentSnapshot doc : snap.getDocuments()) {
                    EmployeeFeedback fb = doc.toObject(EmployeeFeedback.class);
                    if (fb != null) {
                        if (fb.getFeedbackId() == null || fb.getFeedbackId().isBlank()) {
                            fb.setFeedbackId(doc.getId());
                        }
                        list.add(fb);
                    }
                }

                if (list.isEmpty()) {
                    QuerySnapshot allSnap = db.collection("employee_feedback").get().get();
                    for (DocumentSnapshot doc : allSnap.getDocuments()) {
                        EmployeeFeedback fb = doc.toObject(EmployeeFeedback.class);
                        if (fb != null) {
                            if (fb.getFeedbackId() == null || fb.getFeedbackId().isBlank()) {
                                fb.setFeedbackId(doc.getId());
                            }
                            list.add(fb);
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching manager feedback history: " + e.getMessage());
            }
            return list;
        });
    }

    public CompletableFuture<List<SystemApproval>> getPendingSystemApprovals() {
        return CompletableFuture.supplyAsync(() -> {
            List<SystemApproval> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return list;

                QuerySnapshot snap = db.collection("system_approvals")
                        .whereEqualTo("status", "PENDING")
                        .get().get();

                for (DocumentSnapshot doc : snap.getDocuments()) {
                    SystemApproval sa = doc.toObject(SystemApproval.class);
                    if (sa != null) {
                        if (sa.getApprovalId() == null || sa.getApprovalId().isBlank()) {
                            sa.setApprovalId(doc.getId());
                        }
                        list.add(sa);
                    }
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching pending approvals: " + e.getMessage());
            }
            return list;
        });
    }

    public CompletableFuture<Void> updateApprovalStatus(String approvalId, String status) {
        return CompletableFuture.runAsync(() -> {
            try {
                Map<String, Object> updates = new HashMap<>();
                updates.put("status", status);
                updates.put("reviewedAt", new Date());
                getFirestore().collection("system_approvals").document(approvalId).update(updates).get();
            } catch (Exception e) {
                System.err.println("[FirebaseDAO] Error updating approval: " + e.getMessage());
            }
        });
    }

    private Firestore getFirestore() {
        return FirebaseConfig.getFirestore();
    }

    public CompletableFuture<List<JobPosting>> getAllJobPostings() {
        return CompletableFuture.supplyAsync(() -> {
            List<JobPosting> list = new ArrayList<>();
            try {
                Firestore db = getFirestore();
                if (db == null) return list;
                ApiFuture<QuerySnapshot> future = db.collection("job_postings").get();
                List<QueryDocumentSnapshot> docs = future.get().getDocuments();
                for (QueryDocumentSnapshot doc : docs) {
                    try {
                        JobPosting jp = doc.toObject(JobPosting.class);
                        if (jp != null) {
                            jp.setId(doc.getId());
                            list.add(jp);
                        }
                    } catch (Exception e) {
                        JobPosting jp = new JobPosting();
                        jp.setId(doc.getId());
                        jp.setTitle(doc.getString("title"));
                        jp.setDepartment(doc.getString("department"));
                        jp.setLocation(doc.getString("location"));
                        jp.setJobType(doc.getString("jobType"));
                        jp.setExperience(doc.getString("experience"));
                        jp.setSalaryRange(doc.getString("salaryRange"));
                        jp.setStatus(doc.getString("status") != null ? doc.getString("status") : "ACTIVE");
                        
                        Long applicants = doc.getLong("applicantsCount");
                        jp.setApplicantsCount(applicants != null ? applicants : 0);

                        @SuppressWarnings("unchecked")
                        List<String> skills = (List<String>) doc.get("requiredSkills");
                        if (skills != null) jp.setRequiredSkills(skills);

                        jp.setCreatedAt(doc.get("createdAt"));
                        list.add(jp);
                    }
                }
            } catch (Exception e) {
                System.err.println("[FirebaseDAO] Error fetching job postings: " + e.getMessage());
            }
            return list;
        });
    }

    public CompletableFuture<Boolean> createDepartment(String deptName) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || deptName == null || deptName.isBlank()) return false;

                Map<String, Object> data = new HashMap<>();
                data.put("departmentName", deptName.trim());
                data.put("createdAt", FieldValue.serverTimestamp());

                db.collection("departments").document(deptName.trim().replaceAll("\\s+", "_")).set(data).get();
                System.out.println("✅ [FirebaseDAO] Created department: " + deptName);
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error creating department: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<List<MasterSkill>> getAllMasterSkills() {
        return CompletableFuture.supplyAsync(() -> {
            List<MasterSkill> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return list;
                QuerySnapshot snap = db.collection("master_skills").get().get();
                for (DocumentSnapshot doc : snap.getDocuments()) {
                    MasterSkill ms = doc.toObject(MasterSkill.class);
                    if (ms != null) {
                        if (ms.getSkillId() == null) ms.setSkillId(doc.getId());
                        list.add(ms);
                    }
                }
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error fetching master_skills: " + e.getMessage());
            }
            return list;
        });
    }

    public CompletableFuture<Boolean> addMasterSkill(MasterSkill skill) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || skill == null) return false;

                String docId = skill.getSkillName() != null ? skill.getSkillName().trim().toLowerCase().replaceAll("\\s+", "_") : UUID.randomUUID().toString();
                skill.setSkillId(docId);

                db.collection("master_skills").document(docId).set(skill).get();
                System.out.println("✅ [FirebaseDAO] Added master skill: " + skill.getSkillName());
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error adding master skill: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<Boolean> createMasterSkill(MasterSkill skill) {
        return addMasterSkill(skill);
    }

    public CompletableFuture<Boolean> updateJobStatus(String jobId, String newStatus) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || jobId == null) return false;
                db.collection("job_postings").document(jobId).update("status", newStatus).get();
                System.out.println("✅ [FirebaseDAO] Updated job status for " + jobId + " to " + newStatus);
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error updating job status: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<Boolean> deleteJobPosting(String jobId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || jobId == null) return false;
                db.collection("job_postings").document(jobId).delete().get();
                System.out.println("✅ [FirebaseDAO] Deleted job posting: " + jobId);
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error deleting job posting: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<Boolean> saveCandidateShortlist(String candidateEmail, String jobId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || candidateEmail == null || jobId == null) return false;

                Map<String, Object> shortlistData = new HashMap<>();
                shortlistData.put("candidateEmail", candidateEmail);
                shortlistData.put("jobId", jobId);
                shortlistData.put("shortlistedAt", new java.util.Date().toString());
                shortlistData.put("status", "SHORTLISTED");

                String docId = candidateEmail.replace("@", "_at_").replace(".", "_dot_") + "_" + jobId;
                db.collection("candidate_shortlists").document(docId).set(shortlistData).get();
                System.out.println("✅ [FirebaseDAO] Candidate " + candidateEmail + " shortlisted for job " + jobId);
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error saving candidate shortlist: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<List<SystemApproval>> getAllSystemApprovals() {
        return CompletableFuture.supplyAsync(() -> {
            List<SystemApproval> list = new ArrayList<>();
            try {
                ApiFuture<QuerySnapshot> future = getFirestore().collection("system_approvals").get();
                for (QueryDocumentSnapshot doc : future.get().getDocuments()) {
                    SystemApproval sa = doc.toObject(SystemApproval.class);
                    if (sa != null) {
                        sa.setId(doc.getId());
                        list.add(sa);
                    }
                }
            } catch (Exception e) {
                System.err.println("[FirebaseDAO] Error fetching approvals: " + e.getMessage());
            }
            return list;
        });
    }

    public CompletableFuture<Boolean> createSystemApproval(SystemApproval sa) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || sa == null) return false;
                String docId = sa.getId() != null ? sa.getId() : "appr_" + System.currentTimeMillis();
                sa.setId(docId);
                db.collection("system_approvals").document(docId).set(sa).get();
                System.out.println("✅ [FirebaseDAO] Created system approval doc: " + docId);
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error creating system approval: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<List<AppNotification>> getAllNotifications() {
        return CompletableFuture.supplyAsync(() -> {
            List<AppNotification> list = new ArrayList<>();
            try {
                ApiFuture<QuerySnapshot> future = getFirestore().collection("system_notifications").get();
                for (QueryDocumentSnapshot doc : future.get().getDocuments()) {
                    AppNotification n = doc.toObject(AppNotification.class);
                    if (n != null) {
                        n.setId(doc.getId());
                        list.add(n);
                    }
                }
            } catch (Exception e) {
                System.err.println("[FirebaseDAO] Error fetching notifications: " + e.getMessage());
            }
            return list;
        });
    }

    public CompletableFuture<Void> createNotification(AppNotification notif) {
        return CompletableFuture.runAsync(() -> {
            try {
                getFirestore().collection("system_notifications").document().set(notif).get();
                if (notif != null && notif.getTitle() != null) {
                    logActivity("Published broadcast: " + notif.getTitle(), "Notifications", "SUCCESS");
                }
            } catch (Exception e) {
                System.err.println("[FirebaseDAO] Error creating notification: " + e.getMessage());
                logActivity("Failed publishing broadcast: " + (notif != null ? notif.getTitle() : "Unknown"), "Notifications", "FAILED");
            }
        });
    }

    public CompletableFuture<Void> markNotificationAsRead(String notifId) {
        return CompletableFuture.runAsync(() -> {
            try {
                getFirestore().collection("system_notifications").document(notifId).update("read", true).get();
            } catch (Exception e) {
                System.err.println("[FirebaseDAO] Error updating notification: " + e.getMessage());
            }
        });
    }

    public CompletableFuture<Boolean> deleteNotification(String notificationId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || notificationId == null) return false;
                db.collection("system_notifications").document(notificationId).delete().get();
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error deleting notification: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<Void> markAllNotificationsAsRead() {
        return CompletableFuture.runAsync(() -> {
            try {
                ApiFuture<QuerySnapshot> future = getFirestore().collection("system_notifications").whereEqualTo("read", false).get();
                for (QueryDocumentSnapshot doc : future.get().getDocuments()) {
                    doc.getReference().update("read", true);
                }
            } catch (Exception e) {
                System.err.println("[FirebaseDAO] Error marking all read: " + e.getMessage());
            }
        });
    }

    public CompletableFuture<List<AuditLog>> getAllAuditLogs() {
        return CompletableFuture.supplyAsync(() -> {
            List<AuditLog> list = new ArrayList<>();
            try {
                ApiFuture<QuerySnapshot> future = getFirestore().collection("audit_logs").get();
                for (QueryDocumentSnapshot doc : future.get().getDocuments()) {
                    try {
                        AuditLog log = doc.toObject(AuditLog.class);
                        if (log != null) {
                            log.setId(doc.getId());
                            list.add(log);
                        }
                    } catch (Exception docEx) {
                        AuditLog log = new AuditLog();
                        log.setId(doc.getId());
                        log.setUserName(doc.getString("userName"));
                        log.setUserEmail(doc.getString("userEmail"));
                        log.setUserRole(doc.getString("userRole"));
                        log.setAction(doc.getString("action"));
                        log.setModule(doc.getString("module"));
                        log.setStatus(doc.getString("status") != null ? doc.getString("status") : "SUCCESS");
                        log.setTimestamp(doc.get("timestamp"));
                        list.add(log);
                    }
                }
            } catch (Exception e) {
                System.err.println("[FirebaseDAO] Error fetching audit logs: " + e.getMessage());
            }
            return list;
        });
    }

    public CompletableFuture<Void> createAuditLog(AuditLog log) {
        return CompletableFuture.runAsync(() -> {
            try {
                getFirestore().collection("audit_logs").document().set(log).get();
            } catch (Exception e) {
                System.err.println("[FirebaseDAO] Error creating audit log: " + e.getMessage());
            }
        });
    }

    public void logActivity(String action, String module, String status) {
        CompletableFuture.runAsync(() -> {
            try {
                User current = UserSession.getCurrentUser();
                String email = current != null ? current.getEmail() : "System";
                String name = current != null && current.getName() != null ? current.getName() : email;
                String role = current != null && current.getRole() != null ? current.getRole() : "ADMIN";

                Map<String, Object> logData = new HashMap<>();
                logData.put("userEmail", email);
                logData.put("userName", name);
                logData.put("userRole", role);
                logData.put("action", action);
                logData.put("module", module);
                logData.put("status", status);
                logData.put("timestamp", new Date().toString());

                getFirestore().collection("audit_logs").document().set(logData);
            } catch (Exception e) {
                System.err.println("[AuditLog] Error logging activity: " + e.getMessage());
            }
        });
    }

    public CompletableFuture<Void> createEmployee(User emp) {
        return CompletableFuture.runAsync(() -> {
            try {
                if (emp != null) {
                    String docId = emp.getEmail() != null ? emp.getEmail().toLowerCase().trim() : "emp_" + System.currentTimeMillis();
                    getFirestore().collection("users").document(docId).set(emp).get();
                    logActivity("Added new employee: " + emp.getName(), "Employees", "SUCCESS");
                }
            } catch (Exception e) {
                System.err.println("[FirebaseDAO] Error adding employee: " + e.getMessage());
                logActivity("Failed adding employee: " + (emp != null ? emp.getName() : "Unknown"), "Employees", "FAILED");
            }
        });
    }

    public CompletableFuture<Void> updateUserProfile(String email, String name, String department) {
        return CompletableFuture.runAsync(() -> {
            try {
                Map<String, Object> updates = new HashMap<>();
                updates.put("name", name);
                updates.put("department", department);
                getFirestore().collection("users").document(email.toLowerCase().trim()).update(updates).get();
            } catch (Exception e) {
                System.err.println("[FirebaseDAO] Error updating profile: " + e.getMessage());
            }
        });
    }

    public CompletableFuture<Void> updateUserProfilePicture(String email, String photoUrl) {
        return CompletableFuture.runAsync(() -> {
            try {
                if (email == null || email.trim().isEmpty()) return;
                String docId = email.toLowerCase().trim();

                Map<String, Object> updates = new HashMap<>();
                if (photoUrl == null || photoUrl.trim().isEmpty() || "null".equalsIgnoreCase(photoUrl.trim())) {
                    updates.put("profilePicUrl", FieldValue.delete());
                    updates.put("profileImageUrl", FieldValue.delete());
                } else {
                    updates.put("profilePicUrl", photoUrl.trim());
                    updates.put("profileImageUrl", photoUrl.trim());
                }

                getFirestore().collection("users").document(docId).update(updates).get();
                System.out.println("✅ [FirebaseDAO] Profile photo updated in Firestore for: " + docId);
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error updating profile picture: " + e.getMessage());
            }
        });
    }

    public CompletableFuture<Void> updateUserProfileFull(String email, String name, String phone, String department) {
        return CompletableFuture.runAsync(() -> {
            try {
                Map<String, Object> updates = new HashMap<>();
                updates.put("name", name);
                updates.put("phone", phone);
                updates.put("department", department);
                getFirestore().collection("users").document(email.toLowerCase().trim()).update(updates).get();
            } catch (Exception e) {
                System.err.println("[FirebaseDAO] Error updating full user profile: " + e.getMessage());
            }
        });
    }

    public CompletableFuture<List<com.skillverse.CommonFeatures.Feedback>> getAllFeedbacks() {
        return CompletableFuture.supplyAsync(() -> {
            List<com.skillverse.CommonFeatures.Feedback> list = new ArrayList<>();
            try {
                var snapshot = getFirestore().collection("feedbacks").get().get();
                for (var doc : snapshot.getDocuments()) {
                    com.skillverse.CommonFeatures.Feedback f = doc.toObject(com.skillverse.CommonFeatures.Feedback.class);
                    if (f != null) {
                        if (f.getFeedbackId() == null) f.setFeedbackId(doc.getId());
                        list.add(f);
                    }
                }
            } catch (Exception e) {
                System.err.println("[FirebaseDAO] Error loading feedbacks: " + e.getMessage());
            }
            return list;
        });
    }

    public CompletableFuture<List<com.skillverse.CommonFeatures.Feedback>> getAllFeedbackAsync() {
        return getAllFeedbacks();
    }

    public CompletableFuture<Void> submitFeedbackAsync(com.skillverse.CommonFeatures.Feedback feedback) {
        return CompletableFuture.runAsync(() -> {
            try {
                if (feedback != null) {
                    String docId = feedback.getFeedbackId() != null ? feedback.getFeedbackId() : "FB-" + System.currentTimeMillis();
                    feedback.setFeedbackId(docId);
                    if (feedback.getTimestamp() == null) feedback.setTimestamp(new java.util.Date().toString());
                    getFirestore().collection("feedbacks").document(docId).set(feedback).get();
                    System.out.println("✅ [FirebaseDAO] Feedback saved to Firestore: " + docId);
                }
            } catch (Exception e) {
                System.err.println("[FirebaseDAO] Error submitting feedback: " + e.getMessage());
            }
        });
    }


    public CompletableFuture<Boolean> saveAIChatMessage(AIChatMessage message) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || message == null) return false;

                String id = message.getMessageId() != null && !message.getMessageId().isBlank()
                        ? message.getMessageId()
                        : "ai_chat_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 5);
                message.setMessageId(id);

                Map<String, Object> data = new HashMap<>();
                data.put("messageId", id);
                data.put("employeeEmail", message.getEmployeeEmail() != null ? message.getEmployeeEmail().toLowerCase().trim() : "");
                data.put("sender", message.getSender() != null ? message.getSender() : "User");
                data.put("messageText", message.getMessageText() != null ? message.getMessageText() : "");
                data.put("category", message.getCategory() != null ? message.getCategory() : "GENERAL");
                data.put("timestamp", FieldValue.serverTimestamp());

                db.collection("ai_assistant_chats").document(id).set(data).get();
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error saving AI chat message: " + e.getMessage());
                return false;
            }
        });
    }

    public CompletableFuture<List<AIChatMessage>> getAIChatHistory(String employeeEmail) {
        return CompletableFuture.supplyAsync(() -> {
            List<AIChatMessage> list = new ArrayList<>();
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || employeeEmail == null || employeeEmail.isBlank()) return list;

                String target = employeeEmail.toLowerCase().trim();
                QuerySnapshot snap = db.collection("ai_assistant_chats")
                        .whereEqualTo("employeeEmail", target)
                        .get().get();

                for (DocumentSnapshot doc : snap.getDocuments()) {
                    AIChatMessage msg = new AIChatMessage();
                    msg.setMessageId(doc.getString("messageId") != null ? doc.getString("messageId") : doc.getId());
                    msg.setEmployeeEmail(doc.getString("employeeEmail"));
                    msg.setSender(doc.getString("sender"));
                    msg.setMessageText(doc.getString("messageText"));
                    msg.setCategory(doc.getString("category"));
                    msg.setTimestamp(doc.get("timestamp"));
                    list.add(msg);
                }

                list.sort((a, b) -> {
                    if (a.getMessageId() != null && b.getMessageId() != null) {
                        return a.getMessageId().compareTo(b.getMessageId());
                    }
                    return 0;
                });
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error loading AI chat history: " + e.getMessage());
            }
            return list;
        });
    }

    public CompletableFuture<Boolean> clearAIChatHistory(String employeeEmail) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null || employeeEmail == null || employeeEmail.isBlank()) return false;

                String target = employeeEmail.toLowerCase().trim();
                QuerySnapshot snap = db.collection("ai_assistant_chats")
                        .whereEqualTo("employeeEmail", target)
                        .get().get();

                for (DocumentSnapshot doc : snap.getDocuments()) {
                    doc.getReference().delete().get();
                }
                return true;
            } catch (Exception e) {
                System.err.println("❌ [FirebaseDAO] Error clearing AI chat history: " + e.getMessage());
                return false;
            }
        });
    }
}
