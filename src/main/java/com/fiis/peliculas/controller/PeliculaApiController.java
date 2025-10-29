package com.fiis.peliculas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fiis.peliculas.entities.Pelicula;
import com.fiis.peliculas.services.peliculas.IPeliculaService;

@RestController
@RequestMapping("/api")
public class PeliculaApiController {

    @Autowired
    private IPeliculaService peliculaService;

    @GetMapping("/peliculas")
    public ResponseEntity<List<Pelicula>> getAllPeliculas() {
        List<Pelicula> peliculas = peliculaService.findAll();
        return ResponseEntity.ok(peliculas);
    }

    @GetMapping("/peliculas/paginated")
    public ResponseEntity<List<Pelicula>> getAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("nombre"));
            Page<Pelicula> peliculasPage = peliculaService.findAll(pageable);
            return ResponseEntity.ok(peliculasPage.getContent());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/peliculas/search")
    public ResponseEntity<List<Pelicula>> searchPeliculas(
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("nombre"));
            Page<Pelicula> peliculasPage = peliculaService.findByNombre(nombre, pageable);
            return ResponseEntity.ok(peliculasPage.getContent());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/peliculas/search-advanced")
    public ResponseEntity<List<Pelicula>> searchPeliculasAdvanced(
            @RequestParam(value = "searchTerm", required = false) String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("nombre"));
            Page<Pelicula> peliculasPage = peliculaService.findByNombreOrGenero(searchTerm, pageable);
            return ResponseEntity.ok(peliculasPage.getContent());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}