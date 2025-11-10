package com.konex.prueba_tecnica.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.konex.prueba_tecnica.dto.request.VenderBilleteRequest;
import com.konex.prueba_tecnica.dto.response.BilleteResponse;
import com.konex.prueba_tecnica.entity.Billete;
import com.konex.prueba_tecnica.mapper.BilleteMapper;
import com.konex.prueba_tecnica.service.BilleteService;

@RestController
@RequestMapping("/api/billetes")
public class BilleteController {

    private final BilleteService billeteService;
    private final BilleteMapper billeteMapper;

    public BilleteController(BilleteService billeteService, BilleteMapper billeteMapper) {
        this.billeteService = billeteService;
        this.billeteMapper = billeteMapper;
    }

    @PostMapping("/{id}/vender")
    public BilleteResponse vender(@PathVariable Long id, @RequestBody VenderBilleteRequest req) {
        Billete billete = billeteService.vender(id, req.getClienteId());
        return billeteMapper.toResponse(billete);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<BilleteResponse> billetesCliente(@PathVariable Long clienteId) {
        return billeteService.billetesPorCliente(clienteId)
                .stream()
                .map(billeteMapper::toResponse)
                .toList();
    }
}
