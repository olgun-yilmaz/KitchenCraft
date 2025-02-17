package com.olgunyilmaz.kitchencraft.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.olgunyilmaz.kitchencraft.model.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.InputStreamReader

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery
    
    private var allRecipes: List<Recipe> = emptyList()

    init {
        loadRecipes()
    }

    private fun loadRecipes() {
        viewModelScope.launch {
            try {
                val inputStream = getApplication<Application>().assets.open("recipes.json")
                val reader = InputStreamReader(inputStream)
                val recipeListType = object : TypeToken<RecipeResponse>() {}.type
                val recipeResponse = Gson().fromJson<RecipeResponse>(reader, recipeListType)
                allRecipes = recipeResponse.recipes
                _recipes.value = allRecipes
                reader.close()
            } catch (e: Exception) {
                // Log errors if needed
                e.printStackTrace()
            }
        }
    }
    
    fun searchRecipes(query: String) {
        _searchQuery.value = query
        if (query.isEmpty()) {
            _recipes.value = allRecipes
            return
        }
        
        val filteredList = allRecipes.filter { recipe ->
            recipe.name.contains(query, ignoreCase = true)
        }
        _recipes.value = filteredList
    }

    fun getPopularRecipes(): List<Recipe> {
        // Return first 5 recipes for now
        // You can modify this to return recipes based on different criteria
        return allRecipes.take(5)
    }
}

data class RecipeResponse(
    val recipes: List<Recipe>
)