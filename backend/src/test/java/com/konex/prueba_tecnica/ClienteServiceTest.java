package com.konex.prueba_tecnica;

import com.konex.prueba_tecnica.dto.request.CrearClienteRequest;
import com.konex.prueba_tecnica.dto.response.ClienteResponse;
import com.konex.prueba_tecnica.entity.Cliente;
import com.konex.prueba_tecnica.repository.ClienteRepository;
import com.konex.prueba_tecnica.service.ClienteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepo;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crear_deberiaGuardarYRetornarClienteResponse() {

        CrearClienteRequest request = new CrearClienteRequest();
        request.setNombre("Carlos López");
        request.setCorreo("carlos@gmail.com");
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Carlos López");
        cliente.setCorreo("carlos@gmail.com");

        when(clienteRepo.save(any(Cliente.class))).thenReturn(cliente);


        ClienteResponse response = clienteService.crear(request);

      
        assertNotNull(response);
        assertEquals("Carlos López", response.getNombre());
        assertEquals("carlos@gmail.com", response.getCorreo());
        verify(clienteRepo, times(1)).save(any(Cliente.class));
    }

    @Test
    void listar_deberiaRetornarListaDeClientesResponse() {
        
        Cliente c1 = new Cliente();
        c1.setId(1L);
        c1.setNombre("Ana Pérez");
        c1.setCorreo("ana@gmail.com");

        Cliente c2 = new Cliente();
        c2.setId(2L);
        c2.setNombre("Luis Gómez");
        c2.setCorreo("luis@gmail.com");

        List<Cliente> clientes = Arrays.asList(c1, c2);

        when(clienteRepo.findAll()).thenReturn(clientes);

       
        List<ClienteResponse> result = clienteService.listar();

        
        assertEquals(2, result.size());
        assertEquals("Ana Pérez", result.get(0).getNombre());
        assertEquals("Luis Gómez", result.get(1).getNombre());
        verify(clienteRepo, times(1)).findAll();
    }
}
