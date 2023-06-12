package com.kapango.domain.service;

import com.kapango.application.usecase.TagUseCase;
import com.kapango.domain.model.misc.Tag;
import com.kapango.infra.repository.misc.TagRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TagService implements TagUseCase {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.getTags();
    }

    @Override
    public Optional<Tag> findById(Integer id) {
        return tagRepository.findById(id);
    }

    @Override
    public Tag upsert(Tag tag) throws UserExistsException {
        var result = tagRepository.findByTagName(tag.name());
        if (result.isPresent()) {
            throw new UserExistsException("Tag with name : " + tag.name() + " already exists!");
        } else {
            return tagRepository.upsert(tag);
        }
    }

    @Override
    public void delete(Tag tag) {
        tagRepository.delete(tag);
    }

    @Override
    public List<Tag> findAllByTagName(String tagName) {
        if (Objects.isNull(tagName)) {
            return tagRepository.getTags();
        } else {
            return tagRepository.findAllByTagName(tagName);
        }
    }
}
