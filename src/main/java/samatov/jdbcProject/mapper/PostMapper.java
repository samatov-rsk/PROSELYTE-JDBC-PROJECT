package samatov.jdbcProject.mapper;

import samatov.jdbcProject.dto.LabelDTO;
import samatov.jdbcProject.dto.PostDTO;
import samatov.jdbcProject.enums.PostStatus;
import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.model.Post;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PostMapper {

    public static PostDTO toPostDTO(Post post) {
        Set<LabelDTO> labelDtoList = post.getLabels() != null ? post.getLabels().stream()
                .map(LabelMapper::toLabelDTO)
                .collect(Collectors.toSet()) : Collections.emptySet();

        return PostDTO.builder()
                .id(post.getId())
                .content(post.getContent())
                .created(post.getCreated())
                .updated(post.getUpdated())
                .status(post.getStatus())
                .labels(labelDtoList)
                .build();
    }

    public static Post toPostEntity(PostDTO postDTO) {
        Set<Label> labels = postDTO.getLabels() != null ? postDTO.getLabels().stream()
                .map(LabelMapper::toLabelEntity)
                .collect(Collectors.toSet()) : Collections.emptySet();

        return Post.builder()
                .id(postDTO.getId())
                .content(postDTO.getContent())
                .created(postDTO.getCreated())
                .updated(postDTO.getUpdated())
                .status(postDTO.getStatus())
                .labels(labels)
                .build();
    }
}
