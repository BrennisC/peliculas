package com.fiis.peliculas.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fiis.peliculas.entities.Actor;

@Repository
public interface IActorRepository extends  CrudRepository<Actor, Long>, PagingAndSortingRepository<Actor, Long> {
    
    @Query("SELECT a FROM Actor a WHERE LOWER(a.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Actor> findByNombreContainingIgnoreCase(@Param("nombre") String nombre);
    
    @Query("SELECT a FROM Actor a WHERE LOWER(a.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    Page<Actor> findByNombreContainingIgnoreCase(@Param("nombre") String nombre, Pageable pageable);
}
