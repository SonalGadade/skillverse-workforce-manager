package com.skillverse.employee.service;

import com.skillverse.CommonFeatures.EmployeeSkill;
import com.skillverse.CommonFeatures.JobPosting;
import com.skillverse.CommonFeatures.TrainingEnrollment;
import com.skillverse.CommonFeatures.TrainingProgram;
import com.skillverse.CommonFeatures.User;

import java.util.*;
import java.util.stream.Collectors;

public class AILearningEngine {

    public static class EmployeeContext {

        public User employee;
        public String employeeEmail;
        public String fullName;
        public String department;
        public String role;
        public String experienceLevel;
        public String targetCareerGoal;
        public List<EmployeeSkill> employeeSkills = new ArrayList<>();
        public List<TrainingEnrollment> enrollments = new ArrayList<>();
        public List<TrainingProgram> availableCourses = new ArrayList<>();
        public List<User> availableMentors = new ArrayList<>();
        public List<JobPosting> availableProjects = new ArrayList<>();

        public List<EmployeeSkill> getVerifiedSkills() {
            return employeeSkills.stream()
                    .filter(s -> s.getProficiencyPercentage() >= 50 || "Expert".equalsIgnoreCase(s.getCurrentLevel()) || "Advanced".equalsIgnoreCase(s.getCurrentLevel()))
                    .collect(Collectors.toList());
        }

        public List<EmployeeSkill> getSkillGaps() {
            return employeeSkills.stream()
                    .filter(s -> s.getGapPercentage() > 0 || "Beginner".equalsIgnoreCase(s.getCurrentLevel()) || s.getProficiencyPercentage() < 60)
                    .collect(Collectors.toList());
        }

        public List<TrainingEnrollment> getCompletedCourses() {
            return enrollments.stream()
                    .filter(e -> "COMPLETED".equalsIgnoreCase(e.getCompletionStatus()) || e.getAttendancePercentage() >= 80.0 || e.getAssessmentScore() >= 70.0)
                    .collect(Collectors.toList());
        }

        public List<TrainingEnrollment> getInProgressCourses() {
            return enrollments.stream()
                    .filter(e -> !"COMPLETED".equalsIgnoreCase(e.getCompletionStatus()))
                    .collect(Collectors.toList());
        }
    }

    public static String generateGreeting(EmployeeContext ctx) {
        String name = (ctx.fullName != null && !ctx.fullName.isBlank()) ? ctx.fullName : "Colleague";
        String dept = (ctx.department != null && !ctx.department.isBlank()) ? ctx.department : "General";
        String role = (ctx.role != null && !ctx.role.isBlank()) ? ctx.role : "Employee";

        int totalSkills = ctx.employeeSkills.size();
        int gapsCount = ctx.getSkillGaps().size();
        int completedCount = ctx.getCompletedCourses().size();

        StringBuilder sb = new StringBuilder();
        sb.append("Hello ").append(name).append("! 👋\n\n");
        sb.append("I am your personalized **SkillVerse AI Learning Assistant**.\n");
        sb.append("I have synced your live profile from the **").append(dept).append("** department (").append(role).append(").\n\n");

        sb.append("📊 **Quick Profile Snapshot:**\n");
        sb.append("• Verified Skills Tracked: ").append(totalSkills).append("\n");
        sb.append("• Priority Skill Gaps Identified: ").append(gapsCount).append("\n");
        sb.append("• Completed Training Modules: ").append(completedCount).append("\n\n");

        sb.append("You can ask me to **analyze your skill gaps**, **create a personalized learning roadmap**, ")
                .append("**recommend courses**, **match you with mentors**, or **suggest suitable internal projects**.");

        return sb.toString();
    }

    public static String processQuery(String query, EmployeeContext ctx) {
        if (query == null || query.trim().isEmpty()) {
            return "Please enter a question or choose one of the quick prompts below!";
        }
        String q = query.trim().toLowerCase();

        if (q.contains("gap") || q.contains("skill gap") || q.contains("weakness") || q.contains("deficiency") || q.contains("lacking")) {
            return generateSkillGapAnalysis(ctx);
        } else if (q.contains("roadmap") || q.contains("path") || q.contains("plan") || q.contains("learning plan") || q.contains("journey")) {
            return generatePersonalizedRoadmap(ctx);
        } else if (q.contains("course") || q.contains("training") || q.contains("learn") || q.contains("class") || q.contains("program")) {
            return generateCourseRecommendations(ctx);
        } else if (q.contains("mentor") || q.contains("coach") || q.contains("trainer") || q.contains("guide") || q.contains("teacher")) {
            return generateMentorRecommendations(ctx);
        } else if (q.contains("project") || q.contains("opening") || q.contains("job") || q.contains("opportunity") || q.contains("mobility")) {
            return generateProjectRecommendations(ctx);
        } else if (q.contains("career") || q.contains("goal") || q.contains("promotion") || q.contains("growth") || q.contains("next role") || q.contains("future")) {
            return generateCareerDevelopmentAdvice(ctx);
        } else {
            return generateContextualAnswer(query, ctx);
        }
    }

