package com.chrisssanti.tacocloud.web;

import com.chrisssanti.tacocloud.data.jpa.IngredientRepository;
import com.chrisssanti.tacocloud.data.jpa.TacoRepository;
import com.chrisssanti.tacocloud.model.Ingredient;
import com.chrisssanti.tacocloud.model.Ingredient.Type;
import com.chrisssanti.tacocloud.model.Order;
import com.chrisssanti.tacocloud.model.Taco;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;
    private final TacoRepository tacoRepository;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public DesignTacoController(
            IngredientRepository ingredientRepo,
            TacoRepository tacoRepo) {
        this.ingredientRepository = ingredientRepo;
        this.tacoRepository = tacoRepo;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "design")
    public Taco design() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(Model model){
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(
                ingredient -> {
                    ingredients.add(ingredient);
                    System.out.println(ingredient.toString());
                });

        Type[] types = Ingredient.Type.values();
        for(Type type : types){
            // filter the ingredients by type and collect
            // here we add a dictionnary to the model of the type {wrap: [ingredient1, ingredient2], etc
            model.addAttribute(type.toString().toLowerCase(), ingredients.stream().filter(
                    ingredient -> {
                        return ingredient.getType().equals(type);
                    }).collect(Collectors.toList())
            );
        }
        return "design";
    }


    @PostMapping
    public String processDesign(@Valid @ModelAttribute("design") Taco design, Errors errors, @ModelAttribute("order") Order order){
        if(errors.hasErrors()){
            log.error("Error while processing the design form");
            return "design";
        }

        Taco savedTaco = tacoRepository.save(design);
        order.addDesign(savedTaco);
        log.info("Saved design: {} ", design);
        return "redirect:/orders/current";
    }

}
