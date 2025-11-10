package com.konex.prueba_tecnica.dto.response;

import java.time.LocalDate;

public class SorteoResponse {
    private Long id;
    private String nombre;
    private LocalDate fecha;

    public SorteoResponse(Long id, String nombre, LocalDate fecha) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }
}