    public static String generateSkillGapAnalysis(EmployeeContext ctx) {
        List<EmployeeSkill> gaps = ctx.getSkillGaps();
        StringBuilder sb = new StringBuilder();

        sb.append("🎯 **Personalized Skill Gap Analysis for ").append(ctx.fullName).append("**\n");
        sb.append("Department: **").append(ctx.department).append("** | Current Role: **").append(ctx.role).append("**\n\n");

        if (gaps.isEmpty()) {
            sb.append("✨ **Great news!** You currently have **zero critical skill gaps** recorded in your competency matrix. ")
                    .append("Your verified proficiencies are performing well above company thresholds.\n\n");
            sb.append("🚀 **Next Strategic Step:** We recommend looking into advanced domain specialization or leadership mentorship to prepare for higher band responsibilities.");
            return sb.toString();
        }

        sb.append("Based on your role expectations and competency targets, here are your top identified gaps:\n\n");

        int index = 1;
        for (EmployeeSkill s : gaps) {
            sb.append(index++).append(". **").append(s.getSkillName()).append("**\n");
            sb.append("   • Current Level: ").append(s.getCurrentLevel() != null ? s.getCurrentLevel() : "Beginner")
                    .append(" (").append(s.getProficiencyPercentage()).append("% proficiency)\n");
            sb.append("   • Target Required Level: ").append(s.getRequiredLevel() != null ? s.getRequiredLevel() : "Advanced")
                    .append(" (Gap: ").append(s.getGapPercentage()).append("%)\n");

            if (s.getRecommendedCourseName() != null && !s.getRecommendedCourseName().isBlank()) {
                sb.append("   • Recommended Course: *").append(s.getRecommendedCourseName()).append("*\n");
            }
            sb.append("\n");
        }

        sb.append("💡 **AI Insight:** Bridging these gaps will increase your overall readiness for upcoming team projects and internal promotional cycles.");
        return sb.toString();
    }

    public static String generateCourseRecommendations(EmployeeContext ctx) {
        List<EmployeeSkill> gaps = ctx.getSkillGaps();
        List<TrainingEnrollment> completed = ctx.getCompletedCourses();
        Set<String> completedTitles = completed.stream()
                .map(e -> e.getProgramTitle() != null ? e.getProgramTitle().toLowerCase().trim() : "")
                .collect(Collectors.toSet());

        StringBuilder sb = new StringBuilder();
        sb.append("📚 **Targeted Course Recommendations for ").append(ctx.fullName).append("**\n");
        sb.append("Department: **").append(ctx.department).append("** | Experience: **").append(ctx.experienceLevel).append("**\n\n");

        List<TrainingProgram> recommended = new ArrayList<>();

        // Priority 1: Match with specific skill gaps
        for (EmployeeSkill gap : gaps) {
            String targetSkill = gap.getSkillName().toLowerCase();
            for (TrainingProgram tp : ctx.availableCourses) {
                if (tp.getTitle() == null) {
                    continue;
                }
                String titleLower = tp.getTitle().toLowerCase();
                String descLower = tp.getDescription() != null ? tp.getDescription().toLowerCase() : "";

                if (!completedTitles.contains(titleLower) && (titleLower.contains(targetSkill) || descLower.contains(targetSkill))) {
                    if (!recommended.contains(tp)) {
                        recommended.add(tp);
                    }
                }
            }
        }

        for (TrainingProgram tp : ctx.availableCourses) {
            if (recommended.size() >= 5) {
                break;
            }
            if (tp.getTitle() == null) {
                continue;
            }
            String titleLower = tp.getTitle().toLowerCase();
            String progDept = tp.getDepartment() != null ? tp.getDepartment() : "ALL";

            if (!completedTitles.contains(titleLower) && !recommended.contains(tp)) {
                if ("ALL".equalsIgnoreCase(progDept) || progDept.equalsIgnoreCase(ctx.department)) {
                    recommended.add(tp);
                }
            }
        }

        if (recommended.isEmpty()) {
            if (!ctx.availableCourses.isEmpty()) {
                recommended.addAll(ctx.availableCourses.subList(0, Math.min(3, ctx.availableCourses.size())));
            }
        }

        if (recommended.isEmpty()) {
            sb.append("No new open courses are currently registered in your department's catalog. Please check back soon or consult your L&D manager.");
            return sb.toString();
        }

        sb.append("Here are the top learning programs curated to accelerate your growth:\n\n");
        int idx = 1;
        for (TrainingProgram tp : recommended) {
            sb.append(idx++).append(". **").append(tp.getTitle()).append("**\n");
            if (tp.getDescription() != null && !tp.getDescription().isBlank()) {
                sb.append("   • Overview: ").append(tp.getDescription()).append("\n");
            }
            sb.append("   • Lead Trainer: ").append(tp.getAssignedTrainerName() != null ? tp.getAssignedTrainerName() : "L&D Specialist").append("\n");
            sb.append("   • Duration: ").append(tp.getDurationWeeks() != null ? tp.getDurationWeeks() : "4").append(" weeks\n");
            sb.append("   • Department Focus: ").append(tp.getDepartment() != null ? tp.getDepartment() : "General").append("\n\n");
        }

        sb.append("👉 *You can enroll directly from the 'Learning & Courses' hub.*");
        return sb.toString();
    }

