package samatov.jdbcProject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import samatov.jdbcProject.dto.LabelDTO;
import samatov.jdbcProject.dto.PostDTO;
import samatov.jdbcProject.enums.PostStatus;
import samatov.jdbcProject.exception.NotFoundException;
import samatov.jdbcProject.exception.PostException;
import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.model.Post;
import samatov.jdbcProject.repository.PostRepository;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    private Post post;
    private PostDTO postDTO;
    private Label label;
    private LabelDTO labelDTO;

    @BeforeEach
    public void setUp() {
        label = Label.builder().id(1).name("Test Label").build();
        labelDTO = LabelDTO.builder().id(1).name("Test Label").build();

        Set<Label> labels = new HashSet<>();
        labels.add(label);

        post = Post.builder()
                .id(1)
                .content("Test Content")
                .created(new Timestamp(System.currentTimeMillis()))
                .updated(new Timestamp(System.currentTimeMillis()))
                .status(PostStatus.ACTIVE)
                .labels(labels)
                .build();

        Set<LabelDTO> labelDTOs = new HashSet<>();
        labelDTOs.add(labelDTO);

        postDTO = PostDTO.builder()
                .id(1)
                .content("Test Content")
                .created(new Timestamp(System.currentTimeMillis()))
                .updated(new Timestamp(System.currentTimeMillis()))
                .status(PostStatus.ACTIVE)
                .labels(labelDTOs)
                .build();
    }

    @Test
    @DisplayName("Получение всех постов при вызове getAllPosts")
    public void testGetAllPosts_Positive() {
        when(postRepository.findAll()).thenReturn(Arrays.asList(post));
        List<PostDTO> posts = postService.getAllPosts();
        assertNotNull(posts);
        assertEquals(1, posts.size());
        verify(postRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Получение ошибки PostException при вызове getAllPosts")
    public void testGetAllPosts_PostException() {
        doThrow(new PostException("Ошибка запроса, посты не найдены")).when(postRepository).findAll();
        PostException postException = assertThrows(PostException.class, () -> {
            postService.getAllPosts();
        });
        assertEquals("Ошибка запроса, посты не найдены", postException.getMessage());

        verify(postRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Получение поста по его id при вызове getPostById")
    public void testGetPostById_Positive() {
        when(postRepository.findById(1)).thenReturn(post);
        PostDTO foundPost = postService.getPostById(1);
        assertNotNull(foundPost);
        assertEquals(postDTO.getId(), foundPost.getId());
        verify(postRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Получение ошибки NotFoundException при вызове getPostById")
    public void testGetPostById_NotFoundException() {
        doThrow(new NotFoundException("Метка с указаным id:=" + post.getId() + " не найдена...")).when(postRepository).findById(post.getId());
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> {
            postService.getPostById(postDTO.getId());
        });
        assertEquals("Метка с указаным id:=" + post.getId() + " не найдена...", notFoundException.getMessage());

        verify(postRepository, times(1)).findById(post.getId());
    }

    @Test
    @DisplayName("Получение ошибки LabelException при вызове getPostById")
    public void testGetPostById_LabelException() {
        doThrow(new PostException("Ошибка запроса, посты не найдены")).when(postRepository).findById(post.getId());
        PostException postException = assertThrows(PostException.class, () -> {
            postService.getPostById(postDTO.getId());
        });
        assertEquals("Ошибка запроса, посты не найдены", postException.getMessage());

        verify(postRepository, times(1)).findById(post.getId());
    }


    @Test
    @DisplayName("Добавление нового поста при вызове метода createPost")
    public void testCreatePost() {
        when(postRepository.save(any(Post.class))).thenReturn(post);
        PostDTO createdPost = postService.createPost(postDTO);
        assertNotNull(createdPost);
        assertEquals(postDTO.getContent(), createdPost.getContent());
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    @DisplayName("Получение ошибки PostException при вызове метода createPost")
    public void testCreatePost_PostException() {
        doThrow(new PostException("Ошибка запроса, посты не найдены")).when(postRepository).save(post);
        PostException postException = assertThrows(PostException.class, () -> {
            postService.createPost(postDTO);
        });
        assertEquals("Ошибка запроса, посты не найдены", postException.getMessage());

        verify(postRepository, times(1)).save(post);
    }

    @Test
    @DisplayName("Обновление поста при вызове метода updatePost")
    public void testUpdatePost() {
        when(postRepository.update(any(Post.class))).thenReturn(post);
        PostDTO updatedPost = postService.updatePost(postDTO);
        assertNotNull(updatedPost);
        assertEquals(postDTO.getContent(), updatedPost.getContent());
        verify(postRepository, times(1)).update(any(Post.class));
    }

    @Test
    @DisplayName("Получение ошибки PostException при вызове метода updatePost")
    public void testUpdatePost_PostException() {
        doThrow(new PostException("Ошибка запроса, посты не найдены")).when(postRepository).update(post);

        PostException postException = assertThrows(PostException.class, () -> {
            postService.updatePost(postDTO);
        });
        assertEquals("Ошибка запроса, посты не найдены", postException.getMessage());

        verify(postRepository, times(1)).update(post);
    }

    @Test
    @DisplayName("Удаление поста при вызове метода deletePost")
    public void testDeletePost_Positive() {
        doNothing().when(postRepository).removeById(1);
        postService.deletePost(1);
        verify(postRepository, times(1)).removeById(1);
    }

    @Test
    @DisplayName("Получение ошибки PostException при вызове метода deletePost")
    public void testDeletePost_PostException() {
        doThrow(new PostException("Ошибка запроса, пост с указаным id:=" + post.getId() + " не удален..."))
                .when(postRepository).removeById(post.getId());

        PostException postException = assertThrows(PostException.class, () -> {
            postService.deletePost(postDTO.getId());
        });
        assertEquals("Ошибка запроса, пост с указаным id:=" + post.getId() + " не удален...", postException.getMessage());

        verify(postRepository, times(1)).removeById(post.getId());
    }

    @Test
    @DisplayName("Получение ошибки NotFoundException при вызове метода deletePost")
    public void testDeletePost_NotFoundException() {
        doThrow(new NotFoundException("Ошибка запроса, пост с указаным id:=" + post.getId() + " не удален..."))
                .when(postRepository).removeById(post.getId());

        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> {
            postService.deletePost(postDTO.getId());
        });
        assertEquals("Ошибка запроса, пост с указаным id:=" + post.getId() + " не удален...", notFoundException.getMessage());

        verify(postRepository, times(1)).removeById(post.getId());
    }


    @Test
    @DisplayName("Добавление метки к посту при вызове метода addLabelToPost")
    public void testAddLabelToPost() {
        when(postRepository.findById(1)).thenReturn(post);
        doAnswer(invocation -> {
            return null;
        }).when(postRepository).update(any(Post.class));

        postService.addLabelToPost(1, labelDTO);
        verify(postRepository, times(1)).findById(1);
        verify(postRepository, times(1)).update(any(Post.class));
    }

    @Test
    @DisplayName("Получение ошибки PostException при вызове метода addLabelToPost")
    public void testAddLabelToPost_PostException() {
        when(postRepository.findById(1)).thenReturn(null);

        PostException exception = assertThrows(PostException.class, () -> {
            postService.addLabelToPost(1, labelDTO);
        });

        assertEquals("Пост с ID 1 не найден.", exception.getMessage());

        verify(postRepository, times(1)).findById(1);
        verify(postRepository, never()).update(any(Post.class));
    }


    @Test
    @DisplayName("Удаление метки у поста при вызове метода removeLabelFromPost")
    public void testRemoveLabelFromPost() {
        doNothing().when(postRepository).removeLabelFromPost(1, 1);
        postService.removeLabelFromPost(1, 1);
        verify(postRepository, times(1)).removeLabelFromPost(1, 1);
    }

    @Test
    @DisplayName("Получение ошибки при вызове метода removeLabelFromPost")
    public void testRemoveLabelFromPost_PostException() {
        doThrow(new PostException("Ошибка удаления метки с поста..."))
                .when(postRepository).removeLabelFromPost(1, 1);

        PostException exception = assertThrows(PostException.class, () -> {
            postService.removeLabelFromPost(1, 1);
        });

        assertEquals("Ошибка удаления метки с поста...", exception.getMessage());

        verify(postRepository, times(1)).removeLabelFromPost(1, 1);
    }

}
