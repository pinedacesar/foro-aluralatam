package com.alura.topico.usuario;

public record DatosRespuestaUsuario(
        Long id,
        String email,
        String clave
) {
    public static DatosRespuestaUsuario of(Long id, String email, String clave) {
        return new DatosRespuestaUsuario(id, email, "******");
    }
}
