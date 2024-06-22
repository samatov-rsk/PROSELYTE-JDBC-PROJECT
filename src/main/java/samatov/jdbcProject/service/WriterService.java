package samatov.jdbcProject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import samatov.jdbcProject.dto.LabelDTO;
import samatov.jdbcProject.dto.PostDTO;
import samatov.jdbcProject.dto.WriterDTO;
import samatov.jdbcProject.enums.PostStatus;
import samatov.jdbcProject.exception.NotFoundException;
import samatov.jdbcProject.mapper.LabelMapper;
import samatov.jdbcProject.mapper.WriterMapper;
import samatov.jdbcProject.repository.PostRepository;
import samatov.jdbcProject.repository.WriterRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class WriterService {

    private final PostService postService;
    private final WriterRepository writerRepository;
    private final PostRepository postRepository;

    public List<WriterDTO> getAllWriter() {
        log.info("Fetching all writers");
        List<WriterDTO> writers = writerRepository.findAll().stream()
                .map(WriterMapper::toWriterDTO)
                .collect(Collectors.toList());
        log.info("Found {} writers", writers.size());
        return writers;
    }

    public WriterDTO getWriterById(Integer id) {
        log.info("Fetching writer by id: {}", id);
        WriterDTO writerDTO = WriterMapper.toWriterDTO(writerRepository.findById(id));
        log.info("Found writer: {}", writerDTO);
        return writerDTO;
    }

    public WriterDTO saveWriter(WriterDTO writerDTO) {
        log.info("Saving new writer: {}", writerDTO);
        WriterDTO savedWriterDTO = WriterMapper.toWriterDTO(writerRepository.save(WriterMapper.toWriterEntity(writerDTO)));
        log.info("Saved writer: {}", savedWriterDTO);
        return savedWriterDTO;
    }

    public WriterDTO updateWriter(WriterDTO writerDTO) {
        log.info("Updating writer: {}", writerDTO);
        WriterDTO updatedWriterDTO = WriterMapper.toWriterDTO(writerRepository.update(WriterMapper.toWriterEntity(writerDTO)));
        log.info("Updated writer: {}", updatedWriterDTO);
        return updatedWriterDTO;
    }

    public void deleteWriterById(Integer id) {
        log.info("Deleting writer by id: {}", id);
        writerRepository.removeById(id);
        log.info("Deleted writer with id: {}", id);
    }

    public void removePostsByWriterId(Integer writerId) {
        log.info("Removing posts by writer id: {}", writerId);
        writerRepository.removePostsByWriterId(writerId);
        log.info("Removed posts by writer id: {}", writerId);
    }

    public void addPostToWriter(PostDTO postDTO, List<LabelDTO> labelDTOs, Integer writerId) {
        log.info("Adding post {} to writer {}", postDTO, writerId);
        WriterDTO writerDTO = getWriterById(writerId);
        if (writerDTO == null) {
            throw new NotFoundException("Writer with id " + writerId + " not found");
        }
////        postDTO.setWriter(writerDTO);
//        postDTO.setCreated(new Timestamp(System.currentTimeMillis()));
//        postDTO.setUpdated(new Timestamp(System.currentTimeMillis()));
//        postDTO.setStatus(PostStatus.ACTIVE.name());
//        PostDTO savedPostDTO = postService.savePost(postDTO, writerId);
//        labelDTOs.forEach(labelDTO -> postRepository.addLabelToPost(savedPostDTO.getId(), LabelMapper.toLabelEntity(labelDTO)));
//        writerDTO.getPosts().add(savedPostDTO);
//        updateWriter(writerDTO);
//        log.info("Added post {} to writer {}", savedPostDTO, writerDTO);
    }
}
