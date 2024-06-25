package com.armando.personas.DTOS;

public class ResponseNotFoundDTO {

    private Integer codigo;

    private String message;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseNotFoundDTO(Integer codigo, String message) {
        this.codigo = codigo;
        this.message = message;
    }
}
