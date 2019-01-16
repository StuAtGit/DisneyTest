package com.shareplaylearn.dog_breed_api.resources;

import com.shareplaylearn.dog_breed_api.models.Dog;

import java.util.Arrays;
import java.util.List;

public class DogResource {

    public List<Dog> getDogList() {
        return Arrays.asList(new Dog("French Bulldog"), new Dog("German Shepard"));
    }
}
