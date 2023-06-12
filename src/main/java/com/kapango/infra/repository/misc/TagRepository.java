package com.kapango.infra.repository.misc;

import com.kapango.domain.model.misc.Tag;
import com.kapango.infra.mapper.TagEntityMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TagRepository {

    private final TagDao tagDao;
    private final TagEntityMapper mapper;

    @Autowired
    public TagRepository(TagDao tagDao, TagEntityMapper mapper) {
        this.tagDao = tagDao;
        this.mapper = mapper;
    }

    public List<Tag> getTags() {
        return tagDao.findAll()
            .stream().map(mapper::fromEntity)
            .collect(Collectors.toList());
    }

    public Tag upsert(Tag tag) {
        return mapper.fromEntity(tagDao.save(mapper.toEntity(tag)));
    }

    public void delete(Tag tag) {
        tagDao.deleteById(tag.id());
    }

    public List<Tag> findAllByTagName(String tagName) {
        return tagDao.findByNameContainingIgnoreCase(tagName)
            .stream().map(mapper::fromEntity)
            .collect(Collectors.toList());
    }

    public Optional<Tag> findByTagName(String tagName) {
        return tagDao.findByName(tagName)
            .map(mapper::fromEntity);
    }

    public Optional<Tag> findById(Integer id) {
        return tagDao.findById(id)
            .map(mapper::fromEntity);
    }
}
