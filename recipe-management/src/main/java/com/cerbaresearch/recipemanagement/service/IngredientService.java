package com.cerbaresearch.recipemanagement.service;

import com.cerbaresearch.recipemanagement.exception.InternalServerErrorException;
import com.cerbaresearch.recipemanagement.exception.ResourceNotFoundException;
import com.cerbaresearch.recipemanagement.model.Ingredient;
import com.cerbaresearch.recipemanagement.model.Recipe;
import com.cerbaresearch.recipemanagement.repositories.IngredientRepository;
import com.cerbaresearch.recipemanagement.repositories.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        try {
            List<Ingredient> ingredients = ingredientRepository.findAll();
            return ResponseEntity.ok(ingredients);
        }catch (Exception e){
            throw new InternalServerErrorException("Internal server error: "  + e.getMessage());
        }
    }

    public ResponseEntity<Ingredient> getIngredientById(Long id) {
        try {
            Optional<Ingredient> ingredient = ingredientRepository.findById(id);
            if(ingredient.isEmpty()){
                throw new ResourceNotFoundException("Ingredient not found with id: " + id);
            }
            return ResponseEntity.ok(ingredient.get());
        }catch (Exception e){
            throw new InternalServerErrorException("Internal server error: "  + e.getMessage());
        }
    }

    public ResponseEntity<Ingredient> updateIngredient(Ingredient ingredient) {
        try {
            Optional<Ingredient> existingIngredientOptional = ingredientRepository.findById(ingredient.getId());
            if (existingIngredientOptional.isPresent()) {
                Ingredient existingIngredient = existingIngredientOptional.get();
                existingIngredient.setName(ingredient.getName());
                existingIngredient.setQuantity(ingredient.getQuantity());
                existingIngredient.setUpdatedAt(new Date());
                Ingredient ubdatedIngredient = ingredientRepository.save(existingIngredient);
                return ResponseEntity.ok(ubdatedIngredient);
            } else {
                throw new ResourceNotFoundException("Ingredient not found with id: " + ingredient.getId());
            }
        }catch (Exception e){
            throw new InternalServerErrorException("Internal server error: "  + e.getMessage());
        }
    }
    public ResponseEntity<Ingredient> addIngredientToRecipe(Long recipeId, Ingredient ingredient) {
        try {
            Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
            if (recipeOptional.isPresent()) {
                Recipe recipe = recipeOptional.get();
                ingredient.setRecipe(recipe);
                ingredient.setCreatedAt(new Date());
                ingredient.setUpdatedAt(new Date());
                Ingredient addedIngredient = ingredientRepository.save(ingredient);
                return ResponseEntity.ok(addedIngredient);
            } else {
                throw new ResourceNotFoundException("Ingredient not found with id: " + recipeId);
            }
        }catch (Exception e){
            throw new InternalServerErrorException("Internal server error: "  + e.getMessage());
        }
    }

    public void removeIngredientFromRecipe(Long ingredientId) {
        try {
            ingredientRepository.deleteById(ingredientId);
        }catch (Exception e){
            throw new InternalServerErrorException("Internal server error: "  + e.getMessage());
        }
    }
}
