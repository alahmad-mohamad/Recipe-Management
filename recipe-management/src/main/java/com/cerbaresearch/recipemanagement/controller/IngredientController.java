package com.cerbaresearch.recipemanagement.controller;

import com.cerbaresearch.recipemanagement.model.Ingredient;
import com.cerbaresearch.recipemanagement.service.IngredientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;
    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable Long id) {
        return ingredientService.getIngredientById(id);
    }

    @PutMapping
    public ResponseEntity<Ingredient> updateIngredient(@RequestBody Ingredient ingredient) {
       return ingredientService.updateIngredient(ingredient);
    }

    @PostMapping("/{recipeId}")
    public ResponseEntity<Ingredient> addIngredientToRecipe(@PathVariable Long recipeId, @RequestBody Ingredient ingredient) {
        return ingredientService.addIngredientToRecipe(recipeId, ingredient);
    }

    @DeleteMapping("/{id}")
    public void removeIngredientFromRecipe(@PathVariable Long id) {
        ingredientService.removeIngredientFromRecipe(id);
    }
}
