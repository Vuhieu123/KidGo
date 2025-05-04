package com.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = {"com", "com.shared"}) // Quét toàn bộ các package cần thiết
@EnableJpaRepositories("com.shared.db.repo") // Chỉ định chính xác package chứa repository
@EntityScan("com.shared.db.entities") // Đảm bảo chính xác package chứa entity
@EnableTransactionManagement(proxyTargetClass = true)
@EnableAsync
@EnableWebSecurity
@EnableJpaAuditing
@EnableScheduling
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}

