package com.philips.backend.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.philips.backend.domain.Cidade;
import com.philips.backend.domain.Endereco;
import com.philips.backend.domain.Paciente;
import com.philips.backend.dto.EnderecoDTO;
import com.philips.backend.exception.DataIntegrityException;
import com.philips.backend.exception.ObjectNotFoundException;
import com.philips.backend.repositories.EnderecoRepository;
import com.philips.backend.repositories.PacienteRepository;

@Service
public class EnderecoService {

	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Endereco find(Integer id){
		Optional<Endereco> obj = enderecoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Endereco não encontrado! Id: " + id + ", Tipo: " + Endereco.class.getName()));
	}
	
	@Transactional
	public Endereco insert(Endereco obj) {
		obj.setId(null);
		obj = enderecoRepository.save(obj);
		Paciente pac = obj.getPaciente();
		pac.getEnderecos().add(obj);
		pacienteRepository.save(pac);
		return obj;
	}

	public Endereco update(Endereco obj) {
		Endereco newObj = find(obj.getId());
		updateData(newObj, obj);
		return enderecoRepository.save(newObj);
	}

	public Endereco fromDTO(EnderecoDTO objDto) {
		Paciente pac = getPaciente(objDto.getPacienteId());
		return new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), new Cidade(objDto.getCidadeId(), null, null), pac);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			enderecoRepository.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas!");
		}
	}
	
	private void updateData(Endereco newObj, Endereco obj) {
		newObj.setLogradouro(obj.getLogradouro());
		newObj.setNumero(obj.getNumero());
		newObj.setComplemento(obj.getComplemento());
		newObj.setBairro(obj.getBairro());
		newObj.setCep(obj.getCep());
		newObj.setCidade(obj.getCidade());
		newObj.setPaciente(obj.getPaciente());
	}
	
	private Paciente getPaciente(Integer idPaciente) {
		Optional<Paciente> obj = pacienteRepository.findById(idPaciente);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Paciente não encontrado! Id: " + idPaciente + ", Tipo: " + Paciente.class.getName()));
	}
}
