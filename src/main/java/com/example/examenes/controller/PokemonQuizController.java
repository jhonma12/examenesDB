// src/main/java/com/example/examenes/controller/PokemonQuizController.java
package com.example.examenes.controller;

import com.example.examenes.model.PokemonQuizQuestion;
import com.example.examenes.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/questions/pokemon")
public class PokemonQuizController {

    @Autowired
    private PokemonService pokemonService;

    @GetMapping
    public String showQuizForm(Model model) {
        PokemonQuizQuestion question = pokemonService.generateQuizQuestion();
        model.addAttribute("quizQuestion", question);
        return "pokemon_quiz";
    }

    @PostMapping
    public String submitQuizAnswer(@ModelAttribute("quizQuestion") PokemonQuizQuestion quizQuestion,
                                   RedirectAttributes redirectAttributes) {
        boolean isCorrect = pokemonService.checkAnswer(quizQuestion.getSelectedAnswer(), quizQuestion.getCorrectAnswer());
        if (isCorrect) {
            redirectAttributes.addFlashAttribute("successMessage", "¡Respuesta correcta!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Respuesta incorrecta. La respuesta correcta es: " + quizQuestion.getCorrectAnswer());
        }
        return "redirect:/questions/pokemon";
    }
}
