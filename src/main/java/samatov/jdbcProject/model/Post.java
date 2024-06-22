package samatov.jdbcProject.model;

import lombok.*;
import samatov.jdbcProject.enums.PostStatus;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;
    private Timestamp created;
    private Timestamp updated;

    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @ManyToMany
    @JoinTable(
            name = "post_labels",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    private List<Label> labels;
}
