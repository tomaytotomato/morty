package com.kapango.application.controller;


import com.kapango.application.dto.PostmortemDto;
import com.kapango.application.mapper.PostmortemMapper;
import com.kapango.application.usecase.PostmortemUseCase;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostmortemController {

    private final PostmortemUseCase postmortemService;
    private final PostmortemMapper mapper;

    @Autowired
    public PostmortemController(PostmortemUseCase postmortemService, PostmortemMapper mapper) {
        this.postmortemService = postmortemService;
        this.mapper = mapper;
    }

    @GetMapping("/api/postmortems")
    public List<PostmortemDto> getPostmortems() {
        return postmortemService.findAll()
            .stream().map(mapper::fromModel)
            .collect(Collectors.toList());
    }
}
