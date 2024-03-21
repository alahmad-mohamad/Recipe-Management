package com.cerbaresearch.recipemanagement.service;

import com.cerbaresearch.recipemanagement.exception.InternalServerErrorException;
import com.cerbaresearch.recipemanagement.exception.ResourceNotFoundException;
import com.cerbaresearch.recipemanagement.model.Ingredient;
import com.cerbaresearch.recipemanagement.model.Recipe;
import com.cerbaresearch.recipemanagement.repositories.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientService ingredientService;
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        try {
            List<Recipe> recipes = recipeRepository.findAllRecipesWithIngredientsSortedByName();
            return ResponseEntity.ok(recipes);
        }catch (Exception e){
            throw new InternalServerErrorException("Internal server error: "  + e.getMessage());
        }
    }

    public ResponseEntity<Recipe> getRecipeById(Long id) {
        try {
            Optional<Recipe> recipe = recipeRepository.findById(id);
            if(recipe.isEmpty()){
                throw new ResourceNotFoundException("Ingredient not found with id: " + id);
            }
            return ResponseEntity.ok(recipe.get());
        }catch (Exception e){
            throw new InternalServerErrorException("Internal server error: "  + e.getMessage());
        }
    }

    public ResponseEntity<Recipe> createRecipe(Recipe recipe) {
        try {
            recipe.setCreatedAt(new Date());
            recipe.setUpdatedAt(new Date());
            List<Ingredient> ingredients = new ArrayList<>();
            for (int i = 0 ; i < recipe.getIngredients().size(); i++){
                Ingredient ingredient = recipe.getIngredients().get(i);
                ingredient.setCreatedAt(new Date());
                ingredient.setRecipe(recipe);
                ingredients.add(ingredient);
            }
            Recipe savedRecipe = recipeRepository.save(recipe);
            return ResponseEntity.ok(savedRecipe);
        }catch (Exception e){
            throw new InternalServerErrorException("Internal server error: "  + e.getMessage());
        }
    }

    public ResponseEntity<Recipe> updateRecipe(Recipe recipe) {
        try {
            Optional<Recipe> existingRecipeOptional = recipeRepository.findById(recipe.getId());
            if (existingRecipeOptional.isPresent()) {
                Recipe existingRecipe = existingRecipeOptional.get();
                existingRecipe.setName(recipe.getName());
                existingRecipe.setDescription(recipe.getDescription());
                existingRecipe.setCookingInstructions(recipe.getCookingInstructions());
                existingRecipe.setUpdatedAt(new Date());
                Recipe updatedRecipe = recipeRepository.save(existingRecipe);
                return ResponseEntity.ok(updatedRecipe);
            } else {
                throw new ResourceNotFoundException("Recipe not found with id: " + recipe.getId());
            }
        }catch (Exception e){
            throw new InternalServerErrorException("Internal server error: "  + e.getMessage());
        }
    }

    public void deleteRecipe(Long id) {
        try {
            recipeRepository.deleteById(id);
        }catch (Exception e){
            throw new InternalServerErrorException("Internal server error: "  + e.getMessage());
        }
    }
}
