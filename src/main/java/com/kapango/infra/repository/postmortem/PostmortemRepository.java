package com.kapango.infra.repository.postmortem;

import com.kapango.domain.filter.PostmortemFilter;
import com.kapango.domain.model.postmortem.Postmortem;
import com.kapango.infra.mapper.PostmortemEntityMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
public class PostmortemRepository {

    private final PostmortemDao postmortemDao;
    private final PostmortemEntityMapper mapper;

    @Autowired
    public PostmortemRepository(PostmortemDao postmortemDao, PostmortemEntityMapper mapper) {
        this.postmortemDao = postmortemDao;
        this.mapper = mapper;
    }

    public List<Postmortem> findAll() {
        return postmortemDao.findAll()
            .stream().map(mapper::fromEntity)
            .collect(Collectors.toList());
    }

    public List<Postmortem> findAll(PageRequest pageRequest, PostmortemFilter filter) {
        return postmortemDao.findAll(mapper.toJpaFilter(filter), pageRequest)
            .map(mapper::fromEntity)
            .stream().collect(Collectors.toList());
    }

    public List<Postmortem> findAll(PageRequest pageRequest) {
        return postmortemDao.findAll(pageRequest)
            .map(mapper::fromEntity)
            .stream().collect(Collectors.toList());
    }

    public Postmortem upsert(Postmortem postmortem) {
        var upsertedPostmortem = postmortemDao.save(mapper.toEntity(postmortem));
        return mapper.fromEntity(upsertedPostmortem);
    }

    public Optional<Postmortem> findById(Integer postmortemId) {
        return postmortemDao.findById(postmortemId)
            .map(mapper::fromEntity);
    }

    public void delete(Postmortem model) {
        postmortemDao.deleteById(model.id());
    }
}
