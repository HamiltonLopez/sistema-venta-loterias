package com.konex.prueba_tecnica.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.konex.prueba_tecnica.dto.request.CrearBilletesRequest;
import com.konex.prueba_tecnica.dto.response.BilleteResponse;
import com.konex.prueba_tecnica.entity.Billete;
import com.konex.prueba_tecnica.entity.Cliente;
import com.konex.prueba_tecnica.entity.EstadoBillete;
import com.konex.prueba_tecnica.entity.Sorteo;
import com.konex.prueba_tecnica.mapper.BilleteMapper;
import com.konex.prueba_tecnica.repository.BilleteRepository;
import com.konex.prueba_tecnica.repository.ClienteRepository;
import com.konex.prueba_tecnica.repository.SorteoRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class BilleteService {

    private final BilleteRepository billeteRepo;
    private final ClienteRepository clienteRepo;
    private final SorteoRepository sorteoRepo;
    private final BilleteMapper billeteMapper;

    public BilleteService(BilleteRepository billeteRepo, ClienteRepository clienteRepo, SorteoRepository sorteoRepo, BilleteMapper billeteMapper) {
        this.billeteRepo = billeteRepo;
        this.clienteRepo = clienteRepo;
        this.sorteoRepo = sorteoRepo;
        this.billeteMapper = billeteMapper;
    }

    @Transactional
    public void crearBilletesParaSorteo(Long sorteoId, CrearBilletesRequest request) {

        Sorteo sorteo = sorteoRepo.findById(sorteoId)
                .orElseThrow(() -> new IllegalArgumentException("Sorteo no existe"));

        for (CrearBilletesRequest.BilleteDTO dto : request.getBilletes()) {
            boolean existe = billeteRepo.findBySorteoId(sorteoId)
                    .stream()
                    .anyMatch(b -> b.getNumero().equals(dto.getNumero()));
            
            if (existe) {
                throw new IllegalArgumentException("Ya existe un billete con el número " + dto.getNumero() + " en este sorteo");
            }

            Billete b = new Billete();
            b.setNumero(dto.getNumero());
            b.setPrecio(dto.getPrecio());
            b.setEstado(EstadoBillete.DISPONIBLE);
            b.setSorteo(sorteo);
            billeteRepo.save(b);
        }
    }

    @Transactional
    public void generarBilletesAutomaticamente(Long sorteoId, Integer numeroCifras, java.math.BigDecimal precio) {
        Sorteo sorteo = sorteoRepo.findById(sorteoId)
                .orElseThrow(() -> new IllegalArgumentException("Sorteo no existe"));

        int min = 0;
        int max = (int) Math.pow(10, numeroCifras) - 1;
        
        List<Billete> billetesExistentes = billeteRepo.findBySorteoId(sorteoId);
        Set<String> numerosExistentes = billetesExistentes.stream()
                .map(Billete::getNumero)
                .collect(Collectors.toSet());

        int creados = 0;
        int duplicados = 0;

        for (int i = min; i <= max; i++) {
            String numero = String.format("%0" + numeroCifras + "d", i);
            

            if (numerosExistentes.contains(numero)) {
                duplicados++;
                continue;
            }

            Billete b = new Billete();
            b.setNumero(numero);
            b.setPrecio(precio);
            b.setEstado(EstadoBillete.DISPONIBLE);
            b.setSorteo(sorteo);
            billeteRepo.save(b);
            creados++;
        }

        if (duplicados > 0) {
            throw new IllegalStateException("Se crearon " + creados + " billetes. " + duplicados + " números ya existían y se omitieron.");
        }
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

    public List<BilleteResponse> listarPorSorteo(Long sorteoId) {
        sorteoRepo.findById(sorteoId)
                .orElseThrow(() -> new IllegalArgumentException("Sorteo no existe"));
        return billeteRepo.findBySorteoId(sorteoId)
                .stream()
                .map(billeteMapper::toResponse)
                .toList();
    }

}
