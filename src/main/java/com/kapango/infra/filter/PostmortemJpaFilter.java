package com.kapango.infra.filter;

import com.kapango.infra.entity.postmortem.PostmortemEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.ZonedDateTime;
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
public class PostmortemJpaFilter implements Specification<PostmortemEntity> {

    private Integer id;
    private String reference;
    private Long version;
    private ZonedDateTime created;
    private ZonedDateTime updated;
    private ZonedDateTime completed;
    private String name;
    private String description;
    private String status;
    private String source;
    private String severity;
    private String rootCause;
    private String tags;

    @Override
    public Predicate toPredicate(Root<PostmortemEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        var predicates = new ArrayList<Predicate>();

        if (Objects.nonNull(id)) {
            predicates.add(criteriaBuilder.equal(root.get("id"), id));
        }

        if (Objects.nonNull(reference)) {
            predicates.add(criteriaBuilder.equal(root.get("humanReadableId"), reference));
        }

        if (Objects.nonNull(version)) {
            predicates.add(criteriaBuilder.equal(root.get("version"), version));
        }

        if (Objects.nonNull(created)) {
            predicates.add(criteriaBuilder.equal(root.get("created"), created));
        }

        if (Objects.nonNull(updated)) {
            predicates.add(criteriaBuilder.equal(root.get("updated"), updated));
        }

        if (Objects.nonNull(completed)) {
            predicates.add(criteriaBuilder.equal(root.get("completed"), completed));
        }

        if (Objects.nonNull(name)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if (Objects.nonNull(description)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + description.toLowerCase() + "%"));
        }

        if (Objects.nonNull(status)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + status.toLowerCase() + "%"));
        }

        if (Objects.nonNull(source)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("source")), "%" + source.toLowerCase() + "%"));
        }

        if (Objects.nonNull(severity)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("severity")), "%" + severity.toLowerCase() + "%"));
        }

        if (Objects.nonNull(rootCause)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("rootCause")), "%" + rootCause.toLowerCase() + "%"));
        }

        if (Objects.nonNull(tags)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("tags")), "%" + tags.toLowerCase() + "%"));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}

