package samatov.jdbcProject.controller;

import samatov.jdbcProject.dto.LabelDTO;
import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.service.LabelService;

import java.util.List;

public class LabelController {

    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    public List<LabelDTO> getAllLabels() {
        return labelService.getAllLabels();
    }

    public LabelDTO getLabelById(Integer id) {
        return labelService.getLabelById(id);
    }

    public LabelDTO saveLabel(LabelDTO label) {
        return labelService.createLabel(label);
    }

    public LabelDTO updateLabel(LabelDTO label) {
        return labelService.updateLabel(label);
    }

    public void deleteLabelById(Integer id) {
        labelService.deleteLabel(id);
    }
}
