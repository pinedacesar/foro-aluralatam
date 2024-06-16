package com.alura.topico.topico;

public record DatosActualizarTopico(
        Long id,
        String titulo,
        String mensaje,
        String nombreCurso,
        Estado status
) {
}
