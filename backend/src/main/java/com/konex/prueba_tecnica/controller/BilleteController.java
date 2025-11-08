package com.konex.prueba_tecnica.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.konex.prueba_tecnica.dto.request.VenderBIlleteRequest;
import com.konex.prueba_tecnica.entity.Billete;
import com.konex.prueba_tecnica.service.BilleteService;

@RestController
@RequestMapping("/api/billetes")
public class BilleteController {
    
    private final BilleteService billeteService;
    
      public BilleteController(BilleteService billeteService) {
        this.billeteService = billeteService;
    }

    @PostMapping("/{id}/vender")
    public Billete vender(@PathVariable Long id, @RequestBody VenderBIlleteRequest req) {
        return billeteService.vender(id, req.getClienteId());
    }


    @GetMapping("/cliente/{clienteId}")
    public List<Billete> billetesCliente(@PathVariable Long clienteId) {
        return billeteService.billetesPorCliente(clienteId);
    }
}
