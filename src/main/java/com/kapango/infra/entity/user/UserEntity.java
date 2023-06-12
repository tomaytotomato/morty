package com.kapango.infra.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.ZonedDateTime;
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
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    @EqualsAndHashCode.Include
    private Integer id;
    @EqualsAndHashCode.Include
    private String username;
    @Version
    private Long version;
    @CreationTimestamp
    private ZonedDateTime created;
    @UpdateTimestamp
    private ZonedDateTime updated;
    private String firstName;
    private String lastName;
    private String hashedPassword;
    @Enumerated(EnumType.STRING)
    private UserAccountType accountType;
    private String team;
    private String position;
    @Lob
    @Column(length = 1000000)
    private byte[] profilePicture;
}
