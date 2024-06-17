package samatov.jdbcProject.service;

import lombok.RequiredArgsConstructor;
import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.repository.LabelRepository;
import samatov.jdbcProject.repository.impl.LabelRepositoryImpl;

import java.util.List;

@RequiredArgsConstructor
public class LabelService {

    private final LabelRepository labelRepository;

    public List<Label> getAllLabel() {
        return labelRepository.findAll();
    }

    public Label getLabelById(Integer id) {
        return labelRepository.findById(id);
    }

    public Label saveLabel(Label label) {
        return labelRepository.save(label);
    }

    public Label updateLabel(Label label) {
        return labelRepository.update(label);
    }

    public void deleteLabelById(Integer id) {
        labelRepository.removeById(id);
    }
}
