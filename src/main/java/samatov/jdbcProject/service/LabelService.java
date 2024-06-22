package samatov.jdbcProject.service;

import lombok.RequiredArgsConstructor;
import samatov.jdbcProject.dto.LabelDTO;
import samatov.jdbcProject.mapper.LabelMapper;
import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.repository.LabelRepository;
import samatov.jdbcProject.repository.impl.LabelRepositoryImpl;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class LabelService {

    private final LabelRepository labelRepository;

    public List<LabelDTO> getAllLabels() {
        List<Label> labels = labelRepository.findAll();
        return labels.stream()
                .map(LabelMapper::toLabelDTO)
                .collect(Collectors.toList());
    }

    public LabelDTO getLabelById(Integer id) {
        Label label = labelRepository.findById(id);
        return LabelMapper.toLabelDTO(label);
    }

    public LabelDTO createLabel(LabelDTO labelDTO) {
        Label label = LabelMapper.toLabelEntity(labelDTO);
        label = labelRepository.save(label);
        return LabelMapper.toLabelDTO(label);
    }

    public LabelDTO updateLabel(LabelDTO labelDTO) {
        Label label = LabelMapper.toLabelEntity(labelDTO);
        label = labelRepository.update(label);
        return LabelMapper.toLabelDTO(label);
    }

    public void deleteLabel(Integer id) {
        labelRepository.removeById(id);
    }
}
