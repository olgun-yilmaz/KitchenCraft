package com.olgunyilmaz.kitchencraft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.olgunyilmaz.kitchencraft.ui.theme.KitchenCraftTheme
import com.olgunyilmaz.kitchencraft.viewmodel.RecipeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KitchenCraftTheme {
                KitchenCraftApp()
            }
        }
    }
}

@Composable
fun KitchenCraftApp() {
    val navController = rememberNavController()
    val viewModel: RecipeViewModel = viewModel()

    NavHost(navController = navController, startDestination = "recipeList") {
        composable("recipeList") {
            RecipeListScreen(
                viewModel = viewModel,
                onRecipeClick = { recipeId ->
                    navController.navigate("recipeDetail/$recipeId")
                }
            )
        }
        composable("recipeDetail/{recipeId}") { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")?.toIntOrNull() ?: return@composable
            RecipeDetailScreen(
                viewModel = viewModel,
                recipeId = recipeId
            )
        }
    }
}

@Composable
fun RecipeListScreen(
    viewModel: RecipeViewModel,
    onRecipeClick: (Int) -> Unit
) {
    val recipes by viewModel.recipes.collectAsState()

    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(recipes) { recipe ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { onRecipeClick(recipe.id) }
                ) {
                    Text(
                        text = recipe.name,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

@Composable
fun RecipeDetailScreen(
    viewModel: RecipeViewModel,
    recipeId: Int
) {
    val recipes by viewModel.recipes.collectAsState()
    val recipe = recipes.find { it.id == recipeId }

    recipe?.let {
        Scaffold { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Malzemeler:",
                    style = MaterialTheme.typography.titleMedium
                )
                it.ingredients.forEach { ingredient ->
                    Text("• $ingredient")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Hazırlanışı:",
                    style = MaterialTheme.typography.titleMedium
                )
                it.instructions.forEachIndexed { index, instruction ->
                    Text("${index + 1}. $instruction")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Pişirme Süresi: ${it.cookingTime} dakika")
                Text("Porsiyon: ${it.servings} kişilik")
            }
        }
    }
}