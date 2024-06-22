package samatov.jdbcProject.mapper;

import samatov.jdbcProject.dto.LabelDTO;
import samatov.jdbcProject.model.Label;

public class LabelMapper {

    public static LabelDTO toLabelDTO(Label label) {
        return LabelDTO.builder()
                .id(label.getId())
                .name(label.getName())
                .build();
    }

    public static Label toLabelEntity(LabelDTO labelDTO) {
        return Label.builder()
                .id(labelDTO.getId())
                .name(labelDTO.getName())
                .build();
    }
}
