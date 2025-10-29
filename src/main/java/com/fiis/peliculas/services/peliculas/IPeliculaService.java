package com.fiis.peliculas.services.peliculas;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.fiis.peliculas.entities.Pelicula;

public interface IPeliculaService {

    Pelicula save(Pelicula pelicula, Long generoId, List<Long> actorIds , MultipartFile file);
    Pelicula findById(Long id);
    void  delete(Long id);
    List<Pelicula> findAll();
    Page<Pelicula> findAll(Pageable pageable);
    List<Pelicula> findByNombre(String nombre);
    Page<Pelicula> findByNombre(String nombre, Pageable pageable);
    Page<Pelicula> findByNombreOrGenero(String searchTerm, Pageable pageable);
    
}
