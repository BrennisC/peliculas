package com.fiis.peliculas.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fiis.peliculas.entities.Pelicula;


@Repository
public interface  IPeliculaRepository  extends  PagingAndSortingRepository<Pelicula, Long>, CrudRepository<Pelicula, Long> {


}
