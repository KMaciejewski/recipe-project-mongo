package com.km.bootstrap;

import com.km.model.*;
import com.km.repository.CategoryRepository;
import com.km.repository.IngredientRepository;
import com.km.repository.RecipeRepository;
import com.km.repository.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientRepository ingredientRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository,
                           UnitOfMeasureRepository unitOfMeasureRepository, IngredientRepository ingredientRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    @Transactional // to avoid LazyInitializationException in Category entity for recipes field
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadCategories();
        loadUom();
        loadIngredients();
        recipeRepository.saveAll(getRecipes());
        log.debug("Loading bootstrap data");
    }

    private void loadCategories() {
        Category cat1 = new Category();
        cat1.setDescription("American");
        categoryRepository.save(cat1);

        Category cat2 = new Category();
        cat2.setDescription("Italian");
        categoryRepository.save(cat2);

        Category cat3 = new Category();
        cat3.setDescription("Mexican");
        categoryRepository.save(cat3);

        Category cat4 = new Category();
        cat4.setDescription("Fast Food");
        categoryRepository.save(cat4);
    }

    private void loadUom() {
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setDescription("Teaspoon");
        unitOfMeasureRepository.save(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setDescription("Tablespoon");
        unitOfMeasureRepository.save(uom2);

        UnitOfMeasure uom3 = new UnitOfMeasure();
        uom3.setDescription("Cup");
        unitOfMeasureRepository.save(uom3);

        UnitOfMeasure uom4 = new UnitOfMeasure();
        uom4.setDescription("Pinch");
        unitOfMeasureRepository.save(uom4);

        UnitOfMeasure uom5 = new UnitOfMeasure();
        uom5.setDescription("Ounce");
        unitOfMeasureRepository.save(uom5);

        UnitOfMeasure uom6 = new UnitOfMeasure();
        uom6.setDescription("Each");
        unitOfMeasureRepository.save(uom6);

        UnitOfMeasure uom7 = new UnitOfMeasure();
        uom7.setDescription("Pint");
        unitOfMeasureRepository.save(uom7);

        UnitOfMeasure uom8 = new UnitOfMeasure();
        uom8.setDescription("Dash");
        unitOfMeasureRepository.save(uom8);
    }

    private void loadIngredients() {
        //get UOMs
        Optional<UnitOfMeasure> eachUomOptional = unitOfMeasureRepository.findByDescription("Each");
        Optional<UnitOfMeasure> tableSpoonUomOptional = unitOfMeasureRepository.findByDescription("Tablespoon");
        Optional<UnitOfMeasure> teaSpoonUomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
        Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByDescription("Dash");
        Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.findByDescription("Pint");
        Optional<UnitOfMeasure> cupsUomOptional = unitOfMeasureRepository.findByDescription("Cup");

        //get optionals
        UnitOfMeasure eachUom = eachUomOptional.orElseThrow(() -> new RuntimeException("Expected UOM Not Found"));
        UnitOfMeasure tableSpoonUom = tableSpoonUomOptional.orElseThrow(() -> new RuntimeException("Expected UOM Not Found"));
        UnitOfMeasure teapoonUom = tableSpoonUomOptional.orElseThrow(() -> new RuntimeException("Expected UOM Not Found"));
        UnitOfMeasure dashUom = dashUomOptional.orElseThrow(() -> new RuntimeException("Expected UOM Not Found"));
        UnitOfMeasure pintUom = pintUomOptional.orElseThrow(() -> new RuntimeException("Expected UOM Not Found"));
        UnitOfMeasure cupsUom = cupsUomOptional.orElseThrow(() -> new RuntimeException("Expected UOM Not Found"));

        // guacRecipe ingredients
        ingredientRepository.save(new Ingredient("ripe avocados", new BigDecimal(2), eachUom));
        ingredientRepository.save(new Ingredient("Kosher salt", new BigDecimal(".5"), teapoonUom));
        ingredientRepository.save(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), tableSpoonUom));
        ingredientRepository.save(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), tableSpoonUom));
        ingredientRepository.save(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), eachUom));
        ingredientRepository.save(new Ingredient("Cilantro", new BigDecimal(2), tableSpoonUom));
        ingredientRepository.save(new Ingredient("freshly grated black pepper", new BigDecimal(2), dashUom));
        ingredientRepository.save(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), eachUom));

        // tacosRecipe ingredients
        ingredientRepository.save(new Ingredient("Ancho Chili Powder", new BigDecimal(2), tableSpoonUom));
        ingredientRepository.save(new Ingredient("Dried Oregano", new BigDecimal(1), teapoonUom));
        ingredientRepository.save(new Ingredient("Dried Cumin", new BigDecimal(1), teapoonUom));
        ingredientRepository.save(new Ingredient("Sugar", new BigDecimal(1), teapoonUom));
        ingredientRepository.save(new Ingredient("Salt", new BigDecimal(".5"), teapoonUom));
        ingredientRepository.save(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), eachUom));
        ingredientRepository.save(new Ingredient("finely grated orange zestr", new BigDecimal(1), tableSpoonUom));
        ingredientRepository.save(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tableSpoonUom));
        ingredientRepository.save(new Ingredient("Olive Oil", new BigDecimal(2), tableSpoonUom));
        ingredientRepository.save(new Ingredient("boneless chicken thighs", new BigDecimal(4), tableSpoonUom));
        ingredientRepository.save(new Ingredient("small corn tortillasr", new BigDecimal(8), eachUom));
        ingredientRepository.save(new Ingredient("packed baby arugula", new BigDecimal(3), cupsUom));
        ingredientRepository.save(new Ingredient("medium ripe avocados, slic", new BigDecimal(2), eachUom));
        ingredientRepository.save(new Ingredient("radishes, thinly sliced", new BigDecimal(4), eachUom));
        ingredientRepository.save(new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), pintUom));
        ingredientRepository.save(new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), eachUom));
        ingredientRepository.save(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), eachUom));
        ingredientRepository.save(new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), cupsUom));
        ingredientRepository.save(new Ingredient("lime, cut into wedges", new BigDecimal(4), eachUom));
    }

    private List<Recipe> getRecipes() {

        List<Recipe> recipes = new ArrayList<>(2);

        //get Categories
        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");
        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");

        Category americanCategory = americanCategoryOptional.orElseThrow(() -> new RuntimeException("Expected Category Not Found"));
        Category mexicanCategory = mexicanCategoryOptional.orElseThrow(() -> new RuntimeException("Expected Category Not Found"));

        //Yummy Guac
        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.setPrepTime(10);
        guacRecipe.setCookTime(0);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setDirection("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd");

        Note guacNotes = new Note();
        guacNotes.setRecipeNote("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws");

        guacRecipe.setNote(guacNotes);

        addIngredientByDescription(guacRecipe, "ripe avocados");
        addIngredientByDescription(guacRecipe, "Kosher salt");
        addIngredientByDescription(guacRecipe, "fresh lime juice or lemon juice");
        addIngredientByDescription(guacRecipe, "minced red onion or thinly sliced green onion");
        addIngredientByDescription(guacRecipe, "serrano chiles, stems and seeds removed, minced");
        addIngredientByDescription(guacRecipe, "Cilantro");
        addIngredientByDescription(guacRecipe, "freshly grated black pepper");
        addIngredientByDescription(guacRecipe, "ripe tomato, seeds and pulp removed, chopped");

        guacRecipe.getCategories().add(americanCategory);
        guacRecipe.getCategories().add(mexicanCategory);

        guacRecipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacRecipe.setServings(4);
        guacRecipe.setSource("Simply Recipes");

        //add to return list
        recipes.add(guacRecipe);

        //Yummy Tacos
        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setDescription("Spicy Grilled Chicken Taco");
        tacosRecipe.setCookTime(9);
        tacosRecipe.setPrepTime(20);
        tacosRecipe.setDifficulty(Difficulty.MODERATE);

        tacosRecipe.setDirection("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n" +
                "\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm");

        Note tacoNotes = new Note();
        tacoNotes.setRecipeNote("We have a family motto and it is this: Everything goes better in a tortilla.\n" +
                "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
                "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ");

        tacosRecipe.setNote(tacoNotes);

        addIngredientByDescription(tacosRecipe, "Ancho Chili Powder");
        addIngredientByDescription(tacosRecipe, "Dried Oregano");
        addIngredientByDescription(tacosRecipe, "Dried Cumin");
        addIngredientByDescription(tacosRecipe, "Sugar");
        addIngredientByDescription(tacosRecipe, "Salt");
        addIngredientByDescription(tacosRecipe, "Clove of Garlic, Choppedr");
        addIngredientByDescription(tacosRecipe, "finely grated orange zestr");
        addIngredientByDescription(tacosRecipe, "fresh-squeezed orange juice");
        addIngredientByDescription(tacosRecipe, "Olive Oil");
        addIngredientByDescription(tacosRecipe, "boneless chicken thighs");
        addIngredientByDescription(tacosRecipe, "small corn tortillasr");
        addIngredientByDescription(tacosRecipe, "packed baby arugula");
        addIngredientByDescription(tacosRecipe, "medium ripe avocados, slic");
        addIngredientByDescription(tacosRecipe, "radishes, thinly sliced");
        addIngredientByDescription(tacosRecipe, "cherry tomatoes, halved");
        addIngredientByDescription(tacosRecipe, "red onion, thinly sliced");
        addIngredientByDescription(tacosRecipe, "Roughly chopped cilantro");
        addIngredientByDescription(tacosRecipe, "cup sour cream thinned with 1/4 cup milk");
        addIngredientByDescription(tacosRecipe, "lime, cut into wedges");

        tacosRecipe.getCategories().add(americanCategory);
        tacosRecipe.getCategories().add(mexicanCategory);

        recipes.add(tacosRecipe);
        return recipes;
    }

    private void addIngredientByDescription(Recipe recipe, String description) {
        ingredientRepository.findByDescription(description).ifPresent(ingredient -> recipe.addIngredient(ingredient));
    }
}
