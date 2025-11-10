package com.konex.prueba_tecnica.dto.response;

public class ClienteResponse {
    private Long id;
    private String nombre;
    private String correo;

    public ClienteResponse(Long id, String nombre, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }
}
