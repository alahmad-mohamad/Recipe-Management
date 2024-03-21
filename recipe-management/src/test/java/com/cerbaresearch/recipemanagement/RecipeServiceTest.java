package com.cerbaresearch.recipemanagement;

import com.cerbaresearch.recipemanagement.exception.InternalServerErrorException;
import com.cerbaresearch.recipemanagement.exception.ResourceNotFoundException;
import com.cerbaresearch.recipemanagement.model.Recipe;
import com.cerbaresearch.recipemanagement.repositories.RecipeRepository;
import com.cerbaresearch.recipemanagement.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeService recipeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        when(recipeRepository.findAllRecipesWithIngredientsSortedByName()).thenReturn(recipes);

        ResponseEntity<List<Recipe>> responseEntity = recipeService.getAllRecipes();

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(recipes, responseEntity.getBody());
    }

    @Test
    public void testGetRecipeById() {
        Long id = 1L;
        Recipe recipe = new Recipe();
        recipe.setId(id);
        Optional<Recipe> recipeOptional = Optional.of(recipe);
        when(recipeRepository.findById(id)).thenReturn(recipeOptional);

        ResponseEntity<Recipe> responseEntity = recipeService.getRecipeById(id);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(recipe, responseEntity.getBody());
    }

    @Test
    public void testGetRecipeById_NotFound() {
        Long id = 1L;
        Optional<Recipe> recipeOptional = Optional.empty();
        when(recipeRepository.findById(id)).thenReturn(recipeOptional);

        assertThrows(InternalServerErrorException.class, () -> {
            recipeService.getRecipeById(id);
        });
    }

    // Similar tests for createRecipe, updateRecipe, and deleteRecipe methods
}

