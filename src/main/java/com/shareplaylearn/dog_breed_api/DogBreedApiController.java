package com.shareplaylearn.dog_breed_api;

import com.shareplaylearn.dog_breed_api.models.Dog;
import com.shareplaylearn.dog_breed_api.resources.DogResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DogBreedApiController {

    private final DogResource dogResource;

    public DogBreedApiController(
            DogResource dogResource
    ) {
        this.dogResource = dogResource;
    }

    @GetMapping(path="/", produces="application/json")
    public List<Dog> getDogBreedIndex() {
        return dogResource.getDogList();
    }
}
