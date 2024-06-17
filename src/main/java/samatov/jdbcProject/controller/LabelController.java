package samatov.jdbcProject.controller;

import lombok.RequiredArgsConstructor;
import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.service.LabelService;

import java.util.List;

public class LabelController {

    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    public List<Label> getAllLabels() {
        return labelService.getAllLabel();
    }

    public Label getLabelById(Integer id) {
        return labelService.getLabelById(id);
    }

    public Label saveLabel(Label label) {
       return labelService.saveLabel(label);
    }

    public Label updateLabel(Label label) {
       return labelService.updateLabel(label);
    }

    public void deleteLabelById(Integer id) {
        labelService.deleteLabelById(id);
    }
}
