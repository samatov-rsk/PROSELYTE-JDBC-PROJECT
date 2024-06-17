package samatov.jdbcProject.model;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Writer {

    private Integer id;
    private String firstName;
    private String lastName;
    private List<Post> posts;

}
