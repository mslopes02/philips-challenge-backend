package com.philips.backend.repositories;

import com.philips.backend.domain.Paciente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PacienteRepositoryTest {

    @Autowired
    private PacienteRepository repo;

    @Test
    void shouldFindByEmail() {
        Paciente paciente = new Paciente(1, "John Doe", "john.doe@test.com", "000.111.222-33");
        repo.save(paciente);
        Paciente res = repo.findByEmail("john.doe@test.com");
        assertEquals(res, paciente);
    }
}