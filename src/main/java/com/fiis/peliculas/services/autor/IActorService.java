package com.fiis.peliculas.services.autor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fiis.peliculas.entities.Actor;

public interface IActorService {

    public void save(Actor actor );
    public Actor findById(Long id);
    public void  delete(Long id);
    public List<Actor> findAll();
    public Page<Actor> findAll(Pageable pageable);
    public List<Actor> findByNombre(String nombre);
    public Page<Actor> findByNombre(String nombre, Pageable pageable);

}
