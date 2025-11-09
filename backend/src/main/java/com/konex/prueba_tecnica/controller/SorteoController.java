package com.konex.prueba_tecnica.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.konex.prueba_tecnica.dto.request.CrearBilletesRequest;
import com.konex.prueba_tecnica.dto.request.CrearSorteoRequest;
import com.konex.prueba_tecnica.dto.response.BilleteResponse;
import com.konex.prueba_tecnica.dto.response.SorteoResponse;
import com.konex.prueba_tecnica.service.BilleteService;
import com.konex.prueba_tecnica.service.SorteoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sorteos")
public class SorteoController {
    private final SorteoService sorteoService;
    private final BilleteService billeteService;

    public SorteoController(SorteoService sorteoService, BilleteService billeteService) {
        this.billeteService = billeteService;
        this.sorteoService = sorteoService;
    }

    @PostMapping("/{id}/billetes")
    public ResponseEntity<?> crearBilletes(
            @PathVariable Long id,
            @RequestBody CrearBilletesRequest request) {

        billeteService.crearBilletesParaSorteo(id, request);
        return ResponseEntity.ok("Billetes creados correctamente");
    }

    @PostMapping
    public SorteoResponse crear(@Valid @RequestBody CrearSorteoRequest req) {
        return sorteoService.crearSorteo(req);
    }

    @GetMapping
    public List<SorteoResponse> listar() {
        return sorteoService.listar();
    }

    @GetMapping("/{id}")
    public SorteoResponse obtenerPorId(@PathVariable Long id) {
        return sorteoService.obtenerPorId(id);
    }

    @GetMapping("/{id}/billetes")
    public List<BilleteResponse> listarBilletes(@PathVariable Long id) {
        return billeteService.listarPorSorteo(id);
    }

}
