package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.services.CorreoService;
import com.example.services.DepartamentoService;
import com.example.services.EmpleadoService;
import com.example.services.TelefonoService;

@SpringBootApplication
public class SpringMvcEmpresaApplication implements CommandLineRunner{

	@Autowired
	private DepartamentoService departamentoService;

	@Autowired
	private EmpleadoService empleadoService;

	@Autowired
	private TelefonoService telefonoService;

	@Autowired
	private CorreoService correoService;

	public static void main(String[] args) {
		SpringApplication.run(SpringMvcEmpresaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'run'");
	}

}
