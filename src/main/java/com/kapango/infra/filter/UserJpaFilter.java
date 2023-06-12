package com.kapango.infra.filter;

import com.kapango.domain.model.user.UserAccountType;
import com.kapango.infra.entity.user.UserEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserJpaFilter implements Specification<UserEntity> {

    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private UserAccountType UserAccountType;
    private String team;
    private String position;

    @Override
    public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        var predicates = new ArrayList<Predicate>();

        if (Objects.nonNull(id)) {
            predicates.add(criteriaBuilder.equal(root.get("id"), id));
        }

        if (Objects.nonNull(username)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), "%" + username.toLowerCase() + "%"));
        }

        if (Objects.nonNull(firstName)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%"));
        }

        if (Objects.nonNull(lastName)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%"));
        }

        if (Objects.nonNull(UserAccountType)) {
            predicates.add(criteriaBuilder.equal(root.get("role"), UserAccountType));
        }

        if (Objects.nonNull(team)) {
            predicates.add(criteriaBuilder.equal(root.get("team"), team));
        }

        if (Objects.nonNull(position)) {
            predicates.add(criteriaBuilder.equal(root.get("position"), position));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}

