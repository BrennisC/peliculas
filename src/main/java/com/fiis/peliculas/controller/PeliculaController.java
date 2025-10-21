package com.fiis.peliculas.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fiis.peliculas.entities.Actor;
import com.fiis.peliculas.entities.Genero;
import com.fiis.peliculas.entities.Pelicula;
import com.fiis.peliculas.services.autor.IActorService;
import com.fiis.peliculas.services.genero.IGeneroService;
import com.fiis.peliculas.services.peliculas.IPeliculaService;


@Controller
public class PeliculaController {

    @Autowired
    private IPeliculaService peliculaService;
    
    @Autowired
    private IGeneroService generoService;
    
    @Autowired
    private IActorService actorService;
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    
    @ModelAttribute("generos")
    public List<Genero> populateGeneros() {
        return generoService.findAll();
    }
    
    // Método para obtener todos los actores para el formulario (sin paginación)
    @ModelAttribute("todosLosActores")
    public List<Actor> populateTodosLosActores() {
        return actorService.findAll();
    }

    // Mostrar la vista de películas con la lista y formulario
    @GetMapping("/peliculas")
    public String showPeliculas(Model model){

        model.addAttribute("pelicula", new Pelicula());
        return "views/peliculas";
    }

    // Guardar nueva película
    @PostMapping("/peliculas/save")
    public String savePelicula(@ModelAttribute("pelicula") @jakarta.validation.Valid Pelicula pelicula,
                              BindingResult result,
                              @RequestParam(value = "generoId", required = false) Long generoId,
                              @RequestParam(value = "actorIds", required = false) List<Long> actorIds,
                              RedirectAttributes redirectAttributes , Model model) {

        // Si hay errores de validación, devolver la vista con los errores por campo
        if (result.hasErrors()) {
            // Asegurar que la vista tenga los datos necesarios
            model.addAttribute("pelicula", pelicula);
            model.addAttribute("generos", generoService.findAll());
            model.addAttribute("todosLosActores", actorService.findAll());

            // Mensaje genérico de error para la vista (no exponer detalles internos)
            model.addAttribute("error", "Por favor corrija los errores en el formulario.");
            return "views/peliculas";
        }

        try {
            peliculaService.save(pelicula, generoId, actorIds);
            redirectAttributes.addFlashAttribute("success", "Película guardada exitosamente");
        } catch (Exception e) {
            // No exponer e.getMessage() que puede contener rutas/entidades.
            redirectAttributes.addFlashAttribute("error", "Error al guardar la película. Revise los datos y vuelva a intentar.");
        }
        return "redirect:/peliculas";
    }

    // Editar película
    @GetMapping("/peliculas/edit/{id}")
    public String editPelicula(@PathVariable Long id, Model model) {
        Pelicula pelicula = peliculaService.findById(id);
        model.addAttribute("pelicula", pelicula);
        model.addAttribute("peliculas", peliculaService.findAll());
        // Los generos y actores ya están disponibles por los @ModelAttribute
        return "views/adminitrar_peliculas";
    }

    // Eliminar película
    @GetMapping("/peliculas/delete/{id}")
    public String deletePelicula(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            peliculaService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Película eliminada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la película: " + e.getMessage());
        }
        return "redirect:/administrar_peliculas";
    }


    @GetMapping("/administrar_peliculas")
    public String administrarPeliculas(Model model,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        
        // Paginación para películas en la administración
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombre"));
        Page<Pelicula> peliculasPage = peliculaService.findAll(pageable);
        
        model.addAttribute("peliculasPage", peliculasPage);
        model.addAttribute("peliculas", peliculasPage.getContent());
        model.addAttribute("pelicula", new Pelicula());
        
        // Atributos para la paginación
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", peliculasPage.getTotalPages());
        model.addAttribute("totalElements", peliculasPage.getTotalElements());
        model.addAttribute("hasPrevious", peliculasPage.hasPrevious());
        model.addAttribute("hasNext", peliculasPage.hasNext());
        
        return "views/adminitrar_peliculas";
    }
}
