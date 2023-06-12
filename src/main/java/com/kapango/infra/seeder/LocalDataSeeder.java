package com.kapango.infra.seeder;


import static com.kapango.infra.entity.user.UserAccountType.USER;

import com.kapango.infra.entity.misc.TagEntity;
import com.kapango.infra.entity.postmortem.PostmortemEntity;
import com.kapango.infra.entity.postmortem.PostmortemEntityStatus;
import com.kapango.infra.entity.user.UserEntity;
import com.kapango.infra.repository.incident.IncidentDao;
import com.kapango.infra.repository.misc.MortyConfigDao;
import com.kapango.infra.repository.misc.TagDao;
import com.kapango.infra.repository.postmortem.PostmortemDao;
import com.kapango.infra.repository.user.UserDao;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Adds some sample data for local development use
 */

@Profile("local")
@Component
@Slf4j(topic = "Local Data Seeder")
public class LocalDataSeeder {

    private final PostmortemDao postmortemDao;
    private final IncidentDao incidentDao;
    private final UserDao userDao;

    private final MortyConfigDao configDao;

    private final TagDao tagDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LocalDataSeeder(PostmortemDao postmortemDao, IncidentDao incidentDao, UserDao userDao, MortyConfigDao configDao,
                           TagDao tagDao, PasswordEncoder passwordEncoder) {
        this.postmortemDao = postmortemDao;
        this.incidentDao = incidentDao;
        this.userDao = userDao;
        this.configDao = configDao;
        this.tagDao = tagDao;
        this.passwordEncoder = passwordEncoder;

        if (postmortemDao.count() == 0) {
            log.info("Adding local data!");
            init();
        } else {
            log.info("Already has local data!");
        }
    }

    public void init() {

        var user = UserEntity.builder()
            .username("bobby@tables.com")
            .accountType(USER)
            .firstName("Bobby")
            .lastName("Tables")
            .position("Chaos Monkey")
            .team("Foo Inc.")
            .hashedPassword(passwordEncoder.encode("password"))
            .build();


        var tags = List.of(new TagEntity("SQL Injection"), new TagEntity("PHP"), new TagEntity("Login"));

        var postmortem = PostmortemEntity.builder()
            .author(user)
            .name("We've been pwned")
            .reference("WHOOPS-123")
            .severity("Level 1")
            .description("A SQL Injection managed to drop our entire database")
            .rootCause("SQL Injection on Login page")
            .source("Slack")
            .tags(new HashSet<>(tags))
            .status(PostmortemEntityStatus.CREATED)
            .created(ZonedDateTime.now())
            .createdByHuman(true)
            .build();

        postmortemDao.save(postmortem);
    }
}
