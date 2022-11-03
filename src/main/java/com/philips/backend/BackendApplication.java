package com.philips.backend;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.philips.backend.domain.Cidade;
import com.philips.backend.domain.Endereco;
import com.philips.backend.domain.Estado;
import com.philips.backend.domain.Paciente;
import com.philips.backend.repositories.CidadeRepository;
import com.philips.backend.repositories.EnderecoRepository;
import com.philips.backend.repositories.EstadoRepository;
import com.philips.backend.repositories.PacienteRepository;


@SpringBootApplication
public class BackendApplication implements CommandLineRunner	{
	
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private PacienteRepository pacienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		Estado est3 = new Estado(null, "Santa Catarina");
		Estado est4 = new Estado(null, "Pernambuco");
	
		Cidade c1 = new Cidade(null, "Belo Horizonte", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Blumenau", est3);
		Cidade c4 = new Cidade(null, "Garanhuns", est4);
	
		estadoRepository.saveAll(Arrays.asList(est1, est2, est3, est4));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3, c4));
		
		Paciente pac1 = new Paciente(null, "João Sousa", "joao@email.com", "36378912377");
		Paciente pac2 = new Paciente(null, "Maria Silva", "maria@email.com", "38837142080");
		Paciente pac3 = new Paciente(null, "José Campos", "jose@email.com", "21973955059");
		Paciente pac4 = new Paciente(null, "Ana Oliveira", "ana@email.com", "27639310067");
		
		Endereco e1 = new Endereco(null, "Rua Um", "12", "AP 3", "Casa Caiada", "22113321", c1, pac1);
		Endereco e2 = new Endereco(null, "Rua Dois", "23", "CASA", "Casa Amarela", "13322233", c2, pac2);
		Endereco e3 = new Endereco(null, "Rua Tres", "34", "ZONA RURAL", "Centro", "12548965", c3, pac3);
		Endereco e4 = new Endereco(null, "Rua Quatro", "45", "CASA", "Outro Lugar", "84579658", c4, pac4);
		Endereco e5 = new Endereco(null, "Rua Cinco", "56", "AP 17", "Industrial", "54895478", c4, pac4);
		
		pac1.getEnderecos().addAll(Arrays.asList(e1));
		pac2.getEnderecos().addAll(Arrays.asList(e2));
		pac3.getEnderecos().addAll(Arrays.asList(e3));
		pac4.getEnderecos().addAll(Arrays.asList(e4, e5));
		
		pacienteRepository.saveAll(Arrays.asList(pac1, pac2, pac3, pac4));
		enderecoRepository.saveAll(Arrays.asList(e1, e2, e3, e4, e5));
	}

}
