package com.olgunyilmaz.kitchencraft.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.olgunyilmaz.kitchencraft.model.Recipe

class RecipeViewModel : ViewModel() {
    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

    init {
        // Örnek veriler
        _recipes.value = listOf(
            Recipe(
                id = 1,
                name = "Karnıyarık",
                ingredients = listOf("Patlıcan", "Kıyma", "Soğan", "Domates", "Biber"),
                instructions = listOf(
                    "Patlıcanları boydan ikiye kesin",
                    "Kıymayı soğan ile kavurun",
                    "Patlıcanları kızartın",
                    "İç harcı doldurun",
                    "180 derecede 25 dakika pişirin"
                ),
                cookingTime = 45,
                servings = 4
            ),
            Recipe(
                id = 2,
                name = "Mercimek Çorbası",
                ingredients = listOf("Kırmızı mercimek", "Soğan", "Havuç", "Un", "Tereyağı"),
                instructions = listOf(
                    "Mercimekleri yıkayın",
                    "Sebzeleri doğrayın",
                    "Tüm malzemeleri tencereye koyun",
                    "40 dakika pişirin",
                    "Blenderdan geçirin"
                ),
                cookingTime = 40,
                servings = 6
            )
        )
    }
}