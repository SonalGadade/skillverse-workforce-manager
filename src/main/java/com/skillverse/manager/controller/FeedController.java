package com.skillverse.manager.controller;

import java.util.ArrayList;
import java.util.List;

import com.skillverse.manager.model.FeedModel;
import com.skillverse.manager.view.Feed;

import javafx.scene.Scene;

public class FeedController {

    private Feed feedView;

    private List<FeedModel> postList;

    private Runnable callBackActionDashboard;

    public FeedController() {

        feedView = new Feed();

        postList = new ArrayList<>();

        loadPosts();
    }

    public FeedController(List<FeedModel> postList) {

        feedView = new Feed();

        this.postList = postList;
    }

    private void loadPosts() {

        postList.add(new FeedModel("Priya Sharma", "Sr. Engineer", "Completed the new payment module for the Smart Commerce project.", "2 hours ago", 24, 6));

        postList.add(new FeedModel("Rahul Verma", "Data Scientist", "Completed the Advanced Machine Learning certification.", "5 hours ago", 41, 9));

        postList.add(new FeedModel("Sneha Joshi", "Product Designer", "Sharing our latest product design improvements with the team.", "Yesterday", 32, 4));
    }

    public Scene getFeedScene(Runnable callBackActionDashboard) {

        this.callBackActionDashboard = callBackActionDashboard;

        return feedView.getFeedScene(postList, () -> navigateToDashboard());
    }

    private void navigateToDashboard() {

        if (callBackActionDashboard != null) {

            callBackActionDashboard.run();
        }
    }

    public List<FeedModel> getPostList() {

        return postList;
    }

    public void addPost(FeedModel post) {

        if (post != null) {

            postList.add(post);
        }
    }

    public void likePost(FeedModel post) {

        if (post != null) {

            post.addLike();
        }
    }

    public void unlikePost(FeedModel post) {

        if (post != null) {

            post.removeLike();
        }
    }

    public void commentOnPost(FeedModel post) {

        if (post != null) {

            post.addComment();
        }
    }

    public void savePost(FeedModel post) {

        if (post != null) {

            post.setSaved(true);
        }
    }

    public void unsavePost(FeedModel post) {

        if (post != null) {

            post.setSaved(false);
        }
    }

    public boolean isPostLiked(FeedModel post) {

        if (post == null) {

            return false;
        }

        return post.isLiked();
    }

    public boolean isPostSaved(FeedModel post) {

        if (post == null) {

            return false;
        }

        return post.isSaved();
    }
}
