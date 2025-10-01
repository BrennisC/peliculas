package com.fiis.peliculas.services.autor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fiis.peliculas.dao.IActorRepository;
import com.fiis.peliculas.entities.Actor;

@Service
public class ActorService  implements  IActorService {

    @Autowired
    private final IActorRepository actorRepository;

    public ActorService(IActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Override
    public void save(Actor actor , MultipartFile file) {
        if(file != null && !file.isEmpty()) {
            try {

                String uploadDir = System.getProperty("user.dir") + "/uploads/actores/";
                java.io.File dir = new java.io.File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                // Nombre único para la imagen
                String fileName = java.util.UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                java.nio.file.Path filePath = java.nio.file.Paths.get(uploadDir, fileName);
                // Guardar archivo
                file.transferTo(filePath);
                // Guardar la ruta relativa o absoluta en el actor
                actor.setUrlImagen("/uploads/actores/" + fileName);
            } catch (Exception e) {
                e.printStackTrace();
                // Manejo de errores (puedes lanzar una excepción personalizada si lo deseas)
                throw new RuntimeException("Error al guardar la imagen del actor");
            }
        }

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


}
