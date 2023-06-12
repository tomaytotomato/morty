package com.kapango.application.usecase;


import com.kapango.domain.filter.PostmortemFilter;
import com.kapango.domain.model.postmortem.Postmortem;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface PostmortemUseCase extends CrudUseCase<Postmortem> {

    List<Postmortem> findAll(PageRequest pageRequest, PostmortemFilter filter);
    List<Postmortem> findAll(PageRequest pageRequest);

}
