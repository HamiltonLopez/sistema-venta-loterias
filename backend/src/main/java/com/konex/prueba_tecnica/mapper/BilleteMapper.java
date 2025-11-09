package com.konex.prueba_tecnica.mapper;

import org.springframework.stereotype.Component;

import com.konex.prueba_tecnica.dto.response.BilleteResponse;
import com.konex.prueba_tecnica.entity.Billete;

@Component
public class BilleteMapper {
    public BilleteResponse toResponse(Billete b) {
        return new BilleteResponse(
                b.getId(),
                b.getNumero(),
                b.getPrecio(),
                b.getEstado().name()
        );
    }
}
