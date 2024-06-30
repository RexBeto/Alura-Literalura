package com.navasan.Literalura_Desafio.repository;

import com.navasan.Literalura_Desafio.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Autor findByNombre(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento >= :fecha")
    List<Autor> autorVivoDesde(@Param("fecha") String fecha);
}
