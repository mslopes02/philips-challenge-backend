package com.philips.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.philips.backend.domain.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
	
	@Transactional(readOnly=true)
	Paciente findByEmail(String email);

}
