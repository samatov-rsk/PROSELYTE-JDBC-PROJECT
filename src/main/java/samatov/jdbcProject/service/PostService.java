package samatov.jdbcProject.service;

import lombok.RequiredArgsConstructor;
import samatov.jdbcProject.enums.PostStatus;
import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.model.Post;
import samatov.jdbcProject.model.Writer;
import samatov.jdbcProject.repository.PostRepository;

import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    public Post getPostById(Integer id) {
        return postRepository.findById(id);
    }

    public Post savePost(Post post, Integer writerId) {
        post.setCreated(new Timestamp(System.currentTimeMillis()));
        post.setUpdated(new Timestamp(System.currentTimeMillis()));
        post.setStatus(PostStatus.ACTIVE);
        post.setWriter(new Writer(writerId, null, null, null));
        Post savedPost = postRepository.save(post);
        savePostLabels(savedPost);
        return savedPost;
    }

    public Post updatePost(Post post) {
        post.setUpdated(new Timestamp(System.currentTimeMillis()));
        post.setStatus(PostStatus.UNDER);
        Post updatedPost = postRepository.update(post);
        savePostLabels(updatedPost);
        return updatedPost;
    }

    public void deletePostById(Integer id) {
        postRepository.removeById(id);
    }

    public void addLabelToPost(Integer postId, Label label) {
        postRepository.addLabelToPost(postId, label);
    }

    public void removeLabelFromPost(Integer postId, Integer labelId) {
        postRepository.removeLabelFromPost(postId, labelId);
    }

    private void savePostLabels(Post post) {
        if (post.getLabels() != null) {
            for (Label label : post.getLabels()) {
                postRepository.addLabelToPost(post.getId(), label);
            }
        }
    }
}
