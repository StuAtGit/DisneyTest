package com.shareplaylearn.dog_breed_api;

import com.shareplaylearn.dog_breed_api.models.Dog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DogBreedApiTests {

	@Autowired
	private DogBreedApiController dogBreedApiController;
    private static final List<Dog> defaultBreedIndex = new ArrayList<>(
            Arrays.asList(
                    new Dog("French Bulldog"), new Dog("German Shepard")
            )
    );

	@Test
	public void contextLoads() {
		assertNotNull(dogBreedApiController);
	}

	@Test
	//norm violation: most people call these testIndex(), but
	//it seems a little redundant when you already have the @Test annotation.
	//It comes in handy in DAO tests which often already have looong method names.
	public void index() {
		assertEquals(defaultBreedIndex, dogBreedApiController.getDogBreedIndex());
	}

}

