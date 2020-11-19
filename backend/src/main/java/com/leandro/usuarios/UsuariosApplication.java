package com.leandro.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.leandro.usuarios.storage.StorageProperties;

@SpringBootApplication
@EnableWebMvc
@EnableConfigurationProperties(StorageProperties.class)
public class UsuariosApplication implements WebMvcConfigurer{
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// TODO Auto-generated method stub
		registry.addMapping("/**").allowedMethods("GET", "POST");
		WebMvcConfigurer.super.addCorsMappings(registry);
	}

	public static void main(String[] args) {
		SpringApplication.run(UsuariosApplication.class, args);
	}

}
