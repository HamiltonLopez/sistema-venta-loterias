package com.konex.prueba_tecnica.service;

import org.springframework.stereotype.Service;

import com.konex.prueba_tecnica.dto.request.CrearClienteRequest;
import com.konex.prueba_tecnica.dto.response.ClienteResponse;
import com.konex.prueba_tecnica.entity.Cliente;
import com.konex.prueba_tecnica.repository.ClienteRepository;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepo;

    public ClienteService(ClienteRepository clienteRepo) {
        this.clienteRepo = clienteRepo;
    }

    public ClienteResponse crear(CrearClienteRequest req) {
        Cliente c = new Cliente();
        c.setNombre(req.getNombre());
        c.setCorreo(req.getCorreo());
        clienteRepo.save(c);
        return new ClienteResponse(c.getId(), c.getNombre(), c.getCorreo());
    }
}