    public static String generatePersonalizedRoadmap(EmployeeContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append("🗺️ **Personalized Learning & Upskilling Roadmap**\n");
        sb.append("Tailored for **").append(ctx.fullName).append("** (").append(ctx.role).append(", ").append(ctx.department).append(")\n\n");

        List<EmployeeSkill> gaps = ctx.getSkillGaps();
        String targetGoal = ctx.targetCareerGoal != null ? ctx.targetCareerGoal : "Senior " + ctx.role;

        sb.append("🎯 **Target Milestone:** ").append(targetGoal).append("\n\n");

        // Phase 1
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        sb.append("📍 **Phase 1: Foundation & Immediate Gap Remediation (Weeks 1 – 4)**\n");
        if (!gaps.isEmpty()) {
            EmployeeSkill primaryGap = gaps.get(0);
            sb.append("• **Focus Area:** Rapid closure of **").append(primaryGap.getSkillName()).append("** gap.\n");
            sb.append("• **Target:** Raise proficiency from ").append(primaryGap.getProficiencyPercentage())
                    .append("% to 75% via targeted coursework.\n");
        } else {
            sb.append("• **Focus Area:** Core architecture principles and enterprise best practices in ").append(ctx.department).append(".\n");
        }
        sb.append("• **Action Item:** Complete foundational modules and participate in weekly technical workshops.\n\n");
// Phase 2
        sb.append("📍 **Phase 2: Core Competency & Practical Application (Weeks 5 – 8)**\n");
        if (gaps.size() > 1) {
            EmployeeSkill secondaryGap = gaps.get(1);
            sb.append("• **Focus Area:** Mastering **").append(secondaryGap.getSkillName()).append("** and cross-functional integration.\n");
        } else {
            sb.append("• **Focus Area:** Deep-dive into scalable system architecture, automated testing, and CI/CD pipelines.\n");
        }
        sb.append("• **Action Item:** Partner with an assigned L&D mentor to conduct code reviews and milestone check-ins.\n\n");

        sb.append("📍 **Phase 3: Real-World Project Execution & Evaluation (Weeks 9 – 12)**\n");
        sb.append("• **Focus Area:** Hands-on application in high-impact internal projects.\n");
        sb.append("• **Target:** Deliver tangible deliverables to qualify for role band advancement.\n");
        sb.append("• **Action Item:** Submit final assessment and schedule performance review with your manager.\n");
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");

        sb.append("💡 *This roadmap is dynamic and will update as you complete courses and improve skill scores!*");
        return sb.toString();
    }

    public static String generateMentorRecommendations(EmployeeContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append("🤝 **Recommended Mentors for ").append(ctx.fullName).append("**\n\n");

        List<User> mentors = ctx.availableMentors;

        if (mentors == null || mentors.isEmpty()) {
            sb.append("Currently, our central mentor registry is updating. ")
                    .append("You can connect with your reporting manager or our L&D team lead for direct 1-on-1 mentorship.");
            return sb.toString();
        }

        sb.append("Based on your department (**").append(ctx.department).append("**), target career objectives, and technical focus, here are suggested mentors:\n\n");

        int idx = 1;
        for (User m : mentors) {
            String mName = m.getName() != null ? m.getName() : "Mentor";
            String mDept = m.getDepartment() != null ? m.getDepartment() : "L&D";
            String mEmail = m.getEmail() != null ? m.getEmail() : "";

            sb.append(idx++).append(". **").append(mName).append("**\n");
            sb.append("   • Domain / Focus: ").append(mDept).append(" & Technical Architecture\n");
            sb.append("   • Availability: Open for 1-on-1 Coaching & Code Reviews\n");
            if (!mEmail.isBlank()) {
                sb.append("   • Contact: `").append(mEmail).append("`\n");
            }
            sb.append("\n");

            if (idx > 4) {
                break;
            }
        }

        sb.append("💡 **Recommendation:** Reach out to discuss your current learning roadmap and request guidance on your upcoming milestone assessment.");
        return sb.toString();
    }

