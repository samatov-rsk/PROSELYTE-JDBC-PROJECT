package jdbcProject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import samatov.jdbcProject.dto.LabelDTO;
import samatov.jdbcProject.exception.LabelException;
import samatov.jdbcProject.exception.NotFoundException;
import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.repository.LabelRepository;
import samatov.jdbcProject.service.LabelService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class LabelServiceTest {

    @Mock
    private LabelRepository labelRepository;

    @InjectMocks
    private LabelService labelService;

    private LabelDTO labelDTO;
    private Label label;

    @BeforeEach
    public void setUp() {
        label = Label.builder().id(1).name("Test Label").build();
        labelDTO = LabelDTO.builder().id(1).name("Test Label").build();
    }


    @Test
    @DisplayName("Получение всех пользователей при вызове getAllLabels")
    public void testGetAllLabels_Positive() {
        when(labelRepository.findAll()).thenReturn(Arrays.asList(label));

        List<LabelDTO> labels = labelService.getAllLabels();

        assertNotNull(labels);
        assertEquals(1, labels.size());
        assertEquals("Test Label", labels.get(0).getName());

        verify(labelRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Получение ошибки LabelException при вызове getAllLabels")
    public void testGetAllLabels_LabelException() {
        when(labelRepository.findAll()).thenThrow(new LabelException("Ошибка запроса, метки не найдены..."));

        LabelException exception = assertThrows(LabelException.class, () -> {
            labelService.getAllLabels();
        });

        assertEquals("Ошибка запроса, метки не найдены...", exception.getMessage());

        verify(labelRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Получение метки по ее id при вызове getLabelById")
    public void testGetLabelById_Positive() {
        when(labelRepository.findById(anyInt())).thenReturn(label);

        labelDTO = labelService.getLabelById(anyInt());
        assertNotNull(labelDTO);
        assertEquals(labelDTO.getId(), label.getId());
        assertEquals("Test Label", label.getName());

        verify(labelRepository, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("Получение NotFoundException при вызове getLabelById")
    public void testGetLabelById_NotFoundException() {
        when(labelRepository.findById(label.getId()))
                .thenThrow(new NotFoundException("Метка с указаным id:=" + label.getId() + " не найдена..."));

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            labelService.getLabelById(label.getId());
        });

        assertEquals("Метка с указаным id:=" + label.getId() + " не найдена...", exception.getMessage());

        verify(labelRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Получение LabelException при вызове getLabelById")
    public void testGetLabelById_LabelException() {
        when(labelRepository.findById(label.getId()))
                .thenThrow(new LabelException("Ошибка запроса, метка с указаным id:=" + label.getId() + " не найдена..."));

        LabelException exception = assertThrows(LabelException.class, () -> {
            labelService.getLabelById(label.getId());
        });

        assertNotNull(label);
        assertEquals("Ошибка запроса, метка с указаным id:=" + label.getId() + " не найдена...", exception.getMessage());

        verify(labelRepository, times(1)).findById(label.getId());
    }

    @Test
    @DisplayName("Удаление метки по ее id при вызове deleteLabel")
    public void testRemoveById_Positive() {

        doNothing().when(labelRepository).removeById(label.getId());

        labelService.deleteLabel(label.getId());

        verify(labelRepository).removeById(anyInt());
    }

    @Test
    @DisplayName("Получения NotFoundException при вызове deleteLabel")
    public void testRemoveById_NotFoundException() {
        doThrow(new NotFoundException("Метка с указаным id:=" + label.getId() + "не найдена..."))
                .when(labelRepository).removeById(label.getId());

        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> {
            labelService.deleteLabel(label.getId());
        });
        assertEquals("Метка с указаным id:=" + label.getId() + "не найдена...", notFoundException.getMessage());

        verify(labelRepository).removeById(label.getId());
    }

    @Test
    @DisplayName("Получения LabelException при вызове deleteLabel")
    public void testRemoveById_LabelException() {
        doThrow(new LabelException("Ошибка запроса, метка с указаным id:=" + label.getId() + " не найдена..."))
                .when(labelRepository).removeById(label.getId());

        LabelException labelException = assertThrows(LabelException.class, () -> {
            labelService.deleteLabel(label.getId());
        });
        assertEquals("Ошибка запроса, метка с указаным id:=" + label.getId() + " не найдена...", labelException.getMessage());

        verify(labelRepository).removeById(label.getId());
    }

    @Test
    @DisplayName("Добавление новой метки при вызове createLabel")
    public void createLabel_Positive() {

        when(labelRepository.save(any())).thenReturn(label);

        LabelDTO labelDTO1 = labelService.createLabel(labelDTO);

        assertNotNull(labelDTO1);
        assertEquals(labelDTO, labelDTO1);

        verify(labelRepository).save(any());
    }

    @Test
    @DisplayName("Получения LabelException при вызове createLabel")
    public void createLabel_LabelException() {

        doThrow(new LabelException("Ошибка запроса, метку не удалось сохранить...")).when(labelRepository).save(label);

        LabelException labelException = assertThrows(LabelException.class, () -> {
            labelService.createLabel(labelDTO);
        });

        assertEquals("Ошибка запроса, метку не удалось сохранить...", labelException.getMessage());

        verify(labelRepository).save(label);
    }

    @Test
    @DisplayName("Обновление метки при вызове updateLabel")
    public void updateLabel_Positive() {

        when(labelRepository.update(any())).thenReturn(label);

        LabelDTO labelDTO1 = labelService.updateLabel(labelDTO);

        assertNotNull(labelDTO1);
        assertEquals(labelDTO, labelDTO1);

        verify(labelRepository).update(any());
    }

    @Test
    @DisplayName("Получения LabelException при вызове updateLabel")
    public void updateLabel_LabelException() {

        doThrow(new LabelException("Ошибка запроса, метку не удалось сохранить...")).when(labelRepository).update(label);

        LabelException labelException = assertThrows(LabelException.class, () -> {
            labelService.updateLabel(labelDTO);
        });

        assertEquals("Ошибка запроса, метку не удалось сохранить...", labelException.getMessage());

        verify(labelRepository).update(label);
    }

    @Test
    @DisplayName("Получения NotFoundException при вызове updateLabel")
    public void updateLabel_NotFoundException() {

        doThrow(new NotFoundException("Ошибка запроса, метка с указаным id:=" + label.getId() + " не найдена...")).when(labelRepository).update(label);

        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> {
            labelService.updateLabel(labelDTO);
        });

        assertEquals("Ошибка запроса, метка с указаным id:=" + label.getId() + " не найдена...", notFoundException.getMessage());

        verify(labelRepository).update(label);
    }
}