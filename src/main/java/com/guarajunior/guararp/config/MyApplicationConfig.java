package com.guarajunior.guararp.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyApplicationConfig {
	@Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
