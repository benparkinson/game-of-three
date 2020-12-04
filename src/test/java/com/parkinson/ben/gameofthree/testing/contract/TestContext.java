package com.parkinson.ben.gameofthree.testing.contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.net.URI;

import static java.util.Objects.requireNonNull;

@Configuration
@PropertySource("/config.properties")
public class TestContext {

    @Autowired
    private Environment environment;

    @Bean
    public URI serverUri() {
        return URI.create(requireNonNull(environment.getProperty("test-uri")));
    }
}
