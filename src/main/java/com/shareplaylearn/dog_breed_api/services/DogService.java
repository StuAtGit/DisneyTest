package com.shareplaylearn.dog_breed_api.services;

import com.shareplaylearn.dog_breed_api.daos.DogDao;
import com.shareplaylearn.dog_breed_api.models.Dog;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class DogService {
    private final Jdbi jdbi;

    public DogService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public List<Dog> getDogIndex() {
        return this.jdbi.withHandle(
            (h) -> {
                return h.attach(DogDao.class).getDogIndex();
            }
        );
    }

    public List<Dog> getDogsWithBreed(String breed) {
        return this.jdbi.withHandle(
            (h) -> {
                return h.attach(DogDao.class).getDogsWithBreed(breed);
            }
        );
    }

    public void putDog(Dog dog) {
        this.jdbi.useHandle(
            (h) -> {
                h.attach(DogDao.class).insertDog(dog);
            }
        );
    }
}
