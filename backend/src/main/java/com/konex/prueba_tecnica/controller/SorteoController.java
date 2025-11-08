package com.konex.prueba_tecnica.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.konex.prueba_tecnica.dto.request.CrearSorteoRequest;
import com.konex.prueba_tecnica.dto.response.SorteoResponse;
import com.konex.prueba_tecnica.service.SorteoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sorteos")
public class SorteoController {
    private final SorteoService sorteoService;

    public SorteoController(SorteoService sorteoService) {
        this.sorteoService = sorteoService;
    }

    @PostMapping
    public SorteoResponse crear(@Valid @RequestBody CrearSorteoRequest req) {
        return sorteoService.crearSorteo(req);
    }

    @GetMapping
    public List<?> listar() {
        return sorteoService.listar();
    }
}
