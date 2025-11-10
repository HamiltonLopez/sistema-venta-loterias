package com.konex.prueba_tecnica.repository;

import com.konex.prueba_tecnica.entity.Sorteo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SorteoRepository extends JpaRepository<Sorteo, Long>{


}
