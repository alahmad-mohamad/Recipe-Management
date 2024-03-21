package com.cerbaresearch.recipemanagement;

import com.cerbaresearch.recipemanagement.exception.InternalServerErrorException;
import com.cerbaresearch.recipemanagement.exception.ResourceNotFoundException;
import com.cerbaresearch.recipemanagement.model.Ingredient;
import com.cerbaresearch.recipemanagement.model.Recipe;
import com.cerbaresearch.recipemanagement.repositories.IngredientRepository;
import com.cerbaresearch.recipemanagement.repositories.RecipeRepository;
import com.cerbaresearch.recipemanagement.service.IngredientService;
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

class IngredientServiceTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private IngredientService ingredientService;

    private Ingredient testIngredient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        // Initialize test ingredient
        testIngredient = new Ingredient();
        testIngredient.setId(1L);
        testIngredient.setName("Test Ingredient");
        testIngredient.setQuantity("1 cup");
    }

    @Test
    void testGetAllIngredients() {
        // Mock repository behavior
        List<Ingredient> mockIngredients = new ArrayList<>();
        mockIngredients.add(testIngredient);
        when(ingredientRepository.findAll()).thenReturn(mockIngredients);

        // Call service method
        ResponseEntity<List<Ingredient>> response = ingredientService.getAllIngredients();

        // Verify the response
        assertEquals(mockIngredients, response.getBody());
    }

    @Test
    void testGetIngredientById_Exists() {
        // Mock repository behavior
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(testIngredient));

        // Call service method
        ResponseEntity<Ingredient> response = ingredientService.getIngredientById(1L);

        // Verify the response
        assertEquals(testIngredient, response.getBody());
    }

    @Test
    void testGetIngredientById_NotExists() {
        // Mock repository behavior
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Call service method and verify exception
        assertThrows(InternalServerErrorException.class, () -> ingredientService.getIngredientById(1L));
    }


    @Test
    void testUpdateIngredient_Exists() {
        // Mock repository behavior
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(testIngredient));
        when(ingredientRepository.save(any())).thenReturn(testIngredient);

        // Call service method
        ResponseEntity<Ingredient> response = ingredientService.updateIngredient(testIngredient);
        // Verify the response
        assertEquals(testIngredient, response.getBody());
    }

    @Test
    void testUpdateIngredient_NotExists() {
        // Mock repository behavior
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Call service method and verify exception
        assertThrows(InternalServerErrorException.class, () -> ingredientService.updateIngredient(testIngredient));
    }

    @Test
    void testAddIngredientToRecipe_Exists() {
        // Mock repository behavior
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(new Recipe()));
        when(ingredientRepository.save(any())).thenReturn(testIngredient);

        // Call service method
        ResponseEntity<Ingredient> response = ingredientService.addIngredientToRecipe(1L, testIngredient);

        // Verify the response
        assertEquals(testIngredient, response.getBody());
    }

    @Test
    void testAddIngredientToRecipe_NotExists() {
        // Mock repository behavior
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Call service method and verify exception
        assertThrows(InternalServerErrorException.class, () -> ingredientService.addIngredientToRecipe(1L, testIngredient));
    }

    @Test
    void testRemoveIngredientFromRecipe() {
        // Call service method
        assertDoesNotThrow(() -> ingredientService.removeIngredientFromRecipe(1L));
    }
}

