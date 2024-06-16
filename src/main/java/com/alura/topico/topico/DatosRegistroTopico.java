package com.alura.topico.topico;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroTopico(
        @NotNull(message = "{idUsuarui.obligatorio}")
        Long idUsuario,
        @NotBlank(message = "{titulo.obligatorio}")
        String titulo,
        @NotBlank(message = "{mensaje.obligatorio}")
        String mensaje,
        @NotBlank(message = "curso.obligatorio}")
        String nombreCurso,
        @NotNull(message = "Estado de tópico es obligatorio")
        @Valid
        Estado status
) {
}
