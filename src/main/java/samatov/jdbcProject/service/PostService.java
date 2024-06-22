package samatov.jdbcProject.service;

import lombok.RequiredArgsConstructor;
import samatov.jdbcProject.dto.LabelDTO;
import samatov.jdbcProject.dto.PostDTO;
import samatov.jdbcProject.mapper.LabelMapper;
import samatov.jdbcProject.mapper.PostMapper;
import samatov.jdbcProject.model.Post;
import samatov.jdbcProject.repository.PostRepository;
import samatov.jdbcProject.repository.impl.PostRepositoryImpl;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostMapper::toPostDTO)
                .collect(Collectors.toList());
    }

    public PostDTO getPostById(Integer id) {
        Post post = postRepository.findById(id);
        return PostMapper.toPostDTO(post);
    }

    public PostDTO createPost(PostDTO postDTO) {
        Post post = PostMapper.toPostEntity(postDTO);
        post = postRepository.save(post);
        return PostMapper.toPostDTO(post);
    }

    public PostDTO updatePost(PostDTO postDTO) {
        Post post = PostMapper.toPostEntity(postDTO);
        post = postRepository.update(post);
        return PostMapper.toPostDTO(post);
    }

    public void deletePost(Integer id) {
        postRepository.removeById(id);
    }

    public void addLabelToPost(Integer postId, LabelDTO labelDTO) {
        Post post = postRepository.findById(postId);
        if (post != null) {
            post.getLabels().add(LabelMapper.toLabelEntity(labelDTO));
            postRepository.update(post);
        } else {
            throw new RuntimeException("Пост с ID " + postId + " не найден.");
        }
    }

    public void removeLabelFromPost(Integer postId, Integer labelId) {
        postRepository.removeLabelFromPost(postId, labelId);
    }
}
