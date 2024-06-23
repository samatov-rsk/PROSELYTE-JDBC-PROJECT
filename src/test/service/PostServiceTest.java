package samatov.jdbcProject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import samatov.jdbcProject.dto.LabelDTO;
import samatov.jdbcProject.dto.PostDTO;
import samatov.jdbcProject.enums.PostStatus;
import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.model.Post;
import samatov.jdbcProject.repository.PostRepository;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    public void testGetAllPosts() {
        when(postRepository.findAll()).thenReturn(Arrays.asList(post));
        List<PostDTO> posts = postService.getAllPosts();
        assertNotNull(posts);
        assertEquals(1, posts.size());
        verify(postRepository, times(1)).findAll();
    }

    @Test
    public void testGetPostById() {
        when(postRepository.findById(1)).thenReturn(post);
        PostDTO foundPost = postService.getPostById(1);
        assertNotNull(foundPost);
        assertEquals(postDTO.getId(), foundPost.getId());
        verify(postRepository, times(1)).findById(1);
    }

    @Test
    public void testCreatePost() {
        when(postRepository.save(any(Post.class))).thenReturn(post);
        PostDTO createdPost = postService.createPost(postDTO);
        assertNotNull(createdPost);
        assertEquals(postDTO.getContent(), createdPost.getContent());
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    public void testUpdatePost() {
        when(postRepository.update(any(Post.class))).thenReturn(post);
        PostDTO updatedPost = postService.updatePost(postDTO);
        assertNotNull(updatedPost);
        assertEquals(postDTO.getContent(), updatedPost.getContent());
        verify(postRepository, times(1)).update(any(Post.class));
    }

    @Test
    public void testDeletePost() {
        doNothing().when(postRepository).removeById(1);
        postService.deletePost(1);
        verify(postRepository, times(1)).removeById(1);
    }

    @Test
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
    public void testRemoveLabelFromPost() {
        doNothing().when(postRepository).removeLabelFromPost(1, 1);
        postService.removeLabelFromPost(1, 1);
        verify(postRepository, times(1)).removeLabelFromPost(1, 1);
    }
}
