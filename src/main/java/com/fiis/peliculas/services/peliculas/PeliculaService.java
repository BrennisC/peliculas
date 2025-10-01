package com.fiis.peliculas.services.peliculas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiis.peliculas.dao.IPeliculaRepository;
import com.fiis.peliculas.entities.Actor;
import com.fiis.peliculas.entities.Genero;
import com.fiis.peliculas.entities.Pelicula;
import com.fiis.peliculas.services.autor.IActorService;
import com.fiis.peliculas.services.genero.IGeneroService;


@Service
public class PeliculaService implements IPeliculaService {

    @Autowired
    private IPeliculaRepository peliculaRepository;

    @Autowired
    private IGeneroService generoService;
    
    @Autowired
    private IActorService actorService;

    @Override
    public Pelicula save(Pelicula pelicula, Long generoId, List<Long> actorIds) {
        // Asignar género si se seleccionó uno
        if (generoId != null) {
            Genero genero = generoService.findById(generoId);
            if (genero != null) {
                pelicula.setGenero(genero);
            }
        }
        
        // Asignar actores si se seleccionaron
        if (actorIds != null && !actorIds.isEmpty()) {
            List<Actor> actores = actorIds.stream()
                .map(id -> actorService.findById(id))
                .filter(actor -> actor != null)
                .toList();
            pelicula.setProtagonistas(actores);
        }

        return peliculaRepository.save(pelicula);
    }

    @Override
    public Pelicula findById(Long id) {
        return peliculaRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        peliculaRepository.deleteById(id);
    }

    @Override
    public List<Pelicula> findAll() {
        return (List<Pelicula>) peliculaRepository.findAll();
    }
}
