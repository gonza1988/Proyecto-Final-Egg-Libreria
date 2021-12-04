package com.example.demo;

import com.example.demo.servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DemoApplication {
    
    @Autowired
    private ServicioUsuario servicioUsuario;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

        @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(servicioUsuario)
                .passwordEncoder(new BCryptPasswordEncoder());

    }
}
