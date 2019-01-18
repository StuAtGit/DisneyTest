package com.shareplaylearn.dog_breed_api.resources;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.google.gson.Gson;
import com.shareplaylearn.dog_breed_api.services.DogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

public class DogResource {

    private final DogService dogService;
    private final MetricRegistry metricRegistry;
    //public is useful for use with tests/mocking
    public static final String DOG_BREED_LOOKUP_TIMER = "dogBreedLookupTimer-resource";
    private final Timer dogBreedLookupTimer;
    public static final String DOG_INDEX_LIST_TIMER = "dogIndexListTimer-resource";
    private final Timer dogIndexListTimer;
    private static final Logger LOG = LoggerFactory.getLogger(DogResource.class);

    public DogResource(DogService dogService, MetricRegistry metricRegistry) {
        this.dogService = dogService;
        this.metricRegistry = metricRegistry;
        this.dogBreedLookupTimer = this.metricRegistry.timer(
            DOG_BREED_LOOKUP_TIMER
        );
        this.dogIndexListTimer = this.metricRegistry.timer(
            DOG_INDEX_LIST_TIMER
        );
    }


    public ResponseEntity<String> getDogList() {
        Timer.Context ctxt = this.dogIndexListTimer.time();
        ResponseEntity<String> resp = GenericResourceHandler.handleResource(
            () -> this.dogService.getDogIndex(),
            LOG,
            "No dogs in index"
        );
        ctxt.stop();
        return resp;
    }

    public ResponseEntity<String> getDogsWithBreed(String breed) {
        Timer.Context ctxt = this.dogBreedLookupTimer.time();
        ResponseEntity<String> resp = GenericResourceHandler.handleResource(
            () -> this.dogService.getDogsWithBreed(breed),
            LOG,
            "No dogs of breed " + breed + " found."
        );
        ctxt.stop();
        return resp;
    }
}
