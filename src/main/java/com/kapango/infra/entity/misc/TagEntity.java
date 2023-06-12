package com.kapango.infra.entity.misc;

import com.kapango.infra.entity.postmortem.PostmortemEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.ZonedDateTime;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tags")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    @EqualsAndHashCode.Include
    private Integer id;
    @EqualsAndHashCode.Include
    private String name;
    @Version
    private Long version;
    @CreationTimestamp
    private ZonedDateTime created;
    @UpdateTimestamp
    private ZonedDateTime updated;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
        name = "postmortem_tags",
        joinColumns = @JoinColumn(name = "postmortem_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<PostmortemEntity> postmortems;

    public TagEntity(String name) {
        this.name = name;
    }
}
