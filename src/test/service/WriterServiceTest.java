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
import samatov.jdbcProject.dto.WriterDTO;
import samatov.jdbcProject.exception.NotFoundException;
import samatov.jdbcProject.mapper.LabelMapper;
import samatov.jdbcProject.mapper.PostMapper;
import samatov.jdbcProject.mapper.WriterMapper;
import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.model.Post;
import samatov.jdbcProject.model.Writer;
import samatov.jdbcProject.repository.WriterRepository;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WriterServiceTest {

    @Mock
    private WriterRepository writerRepository;

    @InjectMocks
    private WriterService writerService;

    private Writer writer;
    private WriterDTO writerDTO;
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
                .status(samatov.jdbcProject.enums.PostStatus.ACTIVE)
                .labels(labels)
                .build();

        Set<LabelDTO> labelDTOs = new HashSet<>();
        labelDTOs.add(labelDTO);

        postDTO = PostDTO.builder()
                .id(1)
                .content("Test Content")
                .created(new Timestamp(System.currentTimeMillis()))
                .updated(new Timestamp(System.currentTimeMillis()))
                .status(samatov.jdbcProject.enums.PostStatus.ACTIVE)
                .labels(labelDTOs)
                .build();

        writer = Writer.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .posts(Arrays.asList(post))
                .build();

        writerDTO = WriterDTO.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .posts(Arrays.asList(postDTO))
                .build();
    }

    @Test
    @DisplayName("Получение всех писателей при вызове метода getAllWriter")
    public void testGetAllWriters() {
        when(writerRepository.findAll()).thenReturn(Arrays.asList(writer));
        List<WriterDTO> writers = writerService.getAllWriter();
        assertNotNull(writers);
        assertEquals(1, writers.size());
        verify(writerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Получение писателя по ID при вызове метода getWriterById")
    public void testGetWriterById() {
        when(writerRepository.findById(1)).thenReturn(writer);
        WriterDTO foundWriter = writerService.getWriterById(1);
        assertNotNull(foundWriter);
        assertEquals(writerDTO.getId(), foundWriter.getId());
        verify(writerRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Получение ошибки NotFoundException при вызове метода getWriterById")
    public void testGetWriterById_NotFound() {
        doThrow(new NotFoundException("Писатель с указанным id не найден")).when(writerRepository).findById(post.getId());
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> {
            writerService.getWriterById(postDTO.getId());
        });
        assertEquals("Писатель с указанным id не найден", notFoundException.getMessage());

        verify(writerRepository, times(1)).findById(post.getId());
    }

    @Test
    @DisplayName("Сохранение писателя при вызове метода saveWriter")
    public void testSaveWriter() {
        when(writerRepository.save(any(Writer.class))).thenReturn(writer);
        WriterDTO savedWriter = writerService.saveWriter(writerDTO);
        assertNotNull(savedWriter);
        assertEquals(writerDTO.getFirstName(), savedWriter.getFirstName());
        verify(writerRepository, times(1)).save(any(Writer.class));
    }

    @Test
    @DisplayName("Обновление писателя при вызове метода updateWriter")
    public void testUpdateWriter() {
        when(writerRepository.update(any(Writer.class))).thenReturn(writer);
        WriterDTO updatedWriter = writerService.updateWriter(writerDTO);
        assertNotNull(updatedWriter);
        assertEquals(writerDTO.getFirstName(), updatedWriter.getFirstName());
        verify(writerRepository, times(1)).update(any(Writer.class));
    }

    @Test
    @DisplayName("Удаление писателя по ID при вызове метода deleteWriterById")
    public void testDeleteWriterById() {
        doNothing().when(writerRepository).removeById(1);
        writerService.deleteWriterById(1);
        verify(writerRepository, times(1)).removeById(1);
    }

    @Test
    @DisplayName("Удаление всех постов писателя при вызове метода removePostsByWriterId")
    public void testRemovePostsByWriterId() {
        doNothing().when(writerRepository).removePostsByWriterId(1);
        writerService.removePostsByWriterId(1);
        verify(writerRepository, times(1)).removePostsByWriterId(1);
    }

    @Test
    @DisplayName("Добавление поста с метками к писателю при вызове метода addPostWithLabelsToWriter")
    public void testAddPostWithLabelsToWriter() {
        when(writerRepository.findById(1)).thenReturn(writer);
        doNothing().when(writerRepository).addPostWithLabelsToWriter(eq(1), any(Post.class), anySet());
        writerService.addPostWithLabelsToWriter(postDTO, new HashSet<>(Arrays.asList(labelDTO)), 1);
        verify(writerRepository, times(1)).findById(1);
        verify(writerRepository, times(1)).addPostWithLabelsToWriter(eq(1), any(Post.class), anySet());
    }

    @Test
    @DisplayName("Получение ошибки NotFoundException при вызове метода addPostWithLabelsToWriter")
    public void testAddPostWithLabelsToWriter_NotFound() {
        when(writerRepository.findById(1)).thenReturn(null);
        assertThrows(NotFoundException.class, () -> writerService.addPostWithLabelsToWriter(postDTO, new HashSet<>(Arrays.asList(labelDTO)), 1));
        verify(writerRepository, times(1)).findById(1);
        verify(writerRepository, never()).addPostWithLabelsToWriter(anyInt(), any(Post.class), anySet());
    }
}
