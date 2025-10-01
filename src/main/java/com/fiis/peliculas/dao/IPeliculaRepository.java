package com.fiis.peliculas.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fiis.peliculas.entities.Pelicula;


@Repository
public interface  IPeliculaRepository  extends  CrudRepository<Pelicula, Long> {


}
