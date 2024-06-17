package samatov.jdbcProject.controller;

import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.model.Post;
import samatov.jdbcProject.service.PostService;

import java.util.List;

public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    public List<Post> getAllPost() {
        return postService.getAllPost();
    }

    public Post getPostById(Integer id) {
        return postService.getPostById(id);
    }

    public Post savePost(Post post, int writerId) {
        return postService.savePost(post, writerId);
    }

    public Post updatePost(Post post) {
        return postService.updatePost(post);
    }

    public void deletePostById(Integer id) {
        postService.deletePostById(id);
    }

    public void addLabelToPost(Integer postId, Label label) {
        postService.addLabelToPost(postId, label);
    }

    public void removeLabelFromPost(Integer postId, Integer labelId) {
        postService.removeLabelFromPost(postId, labelId);
    }
}
