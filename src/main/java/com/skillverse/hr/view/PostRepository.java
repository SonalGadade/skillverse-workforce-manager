package com.skillverse.hr.view;

import java.util.ArrayList;
import java.util.List;

public class PostRepository {

    private static final PostRepository INSTANCE = new PostRepository();
    private static final List<Post> posts = new ArrayList<>();

    static {

        Post p1 = new Post(
                "Alice Johnson",
                "HR Manager • Hiring",
                "We are Hiring: Senior Java & Full-Stack Engineers! 🚀",
                "Hiring",
                "We are expanding our core product team at SkillVerse AI! We are looking for experienced Java developers proficient in Spring Boot, JavaFX, and Microservices architecture.\n\n📍 Location: Remote / Hybrid\n💼 Experience: 3+ Years\n\nApply directly or reach out to hr@skillverse.com!",
                null,
                "2 hours ago",
                42
        );
        p1.addComment("Alice Johnson: Great opportunity! Sharing with my engineering network.");
        p1.addComment("Rohan Kulkarni: Applied! Looking forward to hearing back.");

        Post p2 = new Post(
                "Aarav Sharma",
                "Software Manager • Engineering",
                "Proud of the team for completing Sprint 4 ahead of schedule! 👏",
                "Project Milestone",
                "Huge shoutout to the entire software engineering team for pushing Sprint 4 deliverables across the finish line with 100% test coverage and zero critical bugs!",
                null,
                "4 hours ago",
                28
        );
        p2.addComment("Alex Morgan: Kudos to the team for an outstanding release!");
        
        
        Post p3 = new Post(
            "Rohan Kulkarni",
            "Employee • Software Engineer",
            "Thrilled to share that I just cleared the AWS Certified Developer Exam! 🎓",
            "Certification",
            "After weeks of intense preparation and hands-on cloud labs, I passed the AWS Certified Developer Associate examination! Special thanks to SkillVerse AI for the training track.",
            null,
            "6 hours ago",
            35
        );
        p3.addComment("System Admin: Congratulations Rohan! Amazing milestone 🎉");

        posts.add(p1);
        posts.add(p2);
        posts.add(p3);
    }

    public static PostRepository getInstance() {
        return INSTANCE;
    }

    public static List<Post> getPosts() {
        return new ArrayList<>(posts);
    }

    public List<Post> getAllPosts() {
        return new ArrayList<>(posts);
    }

    public static void addPost(Post post) {
        if (post != null) {
            posts.add(0, post);
        }
    }

    public void createPost(Post post) {
 
       addPost(post);
    }
}