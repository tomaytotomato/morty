package com.kapango.domain.service;

import com.kapango.application.usecase.PostmortemUseCase;
import com.kapango.domain.filter.PostmortemFilter;
import com.kapango.domain.model.postmortem.Postmortem;
import com.kapango.infra.mapper.PostmortemEntityMapper;
import com.kapango.infra.repository.postmortem.PostmortemRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PostmortemService implements PostmortemUseCase {

    private final PostmortemRepository postmortemRepository;
    private final PostmortemEntityMapper mapper;

    @Autowired
    public PostmortemService(PostmortemRepository postmortemRepository, PostmortemEntityMapper mapper) {
        this.postmortemRepository = postmortemRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Postmortem> findAll() {
        return postmortemRepository.findAll();
    }

    @Override
    public List<Postmortem> findAll(PageRequest pageRequest, PostmortemFilter filter) {
        return postmortemRepository.findAll(pageRequest, filter);
    }

    @Override
    public List<Postmortem> findAll(PageRequest pageRequest) {
        return postmortemRepository.findAll(pageRequest);
    }

    @Override
    public Postmortem upsert(Postmortem postmortem) {
        return postmortemRepository.upsert(postmortem);
    }

    @Override
    public void delete(Postmortem model) {
        postmortemRepository.delete(model);
    }

    @Override
    public Optional<Postmortem> findById(Integer id) {
        return postmortemRepository.findById(id);
    }

}
