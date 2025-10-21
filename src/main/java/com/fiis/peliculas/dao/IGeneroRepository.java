package com.fiis.peliculas.dao;

import com.fiis.peliculas.entities.Genero;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGeneroRepository
    extends
        CrudRepository<Genero, Long>,
        PagingAndSortingRepository<Genero, Long> {}
