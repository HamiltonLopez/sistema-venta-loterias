package com.konex.prueba_tecnica.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class GenerarBilletesRequest {
    
    @NotNull(message = "El número de cifras es requerido")
    @Min(value = 1, message = "El número de cifras debe ser al menos 1")
    private Integer numeroCifras;
    
    @NotNull(message = "El precio es requerido")
    @Positive(message = "El precio debe ser positivo")
    private BigDecimal precio;

    public Integer getNumeroCifras() {
        return numeroCifras;
    }

    public void setNumeroCifras(Integer numeroCifras) {
        this.numeroCifras = numeroCifras;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
}

