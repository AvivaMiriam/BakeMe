package com.avivamiriammandel.bakeme.aac;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.avivamiriammandel.bakeme.model.Recipe;
import com.avivamiriammandel.bakeme.ui.RecipeActivity;

import java.util.List;

public class RecipesInsertViewModel extends AndroidViewModel {

    private static final String TAG = RecipesInsertViewModel.class.getSimpleName();
    private RecipeRepository recipeRepository;
    private List<Recipe> recipesForInsert;
    MutableLiveData<Boolean> recipesInserted;

    public RecipesInsertViewModel(@NonNull Application application, List<Recipe> recipesForInsert) {
        super(application);
        this.recipeRepository = new RecipeRepository(application);
        this.recipesForInsert = recipesForInsert;

        Log.d(TAG, "RecipeInsertOrDeleteViewModel: " + recipesForInsert);
    }

    public LiveData<Boolean> InsertListOfRecipes() {
        recipeRepository.insertListOfRecipes(recipesForInsert);
        recipesInserted = recipeRepository.recipesInserted;
        Log.d(TAG, "InsertRecipe: success " + recipesForInsert);
        return recipesInserted;
    }

}
