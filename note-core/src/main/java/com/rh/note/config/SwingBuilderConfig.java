package com.rh.note.config;

import groovy.swing.SwingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwingBuilderConfig {

    @Bean
    public SwingBuilder newSwingBuilder() {
        return new SwingBuilder();
    }

}
