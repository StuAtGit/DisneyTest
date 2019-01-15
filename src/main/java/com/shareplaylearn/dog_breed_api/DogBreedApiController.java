package com.shareplaylearn.dog_breed_api;

import com.shareplaylearn.dog_breed_api.models.Dog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DogBreedApiController {

    @GetMapping(path="/", produces="application/json")
    public List<Dog> getDogBreedIndex() {

        return Arrays.asList(new Dog("French Bulldog"), new Dog("German Shepard"));
    }
}
