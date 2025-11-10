package com.konex.prueba_tecnica;

import com.konex.prueba_tecnica.dto.request.CrearBilletesRequest;
import com.konex.prueba_tecnica.dto.response.BilleteResponse;
import com.konex.prueba_tecnica.entity.Billete;
import com.konex.prueba_tecnica.entity.Cliente;
import com.konex.prueba_tecnica.entity.EstadoBillete;
import com.konex.prueba_tecnica.entity.Sorteo;
import com.konex.prueba_tecnica.mapper.BilleteMapper;
import com.konex.prueba_tecnica.repository.BilleteRepository;
import com.konex.prueba_tecnica.repository.ClienteRepository;
import com.konex.prueba_tecnica.repository.SorteoRepository;
import com.konex.prueba_tecnica.service.BilleteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BilleteServiceTest {

    @Mock
    private BilleteRepository billeteRepo;

    @Mock
    private ClienteRepository clienteRepo;

    @Mock
    private SorteoRepository sorteoRepo;

    @Mock
    private BilleteMapper billeteMapper;

    @InjectMocks
    private BilleteService billeteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearBilletesParaSorteo_deberiaCrearBilletesCorrectamente() {
        // Arrange
        Long sorteoId = 1L;
        Sorteo sorteo = new Sorteo();
        sorteo.setId(sorteoId);
        sorteo.setNombre("Sorteo de Navidad");

        CrearBilletesRequest request = new CrearBilletesRequest();
        CrearBilletesRequest.BilleteDTO billeteDTO = new CrearBilletesRequest.BilleteDTO();
        billeteDTO.setNumero("0001");
        billeteDTO.setPrecio(new BigDecimal("10.00"));
        request.setBilletes(Arrays.asList(billeteDTO));

        when(sorteoRepo.findById(sorteoId)).thenReturn(Optional.of(sorteo));
        when(billeteRepo.findBySorteoId(sorteoId)).thenReturn(Collections.emptyList());

     
        billeteService.crearBilletesParaSorteo(sorteoId, request);

 
        verify(sorteoRepo, times(1)).findById(sorteoId);
        verify(billeteRepo, times(1)).findBySorteoId(sorteoId);
        verify(billeteRepo, times(1)).save(any(Billete.class));
    }

    @Test
    void crearBilletesParaSorteo_deberiaLanzarExcepcionSiSorteoNoExiste() {
    
        Long sorteoId = 999L;
        CrearBilletesRequest request = new CrearBilletesRequest();
        request.setBilletes(Collections.emptyList());

        when(sorteoRepo.findById(sorteoId)).thenReturn(Optional.empty());

     
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            billeteService.crearBilletesParaSorteo(sorteoId, request);
        });

        assertEquals("Sorteo no existe", exception.getMessage());
        verify(sorteoRepo, times(1)).findById(sorteoId);
        verify(billeteRepo, never()).save(any(Billete.class));
    }

    @Test
    void crearBilletesParaSorteo_deberiaLanzarExcepcionSiBilleteYaExiste() {

        Long sorteoId = 1L;
        Sorteo sorteo = new Sorteo();
        sorteo.setId(sorteoId);

        Billete billeteExistente = new Billete();
        billeteExistente.setNumero("0001");
        billeteExistente.setPrecio(new BigDecimal("10.00"));

        CrearBilletesRequest request = new CrearBilletesRequest();
        CrearBilletesRequest.BilleteDTO billeteDTO = new CrearBilletesRequest.BilleteDTO();
        billeteDTO.setNumero("0001");
        billeteDTO.setPrecio(new BigDecimal("10.00"));
        request.setBilletes(Arrays.asList(billeteDTO));

        when(sorteoRepo.findById(sorteoId)).thenReturn(Optional.of(sorteo));
        when(billeteRepo.findBySorteoId(sorteoId)).thenReturn(Arrays.asList(billeteExistente));

  
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            billeteService.crearBilletesParaSorteo(sorteoId, request);
        });

        assertEquals("Ya existe un billete con el número 0001 en este sorteo", exception.getMessage());
        verify(billeteRepo, never()).save(any(Billete.class));
    }

    @Test
    void generarBilletesAutomaticamente_deberiaCrearTodosBilletesDisponibles() {

        Long sorteoId = 1L;
        Integer numeroCifras = 2;
        BigDecimal precio = new BigDecimal("5.00");

        Sorteo sorteo = new Sorteo();
        sorteo.setId(sorteoId);

        when(sorteoRepo.findById(sorteoId)).thenReturn(Optional.of(sorteo));
        when(billeteRepo.findBySorteoId(sorteoId)).thenReturn(Collections.emptyList());

  
        billeteService.generarBilletesAutomaticamente(sorteoId, numeroCifras, precio);

   
        verify(sorteoRepo, times(1)).findById(sorteoId);
     
        verify(billeteRepo, times(100)).save(any(Billete.class));
    }

    @Test
    void generarBilletesAutomaticamente_deberiaLanzarExcepcionSiHayDuplicados() {
    
        Long sorteoId = 1L;
        Integer numeroCifras = 2;
        BigDecimal precio = new BigDecimal("5.00");

        Sorteo sorteo = new Sorteo();
        sorteo.setId(sorteoId);

        Billete billeteExistente = new Billete();
        billeteExistente.setNumero("00");

        when(sorteoRepo.findById(sorteoId)).thenReturn(Optional.of(sorteo));
        when(billeteRepo.findBySorteoId(sorteoId)).thenReturn(Arrays.asList(billeteExistente));

   
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            billeteService.generarBilletesAutomaticamente(sorteoId, numeroCifras, precio);
        });

        assertTrue(exception.getMessage().contains("números ya existían y se omitieron"));
    }

    @Test
    void vender_deberiaVenderBilleteCorrectamente() {
  
        Long billeteId = 1L;
        Long clienteId = 1L;

        Billete billete = new Billete();
        billete.setId(billeteId);
        billete.setNumero("0001");
        billete.setEstado(EstadoBillete.DISPONIBLE);

        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        cliente.setNombre("Juan Pérez");

        when(billeteRepo.findById(billeteId)).thenReturn(Optional.of(billete));
        when(clienteRepo.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(billeteRepo.save(any(Billete.class))).thenReturn(billete);

     
        Billete resultado = billeteService.vender(billeteId, clienteId);

   
        assertNotNull(resultado);
        assertEquals(EstadoBillete.VENDIDO, resultado.getEstado());
        assertEquals(cliente, resultado.getCliente());
        verify(billeteRepo, times(1)).findById(billeteId);
        verify(clienteRepo, times(1)).findById(clienteId);
        verify(billeteRepo, times(1)).save(billete);
    }

    @Test
    void vender_deberiaLanzarExcepcionSiBilleteNoExiste() {
     
        Long billeteId = 999L;
        Long clienteId = 1L;

        when(billeteRepo.findById(billeteId)).thenReturn(Optional.empty());

    
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            billeteService.vender(billeteId, clienteId);
        });

        assertEquals("Billete no encontrado", exception.getMessage());
        verify(clienteRepo, never()).findById(anyLong());
        verify(billeteRepo, never()).save(any(Billete.class));
    }

    @Test
    void vender_deberiaLanzarExcepcionSiBilleteYaVendido() {
   
        Long billeteId = 1L;
        Long clienteId = 1L;

        Billete billete = new Billete();
        billete.setId(billeteId);
        billete.setEstado(EstadoBillete.VENDIDO);

        when(billeteRepo.findById(billeteId)).thenReturn(Optional.of(billete));

     
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            billeteService.vender(billeteId, clienteId);
        });

        assertEquals("Billete ya vendido", exception.getMessage());
        verify(clienteRepo, never()).findById(anyLong());
        verify(billeteRepo, never()).save(any(Billete.class));
    }

    @Test
    void vender_deberiaLanzarExcepcionSiClienteNoExiste() {
   
        Long billeteId = 1L;
        Long clienteId = 999L;

        Billete billete = new Billete();
        billete.setId(billeteId);
        billete.setEstado(EstadoBillete.DISPONIBLE);

        when(billeteRepo.findById(billeteId)).thenReturn(Optional.of(billete));
        when(clienteRepo.findById(clienteId)).thenReturn(Optional.empty());

   
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            billeteService.vender(billeteId, clienteId);
        });

        assertEquals("Cliente no existe", exception.getMessage());
        verify(billeteRepo, never()).save(any(Billete.class));
    }

    @Test
    void billetesPorCliente_deberiaRetornarListaDeBilletes() {

        Long clienteId = 1L;

        Billete b1 = new Billete();
        b1.setId(1L);
        b1.setNumero("0001");

        Billete b2 = new Billete();
        b2.setId(2L);
        b2.setNumero("0002");

        List<Billete> billetes = Arrays.asList(b1, b2);

        when(billeteRepo.findByClienteId(clienteId)).thenReturn(billetes);


        List<Billete> resultado = billeteService.billetesPorCliente(clienteId);


        assertEquals(2, resultado.size());
        assertEquals("0001", resultado.get(0).getNumero());
        assertEquals("0002", resultado.get(1).getNumero());
        verify(billeteRepo, times(1)).findByClienteId(clienteId);
    }

    @Test
    void listarPorSorteo_deberiaRetornarListaDeBilleteResponse() {

        Long sorteoId = 1L;

        Sorteo sorteo = new Sorteo();
        sorteo.setId(sorteoId);

        Billete b1 = new Billete();
        b1.setId(1L);
        b1.setNumero("0001");

        Billete b2 = new Billete();
        b2.setId(2L);
        b2.setNumero("0002");

        List<Billete> billetes = Arrays.asList(b1, b2);

        BilleteResponse r1 = mock(BilleteResponse.class);
        BilleteResponse r2 = mock(BilleteResponse.class);

        when(sorteoRepo.findById(sorteoId)).thenReturn(Optional.of(sorteo));
        when(billeteRepo.findBySorteoId(sorteoId)).thenReturn(billetes);
        when(billeteMapper.toResponse(b1)).thenReturn(r1);
        when(billeteMapper.toResponse(b2)).thenReturn(r2);

 
        List<BilleteResponse> resultado = billeteService.listarPorSorteo(sorteoId);

  
        assertEquals(2, resultado.size());
        verify(sorteoRepo, times(1)).findById(sorteoId);
        verify(billeteRepo, times(1)).findBySorteoId(sorteoId);
        verify(billeteMapper, times(2)).toResponse(any(Billete.class));
    }

    @Test
    void listarPorSorteo_deberiaLanzarExcepcionSiSorteoNoExiste() {
        // Arrange
        Long sorteoId = 999L;

        when(sorteoRepo.findById(sorteoId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            billeteService.listarPorSorteo(sorteoId);
        });

        assertEquals("Sorteo no existe", exception.getMessage());
        verify(billeteRepo, never()).findBySorteoId(anyLong());
    }
}
