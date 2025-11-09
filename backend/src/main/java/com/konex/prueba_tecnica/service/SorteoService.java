package com.konex.prueba_tecnica.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.konex.prueba_tecnica.dto.request.CrearSorteoRequest;
import com.konex.prueba_tecnica.dto.response.SorteoResponse;
import com.konex.prueba_tecnica.entity.Sorteo;
import com.konex.prueba_tecnica.mapper.SorteoMapper;
import com.konex.prueba_tecnica.repository.SorteoRepository;

@Service
public class SorteoService {

    private final SorteoRepository sorteoRepo;
    private final SorteoMapper sorteoMapper;

    public SorteoService(SorteoRepository sorteoRepo, SorteoMapper sorteoMapper) {
        this.sorteoRepo = sorteoRepo;
        this.sorteoMapper = sorteoMapper;
    }

    public SorteoResponse crearSorteo(CrearSorteoRequest req) {
        Sorteo s = new Sorteo();
        s.setNombre(req.getNombre());
        s.setFecha(req.getFecha());
        sorteoRepo.save(s);
        return new SorteoResponse(s.getId(), s.getNombre(), s.getFecha());
    }

    public List<SorteoResponse> listar() {
        return sorteoRepo.findAll()
                .stream()
                .map(sorteoMapper::toResponse)
                .toList();
    }
    public Sorteo obtenerPorIdEntity(Long id) {
        return sorteoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Sorteo no encontrado"));
    }

    public SorteoResponse obtenerPorId(Long id) {
        Sorteo sorteo = sorteoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Sorteo no encontrado"));
        return sorteoMapper.toResponse(sorteo);
    }

}
