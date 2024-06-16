package com.alura.topico.controller;

import com.alura.topico.usuario.DatosRegistrarUsuario;
import com.alura.topico.usuario.DatosRespuestaUsuario;
import com.alura.topico.usuario.Usuario;
import com.alura.topico.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Creación de Usuarios", description = "Se registran los usuarios para la utilización del FORO")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    @Operation(
            summary = "Regitro de los usuarios en la BD",
            description = "Se registran los usuarios con el email y clave y devuelve el id del usuario",
            tags = {"post"}
    )
    public ResponseEntity<DatosRespuestaUsuario> registrarUsuario(@RequestBody @Valid DatosRegistrarUsuario datos) {
        String claveCodificada = passwordEncoder.encode(datos.clave());
        Usuario usuario = new Usuario(datos.email(), claveCodificada);
        usuarioRepository.save(usuario);
        DatosRespuestaUsuario datosRespuestaUsuario = DatosRespuestaUsuario.of(usuario.getId(), usuario.getEmail(), usuario.getClave());

        return new ResponseEntity<>(datosRespuestaUsuario, HttpStatus.CREATED);

    }

    @GetMapping("/{email}")
    @Operation(
            summary = "Obtiene un usuario por su email",
            description = "Devuelve el usuario registrado en la BD correspondiente al email proporcionado y así obtiene el ID",
            tags = {"get"}
    )
    public ResponseEntity<DatosRespuestaUsuario> obtenerUsuarioPorEmail(@PathVariable String email) {
        Usuario usuario = (Usuario) usuarioRepository.findByEmail(email);
        DatosRespuestaUsuario datosRespuestaUsuario = DatosRespuestaUsuario.of(usuario.getId(), usuario.getEmail(), usuario.getClave());
        return ResponseEntity.ok(datosRespuestaUsuario);
    }

}
