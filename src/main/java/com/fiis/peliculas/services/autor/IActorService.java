package com.fiis.peliculas.services.autor;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fiis.peliculas.entities.Actor;

public interface IActorService {

    public void save(Actor actor , MultipartFile file);
    public Actor findById(Long id);
    public void  delete(Long id);
    public List<Actor> findAll();

}
