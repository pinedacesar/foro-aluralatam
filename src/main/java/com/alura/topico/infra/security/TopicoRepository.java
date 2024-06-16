package com.alura.topico.infra.security;

import com.alura.topico.topico.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    @Query("SELECT t FROM Topico t WHERE FUNCTION('YEAR', t.fecha) = :year")
    Page<Topico> findByFechaYear(@Param("year") int year, Pageable pageable);

    Optional<Topico> findByTituloAndMensaje(String titulo, String mensaje);
}
