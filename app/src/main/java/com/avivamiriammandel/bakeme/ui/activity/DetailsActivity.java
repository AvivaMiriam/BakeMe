package com.avivamiriammandel.bakeme.ui.activity;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.avivamiriammandel.bakeme.MyApplication;
import com.avivamiriammandel.bakeme.R;
import com.avivamiriammandel.bakeme.aac.AppDatabase;
import com.avivamiriammandel.bakeme.aac.AppExecutors;
import com.avivamiriammandel.bakeme.aac.IngredientTypeConverter;
import com.avivamiriammandel.bakeme.aac.RecipeApiRepository;
import com.avivamiriammandel.bakeme.aac.RecipeDBRepository;
import com.avivamiriammandel.bakeme.aac.RecipeDao;
import com.avivamiriammandel.bakeme.aac.RecipeTypeConverter;
import com.avivamiriammandel.bakeme.aac.StepTypeConverter;

import com.avivamiriammandel.bakeme.adaper.RecipeAdapter;
import com.avivamiriammandel.bakeme.glide.GlideApp;
import com.avivamiriammandel.bakeme.model.Ingredient;
import com.avivamiriammandel.bakeme.model.Recipe;
import com.avivamiriammandel.bakeme.model.Step;
import com.avivamiriammandel.bakeme.rest.Client;
import com.avivamiriammandel.bakeme.rest.Service;
import com.avivamiriammandel.bakeme.ui.fragment.DetailsFragment;
import com.avivamiriammandel.bakeme.ui.fragment.EmptyFragment;
import com.avivamiriammandel.bakeme.ui.fragment.IngredientsFragment;
import com.avivamiriammandel.bakeme.ui.fragment.RecipeFragment;
import com.avivamiriammandel.bakeme.ui.fragment.RecipesListFragment;
import com.avivamiriammandel.bakeme.ui.fragment.StepsFragment;
import com.avivamiriammandel.bakeme.ui.viewmodel.RecipesFromApiViewModel;
import com.avivamiriammandel.bakeme.ui.viewmodel.RecipesInsertViewModel;
import com.avivamiriammandel.bakeme.ui.viewmodel.RecipesInsertViewModelFactory;
import com.avivamiriammandel.bakeme.ui.viewmodel.RecipesViewModel;
import com.github.florent37.glidepalette.GlidePalette;

import java.util.List;

/**
 * An activity representing a list of Recipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class DetailsActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private Context context;
    private List<Recipe> recipeList;
    private Recipe thisRecipe;
    private int recipePosition;
    private ImageView recipeImage;
    private LifecycleOwner lifecycleOwner = DetailsActivity.this;
    private RecipeAdapter adapter;
    private static final String TAG = DetailsActivity.class.getSimpleName();
    public static Boolean hasNoDatabase = true;
    private SharedPreferences sharedPreferences;
    private MyApplication myApplication;
    private RecipeDao recipeDao;
    private AppExecutors appExecutors;
    private Service apiService;
    private SharedPreferences.Editor editor;
    private Boolean insertCompleted;
    private BottomNavigationView navigation;
    private int ingredientFragmentId, stepFragmentId;
    private Boolean navRecipes, navIngredients, navSteps,
            addFragment = false, removeIngredientsFragment = false,
            removeStepsFragment = false;
    Toolbar toolbar;
    private final int[] colors = {R.color.bottomtab_0, R.color.bottomtab_1, R.color.bottomtab_2};

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);

  //      context = DetailsActivity.this;

        Intent fromIntent = getIntent();
        if (fromIntent.hasExtra(getString(R.string.recipe_bundle))) {
            thisRecipe = fromIntent.getParcelableExtra(getString(R.string.recipe_bundle));

            loadDetailsFragment();

        }


    }


    private void loadDetailsFragment() {
        if (getSupportFragmentManager().getFragments().isEmpty()){
            addFragment = true;
        }
        String recipeString =  RecipeTypeConverter.recipeToString(thisRecipe);
        Bundle arguments = new Bundle();
        arguments.putString(getString(R.string.details_intent_title), recipeString);
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(arguments);


        if (addFragment) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout, fragment)
                    .commit();
            addFragment = false;
        }else  {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .commit();
        }
    }

}


