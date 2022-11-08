package com.philips.backend.resources;

import com.philips.backend.dto.PacienteNewDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PacienteResourceTest {
    PacienteResource resource = mock(PacienteResource.class);
    PacienteNewDTO paciente = new PacienteNewDTO(
            "John Doe",
            "john.doe@test.com",
            "000.111.222-33",
            "Rua teste",
            "1234",
            "complemento teste",
            "bairro teste",
            "00.111-222",
            1);
    ResponseEntity<Void> response = ResponseEntity.ok().build();

    @Test
    void shouldInsertAndReturn200() {
        when(resource.insert(paciente)).thenReturn(response);
        ResponseEntity<Void> res = resource.insert(paciente);
        assertEquals(HttpStatus.OK, res.getStatusCode());
    }
}