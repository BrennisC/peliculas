package com.fiis.peliculas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/generos")
    public String generos() {
        return "views/generos";
    }
}
