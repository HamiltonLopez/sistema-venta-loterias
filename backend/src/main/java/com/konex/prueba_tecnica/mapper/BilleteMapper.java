package com.konex.prueba_tecnica.mapper;

import org.springframework.stereotype.Component;

import com.konex.prueba_tecnica.dto.response.BilleteResponse;
import com.konex.prueba_tecnica.entity.Billete;

@Component
public class BilleteMapper {
    public BilleteResponse toResponse(Billete b) {
        BilleteResponse.SorteoInfo sorteoInfo = null;
        if (b.getSorteo() != null) {
            sorteoInfo = new BilleteResponse.SorteoInfo(
                    b.getSorteo().getId(),
                    b.getSorteo().getNombre(),
                    b.getSorteo().getFecha().toString()
            );
        }
        return new BilleteResponse(
                b.getId(),
                b.getNumero(),
                b.getPrecio(),
                b.getEstado().name(),
                sorteoInfo
        );
    }
}
