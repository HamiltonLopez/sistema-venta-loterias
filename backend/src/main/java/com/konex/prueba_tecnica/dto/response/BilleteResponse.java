package com.konex.prueba_tecnica.dto.response;

import java.math.BigDecimal;

public class BilleteResponse {
    private Long id;
    private String numero;
    private BigDecimal precio;
    private String estado;

    public BilleteResponse(Long id, String numero, BigDecimal precio, String estado) {
        this.id = id;
        this.numero = numero;
        this.precio = precio;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public BigDecimal getPrecio() {
        return precio;
    }
    public String getEstado() {
        return estado;
    }

}
