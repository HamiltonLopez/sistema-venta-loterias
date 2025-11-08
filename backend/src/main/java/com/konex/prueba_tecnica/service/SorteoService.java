package com.konex.prueba_tecnica.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.konex.prueba_tecnica.dto.request.CrearSorteoRequest;
import com.konex.prueba_tecnica.dto.response.SorteoResponse;
import com.konex.prueba_tecnica.entity.Sorteo;
import com.konex.prueba_tecnica.repository.SorteoRepository;

@Service
public class SorteoService {

    private final SorteoRepository sorteoRepo;

    public SorteoService(SorteoRepository sorteoRepo) {
        this.sorteoRepo = sorteoRepo;
    }

    public SorteoResponse crearSorteo(CrearSorteoRequest req) {
        Sorteo s = new Sorteo();
        s.setNombre(req.getNombre());
        s.setFecha(req.getFecha());
        sorteoRepo.save(s);
        return new SorteoResponse(s.getId(), s.getNombre(), s.getFecha());
    }

    public List<Sorteo> listar() {
        return sorteoRepo.findAll();
    }
}
