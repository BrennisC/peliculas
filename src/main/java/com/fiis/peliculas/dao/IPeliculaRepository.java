package com.fiis.peliculas.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fiis.peliculas.entities.Pelicula;


@Repository
public interface  IPeliculaRepository  extends  PagingAndSortingRepository<Pelicula, Long>, CrudRepository<Pelicula, Long> {

    @Query("SELECT p FROM Pelicula p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Pelicula> findByNombreContainingIgnoreCase(@Param("nombre") String nombre);
    
    @Query("SELECT p FROM Pelicula p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    Page<Pelicula> findByNombreContainingIgnoreCase(@Param("nombre") String nombre, Pageable pageable);
    
    @Query("SELECT p FROM Pelicula p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) OR LOWER(p.genero.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    Page<Pelicula> findByNombreOrGeneroContainingIgnoreCase(@Param("nombre") String nombre, Pageable pageable);
}
