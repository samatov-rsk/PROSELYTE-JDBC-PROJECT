package samatov.jdbcProject.controller;

import lombok.RequiredArgsConstructor;
import samatov.jdbcProject.dto.LabelDTO;
import samatov.jdbcProject.dto.PostDTO;
import samatov.jdbcProject.dto.WriterDTO;
import samatov.jdbcProject.model.Writer;
import samatov.jdbcProject.model.Post;
import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.service.WriterService;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class WriterController {

    private final WriterService writerService;

    public List<WriterDTO> getAllWriter() {
        return writerService.getAllWriter();
    }

    public WriterDTO getWriterById(Integer id) {
        return writerService.getWriterById(id);
    }

    public WriterDTO saveWriter(WriterDTO writer) {
        return writerService.saveWriter(writer);
    }

    public WriterDTO updateWriter(WriterDTO writer) {
        return writerService.updateWriter(writer);
    }

    public void deleteWriterById(Integer id) {
        writerService.deleteWriterById(id);
    }

    public void addPostWithLabelsToWriter(PostDTO postDTO, Set<LabelDTO> labelDTOs, Integer writerId) {
        writerService.addPostWithLabelsToWriter(postDTO, labelDTOs, writerId);
    }

    public void removePostsByWriterId(Integer writerId) {
        writerService.removePostsByWriterId(writerId);
    }

}

