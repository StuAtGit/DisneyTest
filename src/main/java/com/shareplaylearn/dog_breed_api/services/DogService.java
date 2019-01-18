package com.shareplaylearn.dog_breed_api.services;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.shareplaylearn.dog_breed_api.daos.DogDao;
import com.shareplaylearn.dog_breed_api.models.Dog;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class DogService {
    private final Jdbi jdbi;
    private final MetricRegistry metricRegistry;
    //public is useful for use with tests/mocking
    public static final String DOG_BREED_LOOKUP_TIMER = "dogBreedLookupTimer-service";
    private final Timer dogBreedLookupTimer;
    public static final String DOG_INDEX_LIST_TIMER = "dogIndexListTimer-service";
    private final Timer dogIndexListTimer;

    public DogService(Jdbi jdbi, MetricRegistry metricRegistry) {
        this.jdbi = jdbi;
        this.metricRegistry = metricRegistry;
        this.dogBreedLookupTimer = this.metricRegistry.timer(
            DOG_BREED_LOOKUP_TIMER
        );
        this.dogIndexListTimer = this.metricRegistry.timer(
            DOG_INDEX_LIST_TIMER
        );
    }

    public List<Dog> getDogIndex() {
        return this.jdbi.withHandle(
            (h) -> {
                Timer.Context ctxt = this.dogIndexListTimer.time();
                List<Dog> ret;
                try {
                    ret = h.attach(DogDao.class).getDogIndex();
                } finally {
                    ctxt.stop();
                }
                return ret;
            }
        );
    }

    public List<Dog> getDogsWithBreed(String breed) {
        return this.jdbi.withHandle(
            (h) -> {
                Timer.Context ctxt = this.dogIndexListTimer.time();
                List<Dog> ret;
                try {
                    ret = h.attach(DogDao.class).getDogsWithBreed(breed);
                } finally {
                    ctxt.stop();
                }
                return ret;
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
