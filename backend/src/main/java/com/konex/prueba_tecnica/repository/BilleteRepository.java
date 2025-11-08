package com.konex.prueba_tecnica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.konex.prueba_tecnica.entity.Billete;
import com.konex.prueba_tecnica.entity.EstadoBillete;
@Repository
public interface BilleteRepository extends JpaRepository<Billete, Long> {

    List<Billete> findBySorteoIdAndEstado(Long sorteoId, EstadoBillete estado);

    List<Billete> findByClienteId(Long clienteId);
}