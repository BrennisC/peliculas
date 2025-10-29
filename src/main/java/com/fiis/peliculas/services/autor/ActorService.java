package com.fiis.peliculas.services.autor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fiis.peliculas.dao.IActorRepository;
import com.fiis.peliculas.entities.Actor;

@Service
public class ActorService  implements  IActorService {

    private final IActorRepository actorRepository;

    public ActorService(IActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Override
    public void save(Actor actor ) {
        actorRepository.save(actor);
    }

    @Override
    public Actor findById(Long id) {
        return actorRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        actorRepository.deleteById(id);
    }

    @Override
    public List<Actor> findAll() {
        return (List<Actor>) actorRepository.findAll();
    }

    @Override
    public Page<Actor> findAll(Pageable pageable) {
        return actorRepository.findAll(pageable);
    }

    @Override
    public List<Actor> findByNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return findAll();
        }
        return actorRepository.findByNombreContainingIgnoreCase(nombre.trim());
    }

    @Override
    public Page<Actor> findByNombre(String nombre, Pageable pageable) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return findAll(pageable);
        }
        return actorRepository.findByNombreContainingIgnoreCase(nombre.trim(), pageable);
    }

}
