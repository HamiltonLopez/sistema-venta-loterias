package com.konex.prueba_tecnica.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.konex.prueba_tecnica.entity.Billete;
import com.konex.prueba_tecnica.entity.Cliente;
import com.konex.prueba_tecnica.entity.EstadoBillete;
import com.konex.prueba_tecnica.repository.BilleteRepository;
import com.konex.prueba_tecnica.repository.ClienteRepository;
import org.springframework.transaction.annotation.Transactional;
@Service
public class BilleteService {

    private final BilleteRepository billeteRepo;
    private final ClienteRepository clienteRepo;

    public BilleteService(BilleteRepository billeteRepo, ClienteRepository clienteRepo) {
        this.billeteRepo = billeteRepo;
        this.clienteRepo = clienteRepo;
    }

    @Transactional
    public Billete vender(Long billeteId, Long clienteId) {

        Billete b = billeteRepo.findById(billeteId)
                .orElseThrow(() -> new IllegalArgumentException("Billete no encontrado"));

        if (b.getEstado() == EstadoBillete.VENDIDO) {
            throw new IllegalStateException("Billete ya vendido");
        }

        Cliente c = clienteRepo.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no existe"));

        b.setCliente(c);
        b.setEstado(EstadoBillete.VENDIDO);
        return billeteRepo.save(b);
    }

    public List<Billete> billetesPorCliente(Long clienteId) {
        return billeteRepo.findByClienteId(clienteId);
    }
    
}
