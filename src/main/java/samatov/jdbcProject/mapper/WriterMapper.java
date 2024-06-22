package samatov.jdbcProject.mapper;

import samatov.jdbcProject.dto.PostDTO;
import samatov.jdbcProject.dto.WriterDTO;
import samatov.jdbcProject.model.Post;
import samatov.jdbcProject.model.Writer;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class WriterMapper {

    public static WriterDTO toWriterDTO(Writer writer) {
        List<PostDTO> postDTOs = writer.getPosts().stream()
                .map(PostMapper::toPostDTO)
                .collect(Collectors.toList());
        return WriterDTO.builder()
                .id(writer.getId())
                .firstName(writer.getFirstName())
                .lastName(writer.getLastName())
                .posts(postDTOs)
                .build();
    }

    public static Writer toWriterEntity(WriterDTO writerDTO) {
        return Writer.builder()
                .id(writerDTO.getId())
                .firstName(writerDTO.getFirstName())
                .lastName(writerDTO.getLastName())
                .posts(writerDTO.getPosts() != null
                        ? writerDTO.getPosts().stream()
                        .map(PostMapper::toPostEntity)
                        .collect(Collectors.toList())
                        : Collections.emptyList())
                .build();
    }
}
