package com.shareplaylearn.dog_breed_api;

import com.shareplaylearn.dog_breed_api.models.Dog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	@Test
	public void contextLoads() {
		assertNotNull(dogBreedApiController);
	}

	@Test
	//norm violation: most people call these testIndex(), but
	//it seems a little redundant when you already have the @Test annotation.
	//It comes in handy in DAO tests which often already have looong method names.
	public void index() {
		assertEquals(HttpStatus.OK.value(),dogBreedApiController.getDogIndex().getStatusCodeValue());
	}

	@Test
	public void getBreeds() {
		ResponseEntity<String> breeds = dogBreedApiController.getBreeds();
		assertEquals(HttpStatus.OK.value(), breeds.getStatusCodeValue());
	}

	@Test
	public void getDogsByBreed() {
		ResponseEntity<String> breeds = dogBreedApiController.getDogsByBreed();
		assertEquals(HttpStatus.OK.value(), breeds.getStatusCodeValue());
	}

	@Test
	public void postUpVote() {
		ResponseEntity<String> voteResponse = dogBreedApiController.postUpVote("1", "1");
		assertEquals(HttpStatus.OK.value(), voteResponse.getStatusCodeValue());
	}

	@Test
	public void postTwoVotes() {
		ResponseEntity<String> voteResponse = dogBreedApiController.postUpVote("1", "4");
		assertEquals(HttpStatus.OK.value(), voteResponse.getStatusCodeValue());
		ResponseEntity<String> voteResponse2 = dogBreedApiController.postUpVote("1", "4");
		assertEquals(HttpStatus.BAD_REQUEST.value(), voteResponse2.getStatusCodeValue());
	}

	@Test
	public void postUpVoteWithInvalidId() {
		ResponseEntity<String> voteResponse = dogBreedApiController.postUpVote("abac", "2");
		assertEquals(HttpStatus.BAD_REQUEST.value(), voteResponse.getStatusCodeValue());
	}

	@Test
	public void postDownVote() {
		ResponseEntity<String> voteResponse = dogBreedApiController.postDownVote("1", "3");
		assertEquals(HttpStatus.OK.value(), voteResponse.getStatusCodeValue());
	}

	@Test
	public void postDownVoteWithInvalidDogId() {
		ResponseEntity<String> voteResponse = dogBreedApiController.postDownVote("abac", "1");
		assertEquals(HttpStatus.BAD_REQUEST.value(), voteResponse.getStatusCodeValue());
	}


	@Test
	public void postDownVoteWithInvalidUserId() {
		ResponseEntity<String> voteResponse = dogBreedApiController.postDownVote("1", "abce");
		assertEquals(HttpStatus.BAD_REQUEST.value(), voteResponse.getStatusCodeValue());
	}

}

