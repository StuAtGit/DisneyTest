package com.shareplaylearn.dog_breed_api.models;

import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class Dog {
    private Integer id;
    private String registeredName;
    private String petName;
    private String breed;
    private String pictureUrl;
    private String thumbnailUrl;
    private static final Gson gson = new Gson();

    public Dog(String breed, String pictureUrl) throws MalformedURLException {
        new URL(pictureUrl); //validate the URL format
        this.breed = breed;
        this.pictureUrl = pictureUrl;
    }

    public Dog() {}

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegisteredName() {
        return registeredName;
    }

    public void setRegisteredName(String registeredName) {
        this.registeredName = registeredName;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dog dog = (Dog) o;
        return id == dog.id &&
            Objects.equals(registeredName, dog.registeredName) &&
            Objects.equals(petName, dog.petName) &&
            Objects.equals(breed, dog.breed) &&
            Objects.equals(pictureUrl, dog.pictureUrl) &&
            Objects.equals(thumbnailUrl, dog.thumbnailUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, registeredName, petName, breed, pictureUrl, thumbnailUrl);
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
