package com.alura.topico.topico;

import java.time.LocalDateTime;

public record DatosDetalleTopico(
        Long id,
        Long idUsuario,
        String titulo,
        String mensaje,
        String nombreCurso,
        LocalDateTime fecha
) {
    public DatosDetalleTopico(Topico topico) {
        this(topico.getId(), topico.getAutor().getId(), topico.getTitulo(),
                topico.getMensaje(), topico.getNombreCurso(), topico.getFecha());
    }
}
