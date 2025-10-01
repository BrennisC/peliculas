package com.fiis.peliculas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiis.peliculas.entities.Genero;
import com.fiis.peliculas.services.genero.IGeneroService;




@RestController
@RequestMapping("/api")
public class GeneroController {


    private final IGeneroService generoService; 

    public GeneroController(IGeneroService generoService) {
        this.generoService = generoService;
    }

    @PostMapping("/genero")
    public ResponseEntity<?> guardarGenero(@ModelAttribute Genero genero) {
        try {
            generoService.save(genero);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar el género: " + e.getMessage());
        }
    }

    @GetMapping("/genero/{id}")
    public ResponseEntity<Genero> buscarId(@PathVariable Long id) {
        Genero genero = generoService.findById(id);
        if (genero != null) {
            return ResponseEntity.ok(genero);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/generos")
    public ResponseEntity<List<Genero>> listarGeneros() {
        List<Genero> generos = generoService.findAll();
        return ResponseEntity.ok(generos);
    }
    
    @DeleteMapping("/genero/{id}")
    public ResponseEntity<Void> eliminarGenero(@PathVariable Long id) {
        try {
            generoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    


}
