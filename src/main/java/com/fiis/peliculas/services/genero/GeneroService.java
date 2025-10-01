package com.fiis.peliculas.services.genero;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiis.peliculas.dao.IGeneroRepository;
import com.fiis.peliculas.entities.Genero;

@Service
public class GeneroService implements  IGeneroService {


    @Autowired
    private IGeneroRepository generoRepository; 
    
    @Override
    public void save(Genero genero) {

        findAll().stream()
            .filter(g -> g.getNombre().equalsIgnoreCase(genero.getNombre()) && (genero.getId() == null || !g.getId().equals(genero.getId())))
            .findAny()
            .ifPresent(g -> {
                throw new IllegalArgumentException("Ya existe un genero con el mismo nombre: " + g.getNombre());
            });

        generoRepository.save(genero);
    }

    @Override
    public Genero findById(Long id) {
        return generoRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        generoRepository.deleteById(id);
    }

    @Override
    public List<Genero> findAll() {
        return (List<Genero>)generoRepository.findAll();
    }

}
