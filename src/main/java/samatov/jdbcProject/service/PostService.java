package samatov.jdbcProject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import samatov.jdbcProject.dto.LabelDTO;
import samatov.jdbcProject.dto.PostDTO;
import samatov.jdbcProject.exception.PostException;
import samatov.jdbcProject.mapper.LabelMapper;
import samatov.jdbcProject.mapper.PostMapper;
import samatov.jdbcProject.model.Post;
import samatov.jdbcProject.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    public List<PostDTO> getAllPosts() {
        log.info("Получение всех постов");
        List<Post> posts = postRepository.findAll();
        log.info("Найдено {} постов", posts.size());
        return posts.stream()
                .map(PostMapper::toPostDTO)
                .collect(Collectors.toList());
    }

    public PostDTO getPostById(Integer id) {
        log.info("Получение поста по его id: {}", id);
        Post post = postRepository.findById(id);
        log.info("Получен пост c id: {}", id);
        return PostMapper.toPostDTO(post);
    }

    public PostDTO createPost(PostDTO postDTO) {
        log.info("Добавление нового поста: {}", postDTO);
        Post post = PostMapper.toPostEntity(postDTO);
        post = postRepository.save(post);
        log.info("Добавлен новый пост: {}", postDTO);
        return PostMapper.toPostDTO(post);
    }

    public PostDTO updatePost(PostDTO postDTO) {
        log.info("Изменение поста: {}", postDTO);
        Post post = PostMapper.toPostEntity(postDTO);
        post = postRepository.update(post);
        log.info("Пост изменен: {}", postDTO);
        return PostMapper.toPostDTO(post);
    }

    public void deletePost(Integer id) {
        log.info("Удаление поста по его id: {}", id);
        postRepository.removeById(id);
    }

    public void addLabelToPost(Integer postId, LabelDTO labelDTO) {
        log.info("Добавление к посту {} новой метки: {}", postId, labelDTO);
        Post post = postRepository.findById(postId);
        if (post != null) {
            post.getLabels().add(LabelMapper.toLabelEntity(labelDTO));
            postRepository.update(post);
        } else {
            throw new PostException("Пост с ID " + postId + " не найден.");
        }
        log.info("Добавлена к посту {} новая метка: {}", postId, labelDTO);
    }

    public void removeLabelFromPost(Integer postId, Integer labelId) {
        log.info("Удаление у поста {} метки: {}", postId, labelId);
        postRepository.removeLabelFromPost(postId, labelId);
    }
}
