package com.konex.prueba_tecnica.dto.request;

import java.math.BigDecimal;
import java.util.List;

public class CrearBilletesRequest {
    private List<BilleteDTO> billetes;

    public List<BilleteDTO> getBilletes() {
        return billetes;
    }

    public void setBilletes(List<BilleteDTO> billetes) {
        this.billetes = billetes;
    }

    public static class BilleteDTO {
        private String numero;
        private BigDecimal precio;

        public String getNumero() {
            return numero;
        }

        public void setNumero(String numero) {
            this.numero = numero;
        }

        public BigDecimal getPrecio() {
            return precio;
        }

        public void setPrecio(BigDecimal precio) {
            this.precio = precio;
        }
    }
}
