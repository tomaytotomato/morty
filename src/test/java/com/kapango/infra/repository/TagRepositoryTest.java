package com.kapango.infra.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kapango.domain.model.misc.Tag;
import com.kapango.infra.entity.misc.TagEntity;
import com.kapango.infra.mapper.TagEntityMapper;
import com.kapango.infra.repository.misc.TagDao;
import com.kapango.infra.repository.misc.TagRepository;
import java.time.ZonedDateTime;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TagRepositoryTest {

    private TagRepository tagRepository;

    @Mock
    private TagDao tagDao;

    private TagEntityMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = TagEntityMapper.INSTANCE;
        MockitoAnnotations.openMocks(this);
        tagRepository = new TagRepository(tagDao, mapper);
    }

    @Test
    public void getTags_whenCalled_thenReturnListOfTags() {
        var tagEntities = Arrays.asList(TagEntity.builder().id(1).name("Foo").build(),
            TagEntity.builder().id(2).name("Bar").build(),
            TagEntity.builder().id(3).name("Bobby").build());

        when(tagDao.findAll()).thenReturn(tagEntities);

        var tags = tagRepository.getTags();

        verify(tagDao).findAll();

        assertThat(tags).hasSize(3)
            .extracting("name")
            .containsExactly("Foo", "Bar", "Bobby");
    }

    @Test
    public void upsert_whenCalled_thenReturnTag() {
        var id = 123;
        var name = "Save me";
        var tag = Tag.builder().name(name).build();
        var entityBuilder = TagEntity.builder()
            .created(ZonedDateTime.now())
            .updated(ZonedDateTime.now())
            .name(name);
        var unsavedEntity = entityBuilder.build();

        when(tagDao.save(unsavedEntity)).thenReturn(entityBuilder.id(123).build());

        var result = tagRepository.upsert(tag);

        verify(tagDao).save(unsavedEntity);

        assertThat(result.id()).isEqualTo(id);
        assertThat(result.name()).isEqualTo(name);
    }

    @Test
    public void delete_whenCalled_thenDeleteTag() {
        var tag = Tag.builder().id(1)
            .name("Deleteme")
            .build();

        tagRepository.delete(tag);

        verify(tagDao).deleteById(tag.id());
    }
}
