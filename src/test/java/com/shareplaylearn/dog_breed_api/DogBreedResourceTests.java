package com.shareplaylearn.dog_breed_api;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.google.gson.Gson;
import com.shareplaylearn.dog_breed_api.models.Dog;
import com.shareplaylearn.dog_breed_api.resources.DogResource;
import com.shareplaylearn.dog_breed_api.services.DogService;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DogBreedResourceTests {
    private DogResource dogResource;
    private DogService dogService;
    private MetricRegistry metricRegistry;
    private static final String testBreed1 = "test breed1";
    private static final Gson GSON = new Gson();

    public DogBreedResourceTests() {
        dogService = mock(DogService.class);
        metricRegistry = mock(MetricRegistry.class);
        Timer timer = mock(Timer.class);
        Timer.Context context = mock(Timer.Context.class);
        when(timer.time()).thenReturn(context);
        when(metricRegistry.timer(anyString())).thenReturn(timer);
        dogResource = new DogResource(
            dogService,
            metricRegistry
        );
    }

    @Test
    public void getDogWithBreed() {
        List<Dog> expected = Arrays.asList(new Dog(testBreed1), new Dog(testBreed1));
        when(dogService.getDogsWithBreed(eq(testBreed1))).thenReturn(expected);
        ResponseEntity<String> resp = dogResource.getDogsWithBreed(testBreed1);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(GSON.toJson(expected), resp.getBody());
    }

    @Test
    public void getDogWithBreedNotFound() {
        List<Dog> expected = Collections.emptyList();
        when(dogService.getDogsWithBreed(eq(testBreed1))).thenReturn(expected);
        ResponseEntity<String> resp = dogResource.getDogsWithBreed(testBreed1);
        assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
    }
}
