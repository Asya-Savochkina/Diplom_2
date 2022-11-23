package model;

import java.util.ArrayList;
import java.util.List;

public class CreateTheOrderRequest {

    public List<String> ingredients;

    public CreateTheOrderRequest() {
    }

    public CreateTheOrderRequest(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public static CreateTheOrderRequest createTheOrderRequestWithIngredients() {
        CreateTheOrderRequest createTheOrderRequest = new CreateTheOrderRequest();
        createTheOrderRequest.ingredients = new ArrayList<>();
        createTheOrderRequest.ingredients.add(0,"61c0c5a71d1f82001bdaaa73");
        createTheOrderRequest.ingredients.add(1,"61c0c5a71d1f82001bdaaa76");
        createTheOrderRequest.ingredients.add(2,"61c0c5a71d1f82001bdaaa6d");
        return createTheOrderRequest;
    }

    public static CreateTheOrderRequest createTheOrderRequestWithoutIngredients() {
        CreateTheOrderRequest createTheOrderRequest = new CreateTheOrderRequest();
        createTheOrderRequest.ingredients = new ArrayList<>();
        return createTheOrderRequest;
    }

    public static CreateTheOrderRequest createTheOrderRequestWrongHashOfIngredients() {
        CreateTheOrderRequest createTheOrderRequest = new CreateTheOrderRequest();
        createTheOrderRequest.ingredients = new ArrayList<>();
        createTheOrderRequest.ingredients.add(0,"wrongHash");
        return createTheOrderRequest;
    }

}
