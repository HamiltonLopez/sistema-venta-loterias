package com.konex.prueba_tecnica.dto.request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CrearClienteRequest {
    @NotBlank
    private String nombre;

    @Email
    @NotBlank
    private String correo;

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;

    }
}
