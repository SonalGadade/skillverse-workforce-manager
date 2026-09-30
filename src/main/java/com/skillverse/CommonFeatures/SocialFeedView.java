package com.skillverse.CommonFeatures;

import com.skillverse.CommonFeatures.User;
import com.skillverse.CommonFeatures.UserSession;
import com.skillverse.Config.CloudinaryService;
import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.FeedPost;
import com.skillverse.CommonFeatures.PostComment;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SocialFeedView {

    private static final String ROYAL_BLUE = "#2563EB";
    private static final String DARK_NAVY = "#0F172A";
    private static final String TEXT_MUTED = "#64748B";
    private static final String BORDER_COLOR = "#E2E8F0";

    private File selectedImageFile = null;
    private HBox thumbnailPreviewBox = null;
    private ImageView thumbnailImageView = null;
    private VBox postsContainer = null;

    public VBox getView() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(24));
        root.setStyle("-fx-background-color: #F8FAFC;");

        VBox header = new VBox(4);
        Text titleText = new Text("SkillVerse Enterprise Feed 🌐");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        titleText.setFill(Color.web(DARK_NAVY));

        Text subText = new Text("Share announcements, job openings, and company achievements.");
        subText.setFont(Font.font("Arial", 12.5));
        subText.setFill(Color.web(TEXT_MUTED));
        header.getChildren().addAll(titleText, subText);

        VBox composerCard = createComposerCard();

        postsContainer = new VBox(16);
        postsContainer.setMaxWidth(680);

        loadFeedPosts();

        /*ScrollPane scrollPane = new ScrollPane(new VBox(20, header, composerCard, postsContainer));
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        VBox container = new VBox(scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        return container;*/

        VBox content = new VBox(20, header, composerCard, postsContainer);

        VBox container = new VBox(content);
        VBox.setVgrow(content, Priority.ALWAYS);

        return container;
    }

    private VBox createComposerCard() {
        VBox card = new VBox(12);
        card.setMaxWidth(680);
        card.setPadding(new Insets(18));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 14px; -fx-background-radius: 14px;" +
                "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.04), 10, 0, 0, 2);");

        HBox topRow = new HBox(10);
        topRow.setAlignment(Pos.CENTER_LEFT);

        User currentUser = UserSession.getCurrentUser();
        String currentName = currentUser != null && currentUser.getFullName() != null ? currentUser.getFullName() : "Employee";
        String currentRole = currentUser != null && currentUser.getRole() != null ? currentUser.getRole() : "Employee";

        Label avatarLabel = new Label(currentName.isEmpty() ? "U" : String.valueOf(currentName.charAt(0)).toUpperCase());
        avatarLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        avatarLabel.setTextFill(Color.WHITE);
        avatarLabel.setAlignment(Pos.CENTER);
        avatarLabel.setPrefSize(38, 38);
        avatarLabel.setStyle("-fx-background-color: " + ROYAL_BLUE + "; -fx-background-radius: 19px;");

        VBox userInfoBox = new VBox(2);
        Text nameText = new Text(currentName);
        nameText.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        nameText.setFill(Color.web(DARK_NAVY));

        ComboBox<String> postTypeCombo = new ComboBox<>();
        postTypeCombo.getItems().addAll("📢 General Post", "💼 Job Opening / Hiring", "💡 Knowledge Share");
        postTypeCombo.setValue("📢 General Post");
        postTypeCombo.setStyle("-fx-background-color: #F1F5F9; -fx-border-color: transparent; -fx-font-size: 11.5px; -fx-background-radius: 6px;");

        userInfoBox.getChildren().addAll(nameText, postTypeCombo);
        topRow.getChildren().addAll(avatarLabel, userInfoBox);

        TextArea contentInput = new TextArea();
        contentInput.setPromptText("Share an update, hiring announcement, or idea...");
        contentInput.setPrefRowCount(3);
        contentInput.setWrapText(true);
        contentInput.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 8px; -fx-background-radius: 8px; -fx-padding: 8px; -fx-font-size: 13px;");

        thumbnailPreviewBox = new HBox(10);
        thumbnailPreviewBox.setAlignment(Pos.CENTER_LEFT);
        thumbnailPreviewBox.setPadding(new Insets(6));
        thumbnailPreviewBox.setStyle("-fx-background-color: #F1F5F9; -fx-background-radius: 8px;");
        thumbnailPreviewBox.setVisible(false);
        thumbnailPreviewBox.setManaged(false);

        thumbnailImageView = new ImageView();
        thumbnailImageView.setFitWidth(60);
        thumbnailImageView.setFitHeight(60);
        thumbnailImageView.setPreserveRatio(true);

        Label fileNameLabel = new Label();
        fileNameLabel.setFont(Font.font("Arial", 12));
        fileNameLabel.setTextFill(Color.web(DARK_NAVY));

        Button removeImgBtn = new Button("✕ Remove");
        removeImgBtn.setStyle("-fx-background-color: #FEF2F2; -fx-text-fill: #DC2626; -fx-font-weight: bold; -fx-font-size: 11px; -fx-background-radius: 6px; -fx-cursor: hand;");
        removeImgBtn.setOnAction(e -> {
            selectedImageFile = null;
            thumbnailPreviewBox.setVisible(false);
            thumbnailPreviewBox.setManaged(false);
        });

        thumbnailPreviewBox.getChildren().addAll(thumbnailImageView, fileNameLabel, removeImgBtn);

        HBox actionRow = new HBox(12);
        actionRow.setAlignment(Pos.CENTER_LEFT);

        Button attachImgBtn = new Button("📷 Photo/Banner");
        attachImgBtn.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: " + ROYAL_BLUE + ";" +
                "-fx-font-weight: bold; -fx-font-size: 12.5px; -fx-background-radius: 8px; -fx-cursor: hand;");

        attachImgBtn.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Select Image Attachment");
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            File file = chooser.showOpenDialog(attachImgBtn.getScene().getWindow());
            if (file != null) {
                selectedImageFile = file;
                try {
                    thumbnailImageView.setImage(new Image(file.toURI().toString()));
                    fileNameLabel.setText(file.getName());
                    thumbnailPreviewBox.setVisible(true);
                    thumbnailPreviewBox.setManaged(true);
                } catch (Exception ignored) {}
            }
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button postBtn = new Button("Post / Publish →");
        postBtn.setStyle("-fx-background-color: " + ROYAL_BLUE + "; -fx-text-fill: white;" +
                "-fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 8px; -fx-padding: 8px 18px; -fx-cursor: hand;");

        postBtn.setOnAction(e -> {
            String text = contentInput.getText().trim();
            if (text.isEmpty() && selectedImageFile == null) {
                return;
            }

            postBtn.setDisable(true);
            postBtn.setText("Publishing 🚀...");

            String authorEmail = currentUser != null && currentUser.getEmail() != null ? currentUser.getEmail() : "user@skillverse.com";
            String authorDept = currentUser != null && currentUser.getDepartment() != null ? currentUser.getDepartment() : "General";

            String rawType = postTypeCombo.getValue();
            String postType = "GENERAL";
            if (rawType.contains("Job Opening")) postType = "JOB_OPENING";
            else if (rawType.contains("Knowledge")) postType = "KNOWLEDGE_SHARE";

            final String finalType = postType;

            CompletableFuture<String> uploadFuture;
            if (selectedImageFile != null) {
                uploadFuture = CloudinaryService.uploadImage(selectedImageFile, "feed_posts");
            } else {
                uploadFuture = CompletableFuture.completedFuture(null);
            }

            uploadFuture.thenAccept(imageUrl -> {
                FeedPost post = new FeedPost(null, currentName, authorEmail, currentRole, authorDept, text, finalType, imageUrl);
                FirebaseDAO.getInstance().createPost(post).thenAccept(success -> {
                    Platform.runLater(() -> {
                        postBtn.setDisable(false);
                        postBtn.setText("Post / Publish →");
                        contentInput.clear();
                        selectedImageFile = null;
                        thumbnailPreviewBox.setVisible(false);
                        thumbnailPreviewBox.setManaged(false);

                        loadFeedPosts();
                    });
                });
            });
        });

        actionRow.getChildren().addAll(attachImgBtn, spacer, postBtn);
        card.getChildren().addAll(topRow, contentInput, thumbnailPreviewBox, actionRow);
        return card;
    }

    private void loadFeedPosts() {
        if (postsContainer == null) return;
        postsContainer.getChildren().clear();

        Label loadingLabel = new Label("Loading Enterprise Feed...");
        loadingLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        loadingLabel.setTextFill(Color.web(TEXT_MUTED));
        postsContainer.getChildren().add(loadingLabel);

        FirebaseDAO.getInstance().getAllFeedPosts().thenAccept(posts -> {
            Platform.runLater(() -> {
                postsContainer.getChildren().clear();

                if (posts.isEmpty()) {
                    VBox emptyBox = new VBox(8);
                    emptyBox.setAlignment(Pos.CENTER);
                    emptyBox.setPadding(new Insets(32));
                    Label emptyTitle = new Label("No Posts Yet 🌐");
                    emptyTitle.setFont(Font.font("Arial", FontWeight.BOLD, 15));
                    emptyTitle.setTextFill(Color.web(DARK_NAVY));
                    Label emptySub = new Label("Be the first to share an update with your organization!");
                    emptySub.setFont(Font.font("Arial", 12.5));
                    emptySub.setTextFill(Color.web(TEXT_MUTED));
                    emptyBox.getChildren().addAll(emptyTitle, emptySub);
                    postsContainer.getChildren().add(emptyBox);
                } else {
                    for (FeedPost post : posts) {
                        postsContainer.getChildren().add(createPostCard(post));
                    }
                }
            });
        });
    }

    private VBox createPostCard(FeedPost post) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(18));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 14px; -fx-background-radius: 14px;" +
                "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.03), 8, 0, 0, 2);");

        if ("JOB_OPENING".equalsIgnoreCase(post.getPostType())) {
            HBox jobRibbon = new HBox(6);
            jobRibbon.setAlignment(Pos.CENTER_LEFT);
            jobRibbon.setPadding(new Insets(6, 12, 6, 12));
            jobRibbon.setStyle("-fx-background-color: #EFF6FF; -fx-border-color: #3B82F6; -fx-border-radius: 6px; -fx-background-radius: 6px;");
            Label jobText = new Label("🚀 We are Hiring!");
            jobText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            jobText.setTextFill(Color.web("#1E40AF"));
            jobRibbon.getChildren().add(jobText);
            card.getChildren().add(jobRibbon);
        }

        HBox authorRow = new HBox(10);
        authorRow.setAlignment(Pos.CENTER_LEFT);

        String name = post.getAuthorName() != null ? post.getAuthorName() : "User";
        Label avatarLabel = new Label(name.isEmpty() ? "U" : String.valueOf(name.charAt(0)).toUpperCase());
        avatarLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        avatarLabel.setTextFill(Color.WHITE);
        avatarLabel.setAlignment(Pos.CENTER);
        avatarLabel.setPrefSize(40, 40);
        avatarLabel.setStyle("-fx-background-color: " + ROYAL_BLUE + "; -fx-background-radius: 20px;");

        VBox nameBox = new VBox(2);
        HBox titleRow = new HBox(8);
        titleRow.setAlignment(Pos.CENTER_LEFT);

        Text nameText = new Text(name);
        nameText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        nameText.setFill(Color.web(DARK_NAVY));

        String roleStr = post.getAuthorRole() != null ? post.getAuthorRole().toUpperCase() : "EMPLOYEE";
        Label roleBadge = new Label();
        roleBadge.setFont(Font.font("Arial", FontWeight.BOLD, 10.5));
        roleBadge.setPadding(new Insets(2, 8, 2, 8));

        if (roleStr.contains("HR")) {
            roleBadge.setText("[HR · Hiring]");
            roleBadge.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #1D4ED8; -fx-background-radius: 12px;");
        } else if (roleStr.contains("MANAGER")) {
            roleBadge.setText("[Manager]");
            roleBadge.setStyle("-fx-background-color: #F3E8FF; -fx-text-fill: #6B21A8; -fx-background-radius: 12px;");
        } else if (roleStr.contains("TRAINER")) {
            roleBadge.setText("[Trainer]");
            roleBadge.setStyle("-fx-background-color: #FEF3C7; -fx-text-fill: #92400E; -fx-background-radius: 12px;");
        } else {
            roleBadge.setText("[Employee]");
            roleBadge.setStyle("-fx-background-color: #CCFBF1; -fx-text-fill: #115E59; -fx-background-radius: 12px;");
        }

        titleRow.getChildren().addAll(nameText, roleBadge);

        String relativeTime = formatRelativeTime(post.getCreatedAt());
        Text timeText = new Text(relativeTime);
        timeText.setFont(Font.font("Arial", 11.5));
        timeText.setFill(Color.web(TEXT_MUTED));

        nameBox.getChildren().addAll(titleRow, timeText);
        authorRow.getChildren().addAll(avatarLabel, nameBox);

        Text contentNode = new Text(post.getContent() != null ? post.getContent() : "");
        contentNode.setFont(Font.font("Arial", 13.5));
        contentNode.setFill(Color.web(DARK_NAVY));
        contentNode.setWrappingWidth(640);

        card.getChildren().addAll(authorRow, contentNode);

        if (post.getImageUrl() != null && !post.getImageUrl().isEmpty()) {
            try {
                ImageView imageView = new ImageView(new Image(post.getImageUrl(), true));
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(640);
                imageView.setFitHeight(360);

                Rectangle clip = new Rectangle();
                clip.setArcWidth(12);
                clip.setArcHeight(12);
                clip.widthProperty().bind(imageView.fitWidthProperty());
                clip.heightProperty().bind(imageView.fitHeightProperty());
                imageView.setClip(clip);

                card.getChildren().add(imageView);
            } catch (Exception ignored) {}
        }

        User currentUser = UserSession.getCurrentUser();
        String currentEmail = currentUser != null && currentUser.getEmail() != null ? currentUser.getEmail() : "";
        String currentUserName = currentUser != null && currentUser.getFullName() != null ? currentUser.getFullName() : "Employee";
        String currentUserRole = currentUser != null && currentUser.getRole() != null ? currentUser.getRole() : "Employee";

        boolean isLiked = post.getLikedBy() != null && post.getLikedBy().contains(currentEmail);
        int likesCount = post.getLikesCount();
        int commentsCount = post.getCommentsCount();

        HBox footerBar = new HBox(12);
        footerBar.setAlignment(Pos.CENTER_LEFT);

        Button likeBtn = new Button((isLiked ? "❤️ Liked (" : "👍 Like (") + likesCount + ")");
        likeBtn.setStyle(isLiked ?
                "-fx-background-color: #FEF2F2; -fx-text-fill: #DC2626; -fx-font-weight: bold; -fx-font-size: 12px; -fx-background-radius: 6px; -fx-cursor: hand;" :
                "-fx-background-color: #F8FAFC; -fx-text-fill: #475569; -fx-font-weight: bold; -fx-font-size: 12px; -fx-background-radius: 6px; -fx-cursor: hand;");

        likeBtn.setOnAction(e -> {
            if (currentEmail.isEmpty()) return;
            FirebaseDAO.getInstance().toggleLike(post.getId(), currentEmail).thenAccept(success -> {
                Platform.runLater(this::loadFeedPosts);
            });
        });

        Button commentBtn = new Button("💬 Comment (" + commentsCount + ")");
        commentBtn.setStyle("-fx-background-color: #F8FAFC; -fx-text-fill: #475569; -fx-font-weight: bold; -fx-font-size: 12px; -fx-background-radius: 6px; -fx-cursor: hand;");

        footerBar.getChildren().addAll(likeBtn, commentBtn);
        card.getChildren().add(footerBar);

        VBox commentsSection = new VBox(10);
        commentsSection.setPadding(new Insets(10, 0, 0, 0));
        commentsSection.setVisible(false);
        commentsSection.setManaged(false);

        VBox commentsListContainer = new VBox(8);

        commentBtn.setOnAction(e -> {
            boolean visible = !commentsSection.isVisible();
            commentsSection.setVisible(visible);
            commentsSection.setManaged(visible);

            if (visible) {
                loadCommentsForPost(post.getId(), commentsListContainer);
            }
        });

        HBox addCommentRow = new HBox(8);
        addCommentRow.setAlignment(Pos.CENTER_LEFT);

        TextField commentInput = new TextField();
        commentInput.setPromptText("Write a comment as " + currentUserName + "...");
        commentInput.setPrefHeight(36);
        HBox.setHgrow(commentInput, Priority.ALWAYS);
        commentInput.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 6px; -fx-background-radius: 6px; -fx-padding: 6px 10px; -fx-font-size: 12px;");

        Button submitCommentBtn = new Button("Post ➤");
        submitCommentBtn.setPrefHeight(36);
        submitCommentBtn.setStyle("-fx-background-color: " + ROYAL_BLUE + "; -fx-text-fill: white;" +
                "-fx-font-weight: bold; -fx-font-size: 12px; -fx-background-radius: 6px; -fx-cursor: hand;");

        Runnable handlePostComment = () -> {
            String cText = commentInput.getText().trim();
            if (cText.isEmpty()) return;

            PostComment newComment = new PostComment(null, post.getId(), currentUserName, currentEmail, currentUserRole, cText);

            submitCommentBtn.setDisable(true);
            FirebaseDAO.getInstance().addComment(post.getId(), newComment).thenAccept(success -> {
                Platform.runLater(() -> {
                    submitCommentBtn.setDisable(false);
                    commentInput.clear();
                    loadCommentsForPost(post.getId(), commentsListContainer);
                    post.setCommentsCount(post.getCommentsCount() + 1);
                    commentBtn.setText("💬 Comment (" + post.getCommentsCount() + ")");
                });
            });
        };

        submitCommentBtn.setOnAction(e -> handlePostComment.run());
        commentInput.setOnAction(e -> handlePostComment.run());

        addCommentRow.getChildren().addAll(commentInput, submitCommentBtn);
        commentsSection.getChildren().addAll(commentsListContainer, addCommentRow);
        card.getChildren().add(commentsSection);

        return card;
    }

    private void loadCommentsForPost(String postId, VBox container) {
        container.getChildren().clear();
        Label loadingLabel = new Label("Loading comments...");
        loadingLabel.setFont(Font.font("Arial", 11));
        loadingLabel.setTextFill(Color.web(TEXT_MUTED));
        container.getChildren().add(loadingLabel);

        FirebaseDAO.getInstance().getComments(postId).thenAccept(comments -> {
            Platform.runLater(() -> {
                container.getChildren().clear();
                if (comments.isEmpty()) {
                    Label noComments = new Label("No comments yet. Be the first to comment!");
                    noComments.setFont(Font.font("Arial", 11.5));
                    noComments.setTextFill(Color.web(TEXT_MUTED));
                    container.getChildren().add(noComments);
                } else {
                    for (PostComment c : comments) {
                        container.getChildren().add(createCommentBubble(c));
                    }
                }
            });
        });
    }

    private VBox createCommentBubble(PostComment comment) {
        VBox bubble = new VBox(4);
        bubble.setPadding(new Insets(8, 12, 8, 12));
        bubble.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 8px; -fx-background-radius: 8px;");

        HBox headerRow = new HBox(8);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        Text authorNameText = new Text(comment.getAuthorName() != null ? comment.getAuthorName() : "User");
        authorNameText.setFont(Font.font("Arial", FontWeight.BOLD, 12.5));
        authorNameText.setFill(Color.web(DARK_NAVY));

        String rStr = comment.getAuthorRole() != null ? comment.getAuthorRole().toUpperCase() : "EMPLOYEE";
        Label roleBadge = new Label("[" + (rStr.contains("HR") ? "HR" : rStr.contains("MANAGER") ? "Manager" : rStr.contains("TRAINER") ? "Trainer" : "Employee") + "]");
        roleBadge.setFont(Font.font("Arial", FontWeight.BOLD, 10));
        roleBadge.setStyle("-fx-background-color: #E2E8F0; -fx-text-fill: #334155; -fx-padding: 1px 5px; -fx-background-radius: 4px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Text timeText = new Text(formatRelativeTime(comment.getCreatedAt()));
        timeText.setFont(Font.font("Arial", 10.5));
        timeText.setFill(Color.web(TEXT_MUTED));

        headerRow.getChildren().addAll(authorNameText, roleBadge, spacer, timeText);

        Text bodyText = new Text(comment.getText() != null ? comment.getText() : "");
        bodyText.setFont(Font.font("Arial", 12.5));
        bodyText.setFill(Color.web("#334155"));
        bodyText.setWrappingWidth(620);

        bubble.getChildren().addAll(headerRow, bodyText);
        return bubble;
    }

    private String formatRelativeTime(Date date) {
        if (date == null) return "Just now";
        long diffMillis = System.currentTimeMillis() - date.getTime();
        long minutes = diffMillis / (60 * 1000);
        long hours = minutes / 60;
        if (hours > 0) return hours + "h ago";
        if (minutes > 0) return minutes + "m ago";
        return "Just now";
    }
}
