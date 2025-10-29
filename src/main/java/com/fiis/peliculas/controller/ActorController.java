package com.fiis.peliculas.controller;

import com.fiis.peliculas.entities.Actor;
import com.fiis.peliculas.services.autor.IActorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ActorController {

    @Autowired
    private IActorService actorService;

    public ActorController(IActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping("/actores")
    public String actores(Model model) {
        List<Actor> actores = actorService.findAll();
        if (actores.isEmpty()) {
            model.addAttribute("mensaje", "No hay actores registrados");
        } else {
            model.addAttribute("actores", actores);
        }
        // Paginación para actores
        return "views/actores";
    }

    @GetMapping("/actores/nuevo")
    public String nuevoActor(Model model) {
        model.addAttribute("actor", new Actor()); // Crear un actor vacío
        return "views/form-actor"; // Vista del formulario
    }

    @PostMapping("/actores/guardar")
    public String guardarActor(@ModelAttribute Actor actor) {
        actorService.save(actor); // Guardar el actor en la base de datos
        return "redirect:/actores"; // Redirigir al listado de actores
    }

    @DeleteMapping("/actores/eliminar/{id}")
    public String deleteActor(
        @PathVariable Long id,
        RedirectAttributes redirectAttributes
    ) {
        try {
            actorService.delete(id);
            redirectAttributes.addFlashAttribute(
                "success",
                "Película eliminada exitosamente"
            );
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                "error",
                "Error al eliminar la película: " + e.getMessage()
            );
        }
        return "redirect:/actores";
    }

    @PutMapping("/actores/editar/{id}")
    public String editarActor(@PathVariable Long id, @RequestBody Actor actor) {
        actor.setId(id);
        actorService.save(actor);
        return "redirect:/actores";
    }
}
