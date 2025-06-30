package org.healthtrack.healthtrackweb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsuarioController {

    private double pesoActual = 70.0;

    @GetMapping("/")
    public String mostrarPeso(Model model) {
        model.addAttribute("peso", pesoActual);
        return "index";
    }

    @PostMapping("/actualizarPeso")
    public String actualizarPeso(@RequestParam double nuevoPeso, Model model) {
        pesoActual = nuevoPeso;
        model.addAttribute("peso", pesoActual);
        model.addAttribute("mensaje", "Peso actualizado correctamente");
        return "index";
    }

    @GetMapping("/reset")
    @ResponseBody
    public String resetearPeso() {
        pesoActual = 70.0;
        return "Peso reiniciado a 70.0 kg";
    }

}
