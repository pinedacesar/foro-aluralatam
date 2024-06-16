package com.alura.topico.controller;

import com.alura.topico.infra.security.TopicoRepository;
import com.alura.topico.topico.*;
import com.alura.topico.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Controller
@ResponseBody
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
@SuppressWarnings("all")
public class TopicoController {
    @Autowired
    private TopicoService topicoService;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    @Transactional
    @Operation(
            summary = "Crea los topicos que quiere el usuario",
            description = "Se crea el tópico ingresando id del usuario, titulo, mensaje, nombre del curso, status",
            tags = {"post"}
    )
    public ResponseEntity crear(@RequestBody @Valid DatosRegistroTopico datos,
                                UriComponentsBuilder uriComponentsBuilder) throws Exception {
        var response = topicoService.crear(datos);

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(url).body(datos);

    }

    @GetMapping
    @Operation(
            summary = "Devuelve todos los tópicos",
            description = "Ordena todos los tópicos por fecha.",
            tags = {"get"}
    )
    public ResponseEntity<Page<DatosDetalleTopico>> listar(@PageableDefault(size = 10, sort = "fecha",
            direction = Sort.Direction.ASC) Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findAll(paginacion).map(DatosDetalleTopico::new));
    }

    @GetMapping("/filtro-curso")
    @Operation(
            summary = "Devuelve los tópicos ordenados por el curso",
            description = "Trae todos los tópicos de la base de datos ordenados por curso.",
            tags = {"get"}
    )
    public ResponseEntity<Page<DatosDetalleTopico>> listarPorNombreDeCurso(@PageableDefault(size = 10,
            sort = "nombreCurso", direction = Sort.Direction.ASC) Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findAll(paginacion).map(DatosDetalleTopico::new));
    }

    @GetMapping("/filtro-year")
    @Operation(
            summary = "Devuelve los tópicos ordenados por el año",
            description = "Ordena todos los tópicos por curso.",
            tags = {"get"}
    )
    public ResponseEntity<Page<DatosDetalleTopico>> listarPorAnio(
            @RequestParam int year,
            @PageableDefault(size = 10, sort = "fecha", direction = Sort.Direction.ASC) Pageable paginacion) {
        Page<Topico> topicos = topicoRepository.findByFechaYear(year, paginacion);
        Page<DatosDetalleTopico> datosListadoTopicos = topicos.map(DatosDetalleTopico::new);
        return ResponseEntity.ok(datosListadoTopicos);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Devuelve un tópico por id",
            description = "Trae el tópico por el ID indicado.",
            tags = {"getById"}
    )
    public ResponseEntity<DatosRespuestaTopico> retornaDatosTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        if (topico == null) {
            throw new ValidationException("Id de tópico no existe en la base de datos.");
        }
        var datosTopico = new DatosRespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getStatus(),
                topico.getAutor().getUsername(),
                topico.getNombreCurso(),
                topico.getFecha());
        return ResponseEntity.ok(datosTopico);
    }

    @PutMapping()
    @Transactional
    @Operation(
            summary = "Actualiza un tópico",
            description = "Permite actualizar título, mensaje, nombre del curso y status",
            tags = {"put"}
    )

    public ResponseEntity actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
        Topico topico = topicoRepository.getReferenceById(datosActualizarTopico.id());

        topico.actualizarDatos(datosActualizarTopico);
        return ResponseEntity.ok(
                new DatosDetalleTopico(
                        topico.getId(), topico.getAutor().getId(), topico.getTitulo(),
                        topico.getMensaje(), topico.getNombreCurso(), topico.getFecha()
                )
        );
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(
            summary = "Elimina el tópico indicado por el ID",
            tags = {"delete"}
    )
    public ResponseEntity eliminar(@PathVariable Long id) {
        Topico topico = topicoRepository.findById(id).orElse(null);
        if (topico == null) {
            throw new ValidationException("Id de tópico no existe en la base de datos.");
        }
        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
