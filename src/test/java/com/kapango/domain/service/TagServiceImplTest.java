package com.kapango.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kapango.application.usecase.TagUseCase;
import com.kapango.domain.model.misc.Tag;
import com.kapango.infra.repository.misc.TagRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    private TagUseCase tagService;

    @Mock
    private TagRepository tagRepository;

    @BeforeEach
    public void setUp() {
        tagService = new TagService(tagRepository);
    }

    @Test
    public void findAll_whenTagsExist_thenReturnListOfTags() {
        List<Tag> tags = Arrays.asList(Tag.builder().id(1).name("tag1").build(), Tag.builder().id(2).name("tag2").build());
        when(tagRepository.getTags()).thenReturn(tags);

        var result = tagService.findAll();

        assertThat(result).isEqualTo(tags);

        verify(tagRepository).getTags();
    }

    @Test
    public void upsert_whenTagDoesNotExist_thenReturnTag() throws UserExistsException {
        var tag = Tag.builder().id(1).name("newTag").build();

        when(tagRepository.findByTagName("newTag")).thenReturn(Optional.empty());
        when(tagRepository.upsert(tag)).thenReturn(tag);

        var result = tagService.upsert(tag);

        assertThat(result).isEqualTo(tag);

        verify(tagRepository).findByTagName("newTag");
        verify(tagRepository).upsert(tag);
    }

    @Test
    public void upsert_whenTagAlreadyExists_thenThrowTagExistsException() {
        var tag = Tag.builder().id(1).name("existingTag").build();

        when(tagRepository.findByTagName("existingTag")).thenReturn(Optional.of(tag));

        assertThatThrownBy(() -> tagService.upsert(tag)).isInstanceOf(UserExistsException.class);

        verify(tagRepository).findByTagName("existingTag");
    }

    @Test
    public void delete_whenCalled_thenDeleteTag() {
        var tag = Tag.builder().id(1).name("tagToDelete").build();

        tagService.delete(tag);

        verify(tagRepository).delete(tag);
    }

    @Test
    public void findAllByTagName_whenTagNameIsNull_thenReturnAllTags() {
        List<Tag> tags = Arrays.asList(Tag.builder().id(1).name("tag1").build(), Tag.builder().id(2).name("tag2").build());
        when(tagRepository.getTags()).thenReturn(tags);

        var result = tagService.findAllByTagName(null);

        assertThat(result).isEqualTo(tags);

        verify(tagRepository).getTags();
    }

    @Test
    public void findAllByTagName_whenMatchingTagsExist_thenReturnListOfMatchingTags() {
        var tagName = "tag";
        List<Tag> matchingTags = Arrays.asList(Tag.builder().id(1).name("Punch").build(), Tag.builder().id(2).name("Judy").build());
        when(tagRepository.findAllByTagName(tagName)).thenReturn(matchingTags);

        var result = tagService.findAllByTagName(tagName);

        assertThat(result).isEqualTo(matchingTags);

        verify(tagRepository).findAllByTagName(tagName);
    }
}