package samatov.jdbcProject.dto;

import lombok.Builder;
import lombok.Data;
import samatov.jdbcProject.enums.PostStatus;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
public class PostDTO {
    private Integer id;
    private String content;
    private Timestamp created;
    private Timestamp updated;
    private PostStatus status;
    private List<LabelDTO> labels;
}
