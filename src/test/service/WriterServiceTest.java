package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import samatov.jdbcProject.exception.NotFoundException;
import samatov.jdbcProject.exception.WriterException;
import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.model.Post;
import samatov.jdbcProject.model.Writer;
import samatov.jdbcProject.repository.WriterRepository;
import samatov.jdbcProject.repository.PostRepository;
import samatov.jdbcProject.service.WriterService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WriterServiceTest {

    @Mock
    private WriterRepository writerRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private WriterService writerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("when getAllWriter called then success")
    void getAllWriterTest() {
        List<Writer> expectedWriters = Arrays.asList(new Writer(), new Writer());
        when(writerRepository.findAll()).thenReturn(expectedWriters);

        List<Writer> actualWriters = writerService.getAllWriter();

        assertEquals(expectedWriters, actualWriters);
        verify(writerRepository).findAll();
    }

    @Test
    @DisplayName("when getWriterById called then success")
    void getWriterByIdTest() {
        Writer expectedWriter = new Writer();
        when(writerRepository.findById(anyInt())).thenReturn(expectedWriter);

        Writer actualWriter = writerService.getWriterById(1);

        assertEquals(expectedWriter, actualWriter);
        verify(writerRepository).findById(anyInt());
    }

    @Test
    @DisplayName("when getWriterById called then NotFoundException")
    void throwExceptionWhenWriterNotFound() {
        when(writerRepository.findById(anyInt())).thenThrow(new NotFoundException("Ошибка писатель с таким id не найден"));

        assertThrows(NotFoundException.class, () -> writerService.getWriterById(1));
        verify(writerRepository).findById(anyInt());
    }

    @Test
    @DisplayName("when saveWriter called then success")
    void saveWriterTest() {
        Writer writerToSave = new Writer();
        Writer savedWriter = new Writer();
        when(writerRepository.save(writerToSave)).thenReturn(savedWriter);

        Writer actualWriter = writerService.saveWriter(writerToSave);

        assertEquals(savedWriter, actualWriter);
        verify(writerRepository).save(writerToSave);
    }

    @Test
    @DisplayName("when saveWriter called then WriterException")
    void throwExceptionWhenSaveFails() {
        Writer writerToSave = new Writer();
        doThrow(new WriterException("Ошибка запроса, писателя не удалось сохранить")).when(writerRepository).save(writerToSave);

        assertThrows(WriterException.class, () -> writerService.saveWriter(writerToSave));
        verify(writerRepository).save(writerToSave);
    }

    @Test
    @DisplayName("when updateWriter called then success")
    void updateWriterTest() {
        Writer updatedWriter = new Writer();
        when(writerRepository.update(updatedWriter)).thenReturn(updatedWriter);

        Writer actualWriter = writerService.updateWriter(updatedWriter);

        assertEquals(updatedWriter, actualWriter);
        verify(writerRepository).update(updatedWriter);
    }

    @Test
    @DisplayName("when updateWriter called then WriterException")
    void throwExceptionWhenUpdateFails() {
        Writer updatedWriter = new Writer();
        when(writerRepository.update(updatedWriter)).thenThrow(new WriterException("Failed to update writer"));

        assertThrows(WriterException.class, () -> writerService.updateWriter(updatedWriter));
        verify(writerRepository).update(updatedWriter);
    }

    @Test
    @DisplayName("when deleteWriterById called then success")
    void deleteWriterByIdTest() {
        doNothing().when(writerRepository).removeById(anyInt());

        writerService.deleteWriterById(anyInt());

        verify(writerRepository).removeById(anyInt());
    }

    @Test
    @DisplayName("when deleteWriterById called then WriterException")
    void throwExceptionWhenDeletionFails() {
        doThrow(new WriterException("Failed to delete writer")).when(writerRepository).removeById(anyInt());

        assertThrows(WriterException.class, () -> writerService.deleteWriterById(1));
        verify(writerRepository).removeById(anyInt());
    }

    @Test
    @DisplayName("when removePostsByWriterId called then success")
    void removePostsByWriterIdTest() {
        doNothing().when(writerRepository).removePostsByWriterId(anyInt());

        writerService.removePostsByWriterId(anyInt());

        verify(writerRepository).removePostsByWriterId(anyInt());
    }

    @Test
    @DisplayName("when addPostToWriter called then success")
    void addPostToWriterTest() {
        Writer writer = new Writer(1, "John", "Doe", new ArrayList<>());
        Post post = new Post();
        List<Label> labels = Arrays.asList(new Label(1, "Label1"), new Label(2, "Label2"));
        when(writerRepository.findById(1)).thenReturn(writer);
        when(postRepository.save(post)).thenReturn(post);

        writerService.addPostToWriter(post, labels, 1);

        verify(writerRepository).findById(1);
        verify(postRepository).save(post);
        verify(postRepository, times(labels.size())).addLabelToPost(eq(post.getId()), any(Label.class));
        verify(writerRepository).update(writer);
    }
}
