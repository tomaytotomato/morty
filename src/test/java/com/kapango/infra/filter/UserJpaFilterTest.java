package com.kapango.infra.filter;

import static org.mockito.Mockito.mock;

import com.kapango.infra.entity.user.UserEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserJpaFilterTest {

    private Root<UserEntity> root;
    private CriteriaBuilder criteriaBuilder;

    @BeforeEach
    public void setup() {
        root = mock(Root.class);
        criteriaBuilder = mock(CriteriaBuilder.class);
    }

}