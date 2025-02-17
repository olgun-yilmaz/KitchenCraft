package com.olgunyilmaz.kitchencraft.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.olgunyilmaz.kitchencraft.model.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.InputStreamReader

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

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
                _recipes.value = recipeResponse.recipes
                reader.close()
            } catch (e: Exception) {
                // Hata durumunda loglama yapabilirsiniz
                e.printStackTrace()
            }
        }
    }
}

data class RecipeResponse(
    val recipes: List<Recipe>
)