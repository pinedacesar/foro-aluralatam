package com.alura.topico.topico;

import com.alura.topico.infra.errores.ValidacionDeIntegridad;
import com.alura.topico.infra.security.TopicoRepository;
import com.alura.topico.topico.validaciones.ValidadorDeTopicosExistentes;
import com.alura.topico.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    List<ValidadorDeTopicosExistentes> validador;


    public DatosDetalleTopico crear(DatosRegistroTopico datos) throws Exception {
        if (datos.idUsuario() != null && !usuarioRepository.existsById(datos.idUsuario())) {
            throw new ValidacionDeIntegridad("Usuario inexistente");
        }

        validador.forEach(v -> v.validar(datos));

        var usuario = usuarioRepository.findById(datos.idUsuario()).get();
        var topico = new Topico(
                datos.titulo(),
                datos.mensaje(),
                datos.status(),
                usuario,
                datos.nombreCurso()
        );
        topicoRepository.save(topico);
        return new DatosDetalleTopico(topico);
    }

}
