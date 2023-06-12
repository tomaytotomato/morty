package com.kapango.application.usecase;

import com.kapango.domain.model.misc.MortyConfig;
import java.util.Optional;

public interface MortyConfigUseCase extends CrudUseCase<MortyConfig> {

    Optional<MortyConfig> findByConfigGroupName(String configGroupName);

}
