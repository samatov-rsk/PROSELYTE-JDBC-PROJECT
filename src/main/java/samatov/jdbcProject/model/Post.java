package samatov.jdbcProject.model;

import lombok.*;
import samatov.jdbcProject.enums.PostStatus;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Post {

    private Integer id;
    private String content;
    private Timestamp created;
    private Timestamp updated;
    private List<Label> labels;
    private PostStatus status;
    @ToString.Exclude
    private Writer writer;

}
