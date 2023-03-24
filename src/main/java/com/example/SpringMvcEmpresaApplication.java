package com.example;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.entities.Correo;
import com.example.entities.Departamento;
import com.example.entities.Empleado;
import com.example.entities.Telefono;
import com.example.entities.Empleado.Genero;
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
		
		// Crear los departamentos
		departamentoService.save(
			Departamento.builder()
			.nombre("Informatica")
			.build()
		);

		departamentoService.save(
			Departamento.builder()
			.nombre("RRHH")
			.build()
		);

		// Crear unos empleados asociados a los departamentos

		empleadoService.save(
			Empleado.builder()
			.id(1)
			.nombre("Mara")
			.apellidos("Romero")
			.fechaAlta(LocalDate.of(2018, Month.APRIL, 22))
			.genero(Genero.MUJER)
			.departamento(departamentoService.findById(1))
			.build()
		);

		empleadoService.save(
			Empleado.builder()
			.id(2)
			.nombre("Victor")
			.apellidos("Machado")
			.fechaAlta(LocalDate.of(2010, Month.JANUARY, 2))
			.genero(Genero.HOMBRE)
			.departamento(departamentoService.findById(2))
			.build()
		);

		// Creamos unos telefonos asociados a los empleados

		telefonoService.save(
			Telefono.builder()
			.id(1)
			.numero("678943568")
			.empleado(empleadoService.findById(1))
			.build()
		);

		telefonoService.save(
			Telefono.builder()
			.id(2)
			.numero("612345678")
			.empleado(empleadoService.findById(2))
			.build()
		);

		// Creamos unos correos asociados a los empleados

		correoService.save(
			Correo.builder()
			.id(1)
			.email("mromero@mromero.com")
			.empleado(empleadoService.findById(1))
			.build()
		);

		correoService.save(
			Correo.builder()
			.id(2)
			.email("vmachado@vmachado.com")
			.empleado(empleadoService.findById(2))
			.build()
		);







	}




}
