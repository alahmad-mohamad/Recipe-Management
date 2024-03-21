package com.cerbaresearch.recipemanagement.repositories;

import com.cerbaresearch.recipemanagement.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query("SELECT r FROM Recipe r JOIN FETCH r.ingredients ORDER BY r.name")
    List<Recipe> findAllRecipesWithIngredientsSortedByName();
}
