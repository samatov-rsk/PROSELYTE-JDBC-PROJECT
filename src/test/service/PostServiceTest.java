package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import samatov.jdbcProject.enums.PostStatus;
import samatov.jdbcProject.exception.NotFoundException;
import samatov.jdbcProject.exception.PostException;
import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.model.Post;
import samatov.jdbcProject.model.Writer;
import samatov.jdbcProject.repository.PostRepository;
import samatov.jdbcProject.service.PostService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("when getAllPost called then success")
    void getAllPostTest() {
        List<Post> expectedPosts = Arrays.asList(new Post(), new Post());
        when(postRepository.findAll()).thenReturn(expectedPosts);

        List<Post> actualPosts = postService.getAllPost();

        assertEquals(expectedPosts, actualPosts);
        verify(postRepository).findAll();
    }

    @Test
    @DisplayName("when getPostById called then success")
    void getPostByIdTest() {
        Post expectedPost = new Post();
        when(postRepository.findById(anyInt())).thenReturn(expectedPost);

        Post actualPost = postService.getPostById(1);

        assertEquals(expectedPost, actualPost);
        verify(postRepository).findById(anyInt());
    }

    @Test
    @DisplayName("when getPostById called then NotFoundException")
    void throwExceptionWhenPostNotFound() {
        when(postRepository.findById(anyInt())).thenThrow(new NotFoundException("Ошибка пост с таким id не найден"));

        assertThrows(NotFoundException.class, () -> postService.getPostById(1));
        verify(postRepository).findById(anyInt());
    }

    @Test
    @DisplayName("when savePost called then success")
    void savePostTest() {
        Post postToSave = new Post();
        postToSave.setWriter(new Writer(1, "John", "Doe", null));
        Post savedPost = new Post();
        when(postRepository.save(postToSave)).thenAnswer(invocation -> {
            Post post = invocation.getArgument(0);
            post.setId(1);
            return post;
        });

        Post actualPost = postService.savePost(postToSave, 1);

        assertNotNull(actualPost.getCreated());
        assertNotNull(actualPost.getUpdated());
        assertEquals(PostStatus.ACTIVE, actualPost.getStatus());
        verify(postRepository).save(postToSave);
    }

    @Test
    @DisplayName("when savePost called then PostException")
    void throwExceptionWhenSaveFails() {
        Post postToSave = new Post();
        when(postRepository.save(postToSave)).thenThrow(new PostException("Ошибка запроса, пост не удалось сохранить..."));

        assertThrows(PostException.class, () -> postService.savePost(postToSave, 1));
        verify(postRepository).save(postToSave);
    }

    @Test
    @DisplayName("when updatePost called then success")
    void updatePostTest() {
        Post updatedPost = new Post();
        when(postRepository.update(updatedPost)).thenReturn(updatedPost);

        Post actualPost = postService.updatePost(updatedPost);

        assertNotNull(actualPost.getUpdated());
        assertEquals(PostStatus.UNDER, actualPost.getStatus());
        verify(postRepository).update(updatedPost);
    }

    @Test
    @DisplayName("when updatePost called then PostException")
    void throwExceptionWhenUpdateFails() {
        Post updatedPost = new Post();
        when(postRepository.update(updatedPost)).thenThrow(new PostException("Failed to update post"));

        assertThrows(PostException.class, () -> postService.updatePost(updatedPost));
        verify(postRepository).update(updatedPost);
    }

    @Test
    @DisplayName("when deletePostById called then success")
    void deletePostByIdTest() {
        doNothing().when(postRepository).removeById(anyInt());

        postService.deletePostById(anyInt());

        verify(postRepository).removeById(anyInt());
    }

    @Test
    @DisplayName("when deletePostById called then PostException")
    void throwExceptionWhenDeletionFails() {
        doThrow(new PostException("Failed to delete post")).when(postRepository).removeById(anyInt());

        assertThrows(PostException.class, () -> postService.deletePostById(1));
        verify(postRepository).removeById(anyInt());
    }

    @Test
    @DisplayName("when addLabelToPost called then success")
    void addLabelToPostTest() {
        Label label = new Label(1, "Test Label");
        doNothing().when(postRepository).addLabelToPost(anyInt(), any(Label.class));

        postService.addLabelToPost(1, label);

        verify(postRepository).addLabelToPost(anyInt(), any(Label.class));
    }

    @Test
    @DisplayName("when removeLabelFromPost called then success")
    void removeLabelFromPostTest() {
        doNothing().when(postRepository).removeLabelFromPost(anyInt(), anyInt());

        postService.removeLabelFromPost(anyInt(), anyInt());

        verify(postRepository).removeLabelFromPost(anyInt(), anyInt());
    }
}
