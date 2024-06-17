package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import samatov.jdbcProject.exception.LabelException;
import samatov.jdbcProject.exception.NotFoundException;
import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.repository.impl.LabelRepositoryImpl;
import samatov.jdbcProject.service.LabelService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class LabelServiceTest {
    @Mock
    private LabelRepositoryImpl labelRepository;

    @InjectMocks
    private LabelService labelService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("when getAllLabel called then success")
    void getAllLabelTest() {
        List<Label> expectedLabels = Arrays.asList(new Label(1, "Label1"), new Label(2, "Label2"));
        when(labelRepository.findAll()).thenReturn(expectedLabels);

        List<Label> actualLabels = labelService.getAllLabel();

        assertEquals(expectedLabels, actualLabels);
        verify(labelRepository).findAll();
    }

    @Test
    @DisplayName("when getAllLabelById called then success")
    void getLabelByIdTest() {
        Label expectedLabel = new Label(1, "Label");
        when(labelRepository.findById(anyInt())).thenReturn(expectedLabel);

        Label actualLabel = labelService.getLabelById(1);

        assertEquals(expectedLabel, actualLabel);
        verify(labelRepository).findById(anyInt());
    }

    @Test
    @DisplayName("when getLabelById called then NotFoundException")
    void ThrowExceptionWhenLabelNotFound() {
        when(labelRepository.findById(anyInt())).thenThrow(new NotFoundException("Ошибка метка с таким id не найдена"));

        assertThrows(NotFoundException.class, () -> labelService.getLabelById(1));
        verify(labelRepository).findById(anyInt());
    }


    @Test
    @DisplayName("when saveLabel called then success")
    void saveLabelTest() {
        Label labelToSave = new Label(null, "NewLabel");
        Label savedLabel = new Label(1, "NewLabel");
        when(labelRepository.save(labelToSave)).thenReturn(savedLabel);

        Label actualLabel = labelService.saveLabel(labelToSave);

        assertEquals(savedLabel, actualLabel);
        verify(labelRepository).save(labelToSave);
    }

    @Test
    @DisplayName("when saveLabel called then LabelException")
    void ThrowExceptionWhenSaveFails() {
        Label labelToSave = new Label(null, "NewLabel");
        when(labelRepository.save(labelToSave))
                .thenThrow(new LabelException("Ошибка запроса, метку не удалось сохранить..."));

        assertThrows(LabelException.class, () -> labelService.saveLabel(labelToSave));
        verify(labelRepository).save(labelToSave);
    }


    @Test
    @DisplayName("when updateLabelById called then success")
    void updateLabelByIdTest() {
        Label updatedLabel = new Label(1, "UpdatedLabel");
        when(labelRepository.update(updatedLabel)).thenReturn(updatedLabel);

        Label actualLabel = labelService.updateLabel(updatedLabel);

        assertEquals(updatedLabel, actualLabel);
        verify(labelRepository).update(updatedLabel);
    }

    @Test
    @DisplayName("when updateLabel called then LabelException")
    void ThrowExceptionWhenUpdateFails() {
        Label updatedLabel = new Label(1, "UpdatedLabel");
        when(labelRepository.update(updatedLabel)).thenThrow(new LabelException("Failed to update label"));

        assertThrows(LabelException.class, () -> labelService.updateLabel(updatedLabel));
        verify(labelRepository).update(updatedLabel);
    }


    @Test
    @DisplayName("when deleteLabelById called then success")
    void deleteLabelByIdTest() {
        doNothing().when(labelRepository).removeById(anyInt());

        labelService.deleteLabelById(anyInt());

        verify(labelRepository).removeById(anyInt());
    }

    @Test
    @DisplayName("when deleteLabelById called then LabelException")
    void ThrowExceptionWhenDeletionFails() {
        doThrow(new LabelException("Failed to delete label")).when(labelRepository).removeById(anyInt());

        assertThrows(LabelException.class, () -> labelService.deleteLabelById(1));
        verify(labelRepository).removeById(anyInt());
    }

}
