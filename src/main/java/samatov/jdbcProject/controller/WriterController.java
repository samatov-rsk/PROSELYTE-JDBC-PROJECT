package samatov.jdbcProject.controller;

import lombok.RequiredArgsConstructor;
import samatov.jdbcProject.model.Writer;
import samatov.jdbcProject.model.Post;
import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.service.WriterService;

import java.util.List;

@RequiredArgsConstructor
public class WriterController {

    private final WriterService writerService;

    public List<Writer> getAllWriter() {
        return writerService.getAllWriter();
    }

    public Writer getWriterById(Integer id) {
        return writerService.getWriterById(id);
    }

    public Writer saveWriter(Writer writer) {
        return writerService.saveWriter(writer);
    }

    public Writer updateWriter(Writer writer) {
        return writerService.updateWriter(writer);
    }

    public void deleteWriterById(Integer id) {
        writerService.deleteWriterById(id);
    }

    public void removePostsByWriterId(Integer writerId) {
        writerService.removePostsByWriterId(writerId);
    }

    public void addPostToWriter(Post post, List<Label> labels, Integer writerId) {
        writerService.addPostToWriter(post, labels, writerId);
    }

}
