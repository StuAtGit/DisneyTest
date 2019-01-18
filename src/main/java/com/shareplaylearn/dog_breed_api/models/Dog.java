package com.shareplaylearn.dog_breed_api.models;

import com.google.gson.Gson;

import java.util.Objects;

public class Dog {
    private String breed;
    private static final Gson gson = new Gson();

    public Dog() {}

    public Dog(String breed) {
        this.breed = breed;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dog dog = (Dog) o;
        return Objects.equals(breed, dog.breed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(breed);
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
