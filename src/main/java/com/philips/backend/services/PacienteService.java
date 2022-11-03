package com.philips.backend.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.philips.backend.domain.Cidade;
import com.philips.backend.domain.Endereco;
import com.philips.backend.domain.Paciente;
import com.philips.backend.dto.PacienteDTO;
import com.philips.backend.dto.PacienteNewDTO;
import com.philips.backend.exception.DataIntegrityException;
import com.philips.backend.repositories.EnderecoRepository;
import com.philips.backend.repositories.PacienteRepository;
import com.philips.backend.exception.ObjectNotFoundException;

@Service
public class PacienteService {

	@Autowired
	private PacienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Paciente find(Integer id){
		Optional<Paciente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Paciente.class.getName()));
	}
	
	@Transactional
	public Paciente insert(Paciente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	public Paciente update(Paciente obj) {
		Paciente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas!");
		}
	}

	public List<Paciente> findAll() {
		return repo.findAll();
	}
	
	public Page<Paciente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Paciente fromDTO(PacienteDTO objDto) {
		return new Paciente(objDto.getId(), objDto.getNome(), objDto.getEmail(), objDto.getCpf());
	}
	
	public Paciente fromDTO(PacienteNewDTO objDto) {
		Paciente cli = new Paciente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpf());
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), new Cidade(objDto.getCidadeId(), null, null), cli);
		
		cli.getEnderecos().add(end);
		
		return cli;
	}
	
	private void updateData(Paciente newObj, Paciente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
