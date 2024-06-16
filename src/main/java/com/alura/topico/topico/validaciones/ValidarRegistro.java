package com.alura.topico.topico.validaciones;

import com.alura.topico.infra.security.TopicoRepository;
import com.alura.topico.topico.DatosRegistroTopico;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarRegistro implements ValidadorDeTopicosExistentes {

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validar(DatosRegistroTopico datos) {
        var titulo = datos.titulo();
        var mensaje = datos.mensaje();

        var validarRegistro = topicoRepository.findByTituloAndMensaje(titulo, mensaje);

        if (validarRegistro.isPresent()) {
            throw new ValidationException("El tópico ya existe");
        }
    }

}
