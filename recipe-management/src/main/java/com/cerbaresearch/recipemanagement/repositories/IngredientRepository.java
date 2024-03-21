package com.cerbaresearch.recipemanagement.repositories;

import com.cerbaresearch.recipemanagement.model.Ingredient;
import com.cerbaresearch.recipemanagement.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    @Query("SELECT i FROM Ingredient i WHERE i.recipe = :recipe ORDER BY i.name")
    List<Ingredient> findIngredientsByRecipeSortedByName(@Param("recipe") Recipe recipe);
}
