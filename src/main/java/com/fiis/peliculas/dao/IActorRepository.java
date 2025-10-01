package com.fiis.peliculas.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fiis.peliculas.entities.Actor;

@Repository
public interface IActorRepository extends  CrudRepository<Actor, Long >{
}
