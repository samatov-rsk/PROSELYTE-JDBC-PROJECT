package samatov.jdbcProject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import samatov.jdbcProject.dto.LabelDTO;
import samatov.jdbcProject.mapper.LabelMapper;
import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.repository.LabelRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class LabelService {

    private final LabelRepository labelRepository;

    public List<LabelDTO> getAllLabels() {
        log.info("Получение всех меток");
        List<Label> labels = labelRepository.findAll();
        log.info("Найдено {} меток", labels.size());
        return labels.stream()
                .map(LabelMapper::toLabelDTO)
                .collect(Collectors.toList());
    }

    public LabelDTO getLabelById(Integer id) {
        log.info("Получение метки по id: {} ", id);
        Label label = labelRepository.findById(id);
        log.info("Получена метки с id: {} ", id);
        return LabelMapper.toLabelDTO(label);
    }

    public LabelDTO createLabel(LabelDTO labelDTO) {
        log.info("Добавление метки новой метки: {} ", labelDTO);
        Label label = LabelMapper.toLabelEntity(labelDTO);
        label = labelRepository.save(label);
        log.info("Новая метка добавлена: {} ", labelDTO);
        return LabelMapper.toLabelDTO(label);
    }

    public LabelDTO updateLabel(LabelDTO labelDTO) {
        log.info("Изменение метки: {} ", labelDTO);
        Label label = LabelMapper.toLabelEntity(labelDTO);
        label = labelRepository.update(label);
        log.info("Метка изменена: {} ", labelDTO);
        return LabelMapper.toLabelDTO(label);
    }

    public void deleteLabel(Integer id) {
        labelRepository.removeById(id);
        log.info("Удаление метки по id: {} ", id);
    }
}
