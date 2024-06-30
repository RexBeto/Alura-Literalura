package com.navasan.Literalura_Desafio;

import com.navasan.Literalura_Desafio.principal.Principal;
import com.navasan.Literalura_Desafio.repository.AutorRepository;
import com.navasan.Literalura_Desafio.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraDesafioApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository repository;
	@Autowired
	private AutorRepository autorRepository;
	public static void main(String[] args) {
		SpringApplication.run(LiteraluraDesafioApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(autorRepository, repository);

		principal.mostrarMenu();
	}
}
