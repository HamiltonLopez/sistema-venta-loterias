package com.konex.prueba_tecnica;

import com.konex.prueba_tecnica.dto.request.CrearSorteoRequest;
import com.konex.prueba_tecnica.dto.response.SorteoResponse;
import com.konex.prueba_tecnica.entity.Sorteo;
import com.konex.prueba_tecnica.mapper.SorteoMapper;
import com.konex.prueba_tecnica.repository.SorteoRepository;
import com.konex.prueba_tecnica.service.SorteoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SorteoServiceTest {

    @Mock
    private SorteoRepository sorteoRepo;

    @Mock
    private SorteoMapper sorteoMapper;

    @InjectMocks
    private SorteoService sorteoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearSorteo_deberiaGuardarYRetornarResponse() {

        CrearSorteoRequest request = new CrearSorteoRequest();
        request.setNombre("Sorteo Navidad");
        request.setFecha(LocalDate.of(2025, 12, 24));
        Sorteo sorteo = new Sorteo();
        sorteo.setId(1L);
        sorteo.setNombre("Sorteo Navidad");
        sorteo.setFecha(LocalDate.of(2025, 12, 24));

        when(sorteoRepo.save(any(Sorteo.class))).thenReturn(sorteo);


        SorteoResponse response = sorteoService.crearSorteo(request);


        assertNotNull(response);
        assertEquals("Sorteo Navidad", response.getNombre());
        verify(sorteoRepo, times(1)).save(any(Sorteo.class));
    }

    @Test
    void listar_deberiaRetornarListaDeSorteosResponse() {

        Sorteo sorteo1 = new Sorteo();
        sorteo1.setId(1L);
        sorteo1.setNombre("Sorteo Navidad");
        sorteo1.setFecha(LocalDate.of(2025, 12, 24));

        Sorteo sorteo2 = new Sorteo();
        sorteo2.setId(2L);
        sorteo2.setNombre("Sorteo Año Nuevo");
        sorteo2.setFecha(LocalDate.of(2026, 1, 1));

        List<Sorteo> sorteos = Arrays.asList(sorteo1, sorteo2);

        when(sorteoRepo.findAll()).thenReturn(sorteos);
        when(sorteoMapper.toResponse(any(Sorteo.class)))
                .thenAnswer(invocation -> {
                    Sorteo s = invocation.getArgument(0);
                    return new SorteoResponse(s.getId(), s.getNombre(), s.getFecha());
                });


        List<SorteoResponse> result = sorteoService.listar();


        assertEquals(2, result.size());
        assertEquals("Sorteo Navidad", result.get(0).getNombre());
        verify(sorteoRepo, times(1)).findAll();
    }

    @Test
    void obtenerPorId_deberiaRetornarSorteoResponse() {

        Sorteo sorteo = new Sorteo();
        sorteo.setId(1L);
        sorteo.setNombre("Sorteo Navidad");
        sorteo.setFecha(LocalDate.of(2025, 12, 24));

        when(sorteoRepo.findById(1L)).thenReturn(Optional.of(sorteo));
        when(sorteoMapper.toResponse(sorteo)).thenReturn(new SorteoResponse(1L, "Sorteo Navidad", sorteo.getFecha()));


        SorteoResponse result = sorteoService.obtenerPorId(1L);


        assertEquals("Sorteo Navidad", result.getNombre());
        verify(sorteoRepo, times(1)).findById(1L);
    }

    @Test
    void obtenerPorId_deberiaLanzarExcepcionSiNoExiste() {

        when(sorteoRepo.findById(99L)).thenReturn(Optional.empty());


        RuntimeException ex = assertThrows(RuntimeException.class, () -> sorteoService.obtenerPorId(99L));
        assertEquals("Sorteo no encontrado", ex.getMessage());
    }
}