    public static String generateProjectRecommendations(EmployeeContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append("💼 **Recommended Projects & Internal Opportunities**\n");
        sb.append("Matched to **").append(ctx.fullName).append("** based on active competencies and growth areas\n\n");

        List<JobPosting> projects = ctx.availableProjects;

        if (projects == null || projects.isEmpty()) {
            sb.append("There are currently no open cross-department project requisitions listed. ")
                    .append("Check with your team manager for sprint project assignments or upcoming initiatives.");
            return sb.toString();
        }

        int count = 0;
        for (JobPosting jp : projects) {
            if ("CLOSED".equalsIgnoreCase(jp.getStatus())) {
                continue;
            }

            sb.append("• **").append(jp.getTitle()).append("**\n");
            sb.append("  - Department: ").append(jp.getDepartment() != null ? jp.getDepartment() : "Enterprise").append("\n");
            sb.append("  - Experience Band: ").append(jp.getExperience() != null ? jp.getExperience() : "Mid-Senior").append("\n");
            if (jp.getSkillsRequired() != null && !jp.getSkillsRequired().isBlank()) {
                sb.append("  - Core Skills: ").append(jp.getSkillsRequired()).append("\n");
            }
            if (jp.getDescription() != null && !jp.getDescription().isBlank()) {
                String desc = jp.getDescription();
                if (desc.length() > 120) {
                    desc = desc.substring(0, 117) + "...";
                }
                sb.append("  - Brief: ").append(desc).append("\n");
            }
            sb.append("\n");
            count++;
            if (count >= 4) {
                break;
            }
        }

        sb.append("👉 *You can apply or express interest under the 'Career & Openings' section.*");
        return sb.toString();
    }

    public static String generateCareerDevelopmentAdvice(EmployeeContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append("🚀 **Career Development & Advancement Guidance**\n\n");

        String currentRole = ctx.role != null ? ctx.role : "Software Engineer";
        String dept = ctx.department != null ? ctx.department : "Engineering";

        sb.append("As a **").append(currentRole).append("** in the **").append(dept).append("** division, here is your career progression blueprint:\n\n");

        sb.append("1. **Close Immediate Skill Gaps (Critical Foundation)**\n");
        sb.append("   Consistent top performers maintain at least an 80% proficiency across core technical competencies. ")
                .append("Prioritize your current gap modules to demonstrate mastery.\n\n");

        sb.append("2. **Technical Ownership & Project Impact**\n");
        sb.append("   Transition from task execution to leading feature deliverables. Contribute to architecture reviews and mentor incoming peers.\n\n");

        sb.append("3. **Cross-Functional Collaboration**\n");
        sb.append("   Engage with product, QA, and DevOps stakeholders to broaden your system comprehension beyond your immediate team boundaries.\n\n");

        sb.append("4. **Target Next Role Milestone**\n");
        sb.append("   Prepare for promotional readiness by completing company-sponsored training certifications and achieving high attendance in live technical workshops.");

        return sb.toString();
    }

    public static String generateContextualAnswer(String userQuestion, EmployeeContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append("Regarding your question: *\"").append(userQuestion).append("\"*\n\n");

        sb.append("Here is advice tailored specifically to your profile (**")
                .append(ctx.fullName).append("**, ")
                .append(ctx.department).append(" department, ")
                .append(ctx.role).append("):\n\n");

        sb.append("• **Skill Gaps:** You have ").append(ctx.getSkillGaps().size()).append(" areas targeted for upskilling. Addressing them will directly benefit your everyday technical velocity.\n");
        sb.append("• **Learning Catalog:** SkillVerse has ").append(ctx.availableCourses.size()).append(" available training tracks matching enterprise requirements.\n");
        sb.append("• **Recommendation:** Continue executing your learning roadmap step by step. Feel free to ask me for specific course details, mentor pairings, or roadmap milestones anytime!");

        return sb.toString();
    }
}
