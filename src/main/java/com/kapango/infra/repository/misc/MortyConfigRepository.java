package com.kapango.infra.repository.misc;

import com.kapango.application.usecase.MortyConfigUseCase;
import com.kapango.domain.model.misc.MortyConfig;
import com.kapango.infra.mapper.MortyConfigEntityMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MortyConfigRepository implements MortyConfigUseCase {

    private final MortyConfigDao configDao;
    private final MortyConfigEntityMapper mapper;

    @Autowired
    public MortyConfigRepository(MortyConfigDao configDao, MortyConfigEntityMapper mapper) {
        this.configDao = configDao;
        this.mapper = mapper;
    }

    @Override
    public List<MortyConfig> findAll() {
        return configDao.findAll()
            .stream().map(mapper::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<MortyConfig> findById(Integer id) {
        return configDao.findById(id)
            .map(mapper::fromEntity);
    }

    @Override
    public MortyConfig upsert(MortyConfig model) {
        var toUpsert = mapper.toEntity(model);
        return mapper.fromEntity(configDao.save(toUpsert));
    }

    @Override
    public void delete(MortyConfig model) {
        configDao.deleteById(model.id());
    }

    @Override
    public Optional<MortyConfig> findByConfigGroupName(String configGroupName) {
        return configDao.findByConfigGroupNameContainingIgnoreCase(configGroupName)
            .map(mapper::fromEntity);
    }
}
