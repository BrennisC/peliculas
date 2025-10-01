package com.fiis.peliculas.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fiis.peliculas.entities.Genero;


@Repository
public interface  IGeneroRepository extends  CrudRepository<Genero, Long> {
}
