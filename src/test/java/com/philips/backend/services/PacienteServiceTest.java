package com.philips.backend.services;

import com.philips.backend.domain.Paciente;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PacienteServiceTest {
    PacienteService service = mock(PacienteService.class);
    Paciente paciente = new Paciente(1, "John Doe", "john.doe@test.com", "000.111.222-33");
    Paciente pacienteIdNull = new Paciente(null, "John Doe", "john.doe@test.com", "000.111.222-33");

    @Test
    void shouldInsertPatientWithoutId() {
        when(service.insert(paciente)).thenReturn(pacienteIdNull);

        Paciente serviceRes = service.insert(paciente);
        assertNull(serviceRes.getId());
        assertEquals(paciente.getNome(), serviceRes.getNome());
        assertEquals(paciente.getEmail(), serviceRes.getEmail());
        assertEquals(paciente.getCpf(), serviceRes.getCpf());
    }
}