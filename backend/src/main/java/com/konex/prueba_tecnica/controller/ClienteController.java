package com.konex.prueba_tecnica.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.konex.prueba_tecnica.dto.request.CrearClienteRequest;
import com.konex.prueba_tecnica.dto.response.ClienteResponse;
import com.konex.prueba_tecnica.service.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

     private final ClienteService clienteService;
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ClienteResponse crear(@Valid @RequestBody CrearClienteRequest req) {
        return clienteService.crear(req);
    }

    @GetMapping
    public List<ClienteResponse> listar() {
        return clienteService.listar();
    }
}
