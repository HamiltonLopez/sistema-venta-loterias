package com.konex.prueba_tecnica.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.konex.prueba_tecnica.dto.request.CrearBilletesRequest;
import com.konex.prueba_tecnica.dto.request.CrearSorteoRequest;
import com.konex.prueba_tecnica.dto.request.GenerarBilletesRequest;
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
            @Valid @RequestBody CrearBilletesRequest request) {
Map<String, String> response = new HashMap<>();
        try {
            billeteService.crearBilletesParaSorteo(id, request);
            response.put("mensaje", "Billetes creados correctamente");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/billetes/generar")
    public ResponseEntity<?> generarBilletes(
            @PathVariable Long id,
            @Valid @RequestBody GenerarBilletesRequest request) {
                
                 Map<String, String> response = new HashMap<>();
        try {
            billeteService.generarBilletesAutomaticamente(id, request.getNumeroCifras(), request.getPrecio());
            response.put("mensaje", "Billetes generados correctamente");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (IllegalStateException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
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
