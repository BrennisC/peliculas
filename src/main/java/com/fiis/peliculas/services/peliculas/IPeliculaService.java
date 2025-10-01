package com.fiis.peliculas.services.peliculas;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fiis.peliculas.entities.Pelicula;

public interface IPeliculaService {

    Pelicula save(Pelicula pelicula, Long generoId, List<Long> actorIds);
    Pelicula findById(Long id);
    void  delete(Long id);
    List<Pelicula> findAll();
    Page<Pelicula> findAll(Pageable pageable);
    
}
