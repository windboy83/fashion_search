package com.fashion_search.domain.h2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@Configuration
@EntityScan(basePackages = {"com.fashion_search.domain.h2.entities"})
@EnableJpaRepositories(basePackages = {"com.fashion_search.domain.h2.repository"})
@RequiredArgsConstructor
public class JpaConfig {
}
