package samatov.jdbcProject.controller;

import lombok.RequiredArgsConstructor;
import samatov.jdbcProject.dto.LabelDTO;
import samatov.jdbcProject.dto.PostDTO;
import samatov.jdbcProject.service.LabelService;
import samatov.jdbcProject.service.PostService;

import java.util.List;

@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final LabelService labelService;

    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    public PostDTO getPostById(Integer id) {
        return postService.getPostById(id);
    }

    public PostDTO createPost(PostDTO postDTO) {
        return postService.createPost(postDTO);
    }

    public PostDTO updatePost(PostDTO postDTO) {
        return postService.updatePost(postDTO);
    }

    public void deletePost(Integer id) {
        postService.deletePost(id);
    }

    public void addLabelToPost(Integer postId, Integer labelId) {
        // Поиск метки по ID
        LabelDTO labelDTO = labelService.getLabelById(labelId);
        if (labelDTO != null) {
            postService.addLabelToPost(postId, labelDTO);
        } else {
            throw new RuntimeException("Метка с ID " + labelId + " не найдена.");
        }
    }

    public void removeLabelFromPost(Integer postId, Integer labelId) {
        postService.removeLabelFromPost(postId, labelId);
    }
}
