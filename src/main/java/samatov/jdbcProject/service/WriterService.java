package samatov.jdbcProject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import samatov.jdbcProject.dto.LabelDTO;
import samatov.jdbcProject.dto.PostDTO;
import samatov.jdbcProject.dto.WriterDTO;
import samatov.jdbcProject.exception.NotFoundException;
import samatov.jdbcProject.mapper.LabelMapper;
import samatov.jdbcProject.mapper.PostMapper;
import samatov.jdbcProject.mapper.WriterMapper;
import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.model.Post;
import samatov.jdbcProject.model.Writer;
import samatov.jdbcProject.repository.PostRepository;
import samatov.jdbcProject.repository.WriterRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class WriterService {

    private final WriterRepository writerRepository;

    public List<WriterDTO> getAllWriter() {
        log.info("Получение всех писатлей");
        List<WriterDTO> writers = writerRepository.findAll().stream()
                .map(WriterMapper::toWriterDTO)
                .collect(Collectors.toList());
        log.info("Найдено {} писателей", writers.size());
        return writers;
    }

    public WriterDTO getWriterById(Integer id) {
        log.info("Поиск писателя по его id: {}", id);
        WriterDTO writerDTO = WriterMapper.toWriterDTO(writerRepository.findById(id));
        log.info("Найден писатель: {}", writerDTO);
        return writerDTO;
    }

    public WriterDTO saveWriter(WriterDTO writerDTO) {
        log.info("Сохранение нового писателя: {}", writerDTO);
        WriterDTO savedWriterDTO = WriterMapper.toWriterDTO(writerRepository.save(WriterMapper.toWriterEntity(writerDTO)));
        log.info("Писатель сохранен: {}", savedWriterDTO);
        return savedWriterDTO;
    }

    public WriterDTO updateWriter(WriterDTO writerDTO) {
        log.info("Изменение писателя: {}", writerDTO);
        WriterDTO updatedWriterDTO = WriterMapper.toWriterDTO(writerRepository.update(WriterMapper.toWriterEntity(writerDTO)));
        log.info("Писатель изменен: {}", updatedWriterDTO);
        return updatedWriterDTO;
    }

    public void deleteWriterById(Integer id) {
        log.info("Удаление писателя по его id: {}", id);
        writerRepository.removeById(id);
        log.info("Писатель удалан по его id: {}", id);
    }

    public void removePostsByWriterId(Integer writerId) {
        log.info("Удаление постов писателя по его id: {}", writerId);
        writerRepository.removePostsByWriterId(writerId);
        log.info("Посты писателя удалены id: {}", writerId);
    }

    public void addPostWithLabelsToWriter(PostDTO postDTO, Set<LabelDTO> labelDTOs, Integer writerId) {
        log.info("Добавление постов {} с метками для писателя {}", postDTO, writerId);

        Writer writer = writerRepository.findById(writerId);
        if (writer == null) {
            throw new NotFoundException("Writer with id " + writerId + " not found");
        }

        Post post = PostMapper.toPostEntity(postDTO);
        Set<Label> labels = labelDTOs.stream()
                .map(LabelMapper::toLabelEntity)
                .collect(Collectors.toSet());

        writerRepository.addPostWithLabelsToWriter(writerId, post, labels);

        log.info("Добавлены посты {} с метками для писателя {}", postDTO, writerId);
    }

}
