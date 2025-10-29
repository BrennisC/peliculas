package com.fiis.peliculas.services.peliculas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public Pelicula save(Pelicula pelicula, Long generoId, List<Long> actorIds, MultipartFile file) {

        // Procesar la imagen si se proporciona
        if(file != null && !file.isEmpty()) {
            try {
                // CORREGIDO: Cambiar de /actores/ a /peliculas/
                String uploadDir = System.getProperty("user.dir") + "/uploads/peliculas/";
                java.io.File dir = new java.io.File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                
                // Nombre único para la imagen
                String fileName = java.util.UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                java.nio.file.Path filePath = java.nio.file.Paths.get(uploadDir, fileName);
                
                // Guardar archivo
                file.transferTo(filePath);
                
                // CORREGIDO: Guardar la ruta correcta
                pelicula.setUrlImagen("/uploads/peliculas/" + fileName);
                
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error al guardar la imagen de la película");
            }
        }
        
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

    @Override
    public Page<Pelicula> findAll(Pageable pageable) {
        return peliculaRepository.findAll(pageable);
    }

    @Override
    public List<Pelicula> findByNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return findAll();
        }
        return peliculaRepository.findByNombreContainingIgnoreCase(nombre.trim());
    }

    @Override
    public Page<Pelicula> findByNombre(String nombre, Pageable pageable) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return findAll(pageable);
        }
        return peliculaRepository.findByNombreContainingIgnoreCase(nombre.trim(), pageable);
    }

    @Override
    public Page<Pelicula> findByNombreOrGenero(String searchTerm, Pageable pageable) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return findAll(pageable);
        }
        return peliculaRepository.findByNombreOrGeneroContainingIgnoreCase(searchTerm.trim(), pageable);
    }
}