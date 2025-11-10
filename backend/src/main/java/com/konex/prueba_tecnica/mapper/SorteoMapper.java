package com.konex.prueba_tecnica.mapper;

import org.springframework.stereotype.Component;

import com.konex.prueba_tecnica.dto.response.SorteoResponse;
import com.konex.prueba_tecnica.entity.Sorteo;

@Component
public class SorteoMapper {

    public SorteoResponse toResponse(Sorteo sorteo) {
        return new SorteoResponse(
                sorteo.getId(),
                sorteo.getNombre(),
                sorteo.getFecha()
        );
    }
    
}
