package com.fiis.peliculas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fiis.peliculas.entities.Actor;
import com.fiis.peliculas.services.autor.IActorService;


@RestController
@RequestMapping("/api")
public class ActorController {

    private final IActorService actorService;

    public ActorController(IActorService actorService) {
        this.actorService = actorService;
    }

    @PostMapping("/actor")
    public ResponseEntity<?> createActor(@ModelAttribute Actor actor, @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            actorService.save(actor, file);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar el actor: " + e.getMessage());
        }
    }

    @GetMapping("/actor/{id}")
    public ResponseEntity<Actor> getById(@PathVariable Long id) {
        Actor actor = actorService.findById(id);
        if (actor != null) {
            return ResponseEntity.ok(actor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    

    @GetMapping("/actores")
    public ResponseEntity<List<Actor>> getAll() {
        List<Actor> actores = actorService.findAll();
        return ResponseEntity.ok(actores);
    }

    @DeleteMapping("/actor/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            actorService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
