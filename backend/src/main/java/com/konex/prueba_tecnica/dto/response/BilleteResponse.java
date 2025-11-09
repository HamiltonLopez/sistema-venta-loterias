package com.konex.prueba_tecnica.dto.response;

import java.math.BigDecimal;

public class BilleteResponse {
    private Long id;
    private String numero;
    private BigDecimal precio;
    private String estado;
    private SorteoInfo sorteo;

    public BilleteResponse(Long id, String numero, BigDecimal precio, String estado) {
        this.id = id;
        this.numero = numero;
        this.precio = precio;
        this.estado = estado;
    }

    public BilleteResponse(Long id, String numero, BigDecimal precio, String estado, SorteoInfo sorteo) {
        this.id = id;
        this.numero = numero;
        this.precio = precio;
        this.estado = estado;
        this.sorteo = sorteo;
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

    public SorteoInfo getSorteo() {
        return sorteo;
    }

    public void setSorteo(SorteoInfo sorteo) {
        this.sorteo = sorteo;
    }

    public static class SorteoInfo {
        private Long id;
        private String nombre;
        private String fecha;

        public SorteoInfo(Long id, String nombre, String fecha) {
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

        public String getFecha() {
            return fecha;
        }
    }
}
