package com.kapango.application.usecase;

import com.kapango.domain.model.misc.Tag;
import java.util.List;

public interface TagUseCase extends CrudUseCase<Tag> {

    List<Tag> findAllByTagName(String tagName);
}
