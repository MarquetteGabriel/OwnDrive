package com.pamarg.fileapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value={"classpath:application.properties"})
public class JpaConfig {
}
