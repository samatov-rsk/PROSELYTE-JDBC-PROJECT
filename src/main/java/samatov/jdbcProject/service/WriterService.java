package samatov.jdbcProject.service;

import lombok.RequiredArgsConstructor;
import samatov.jdbcProject.model.Writer;
import samatov.jdbcProject.model.Post;
import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.enums.PostStatus;
import samatov.jdbcProject.repository.WriterRepository;
import samatov.jdbcProject.repository.PostRepository;
import samatov.jdbcProject.exception.NotFoundException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class WriterService {
    private final WriterRepository writerRepository;
    private final PostRepository postRepository;

    public List<Writer> getAllWriter() {
        return writerRepository.findAll();
    }

    public Writer getWriterById(Integer id) {
        return writerRepository.findById(id);
    }

    public Writer saveWriter(Writer writer) {
        return writerRepository.save(writer);
    }

    public Writer updateWriter(Writer writer) {
        return writerRepository.update(writer);
    }

    public void deleteWriterById(Integer id) {
        writerRepository.removeById(id);
    }

    public void removePostsByWriterId(Integer writerId) {
        writerRepository.removePostsByWriterId(writerId);
    }

    public void addPostToWriter(Post post, List<Label> labels, Integer writerId) {
        Writer writer = writerRepository.findById(writerId);
        if (writer == null) {
            throw new NotFoundException("Писатель с указанным id не найден");
        }
        post.setWriter(writer);
        post.setCreated(new Timestamp(System.currentTimeMillis()));
        post.setUpdated(new Timestamp(System.currentTimeMillis()));
        post.setStatus(PostStatus.ACTIVE);
        Post savedPost = postRepository.save(post);
        for (Label label : labels) {
            postRepository.addLabelToPost(savedPost.getId(), label);
        }

        List<Post> posts = writer.getPosts();
        if (posts == null) {
            posts = new ArrayList<>();
        }
        posts.add(savedPost);
        writer.setPosts(posts);
        writerRepository.update(writer);
    }

}
