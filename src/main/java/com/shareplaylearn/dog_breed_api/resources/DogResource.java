package com.shareplaylearn.dog_breed_api.resources;

import com.google.gson.Gson;
import com.shareplaylearn.dog_breed_api.models.Dog;
import com.shareplaylearn.dog_breed_api.services.DogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class DogResource {

    private final DogService dogService;
    private static final Gson GSON = new Gson();
    private static final Logger LOG = LoggerFactory.getLogger(DogResource.class);

    public DogResource(DogService dogService) {
        this.dogService = dogService;
    }


    public ResponseEntity<String> getDogList() {
        return GenericResourceHandler.handleResource(
            () -> this.dogService.getDogIndex(),
            LOG,
            "No dogs in index"
        );
    }

    public ResponseEntity<String> getDogsWithBreed(String breed) {
        return GenericResourceHandler.handleResource(
            () -> this.dogService.getDogsWithBreed(breed),
            LOG,
            "No dogs of breed " + breed + " found."
        );
    }
}
