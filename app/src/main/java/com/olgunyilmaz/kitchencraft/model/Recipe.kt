package com.olgunyilmaz.kitchencraft.model

data class Recipe(
    val id: Int,
    val name: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val cookingTime: Int,
    val servings: Int
)
