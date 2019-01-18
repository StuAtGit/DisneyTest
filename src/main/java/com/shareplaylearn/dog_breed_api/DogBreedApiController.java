package com.shareplaylearn.dog_breed_api;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jmx.JmxReporter;
import com.shareplaylearn.dog_breed_api.daos.DogDao;
import com.shareplaylearn.dog_breed_api.models.Dog;
import com.shareplaylearn.dog_breed_api.resources.DogResource;
import com.shareplaylearn.dog_breed_api.services.DogService;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;

import javax.ws.rs.Path;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DogBreedApiController implements InitializingBean {

    // Resources to the different files we need to load.
    @Value("classpath:data/labrador.txt")
    private Resource labradors;

    @Value("classpath:data/pug.txt")
    private Resource pugs;

    @Value("classpath:data/retriever.txt")
    private Resource retrievers;

    @Value("classpath:data/yorkie.txt")
    private Resource yorkies;

    //Resource as in REST resource
    private DogResource dogResource;

    public DogBreedApiController() throws IOException {
        this.dogResource = dogResource();
    }

    @Bean
    public DogResource dogResource() throws IOException {
        return new DogResource(dogService(), metricRegistry());
    }

    @Bean
    public DogService dogService() throws IOException {
        return new DogService(DogDb(), metricRegistry());
    }

    @Bean
    public Jdbi DogDb() throws IOException {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:DogDb;db_close_delay=-1");
        jdbi.installPlugin(new SqlObjectPlugin());
        return jdbi;
    }

    @Bean
    public MetricRegistry metricRegistry() {
        MetricRegistry metricRegistry = new MetricRegistry();
        JmxReporter reporter = JmxReporter.forRegistry(metricRegistry).build();
        reporter.start();
        return metricRegistry;
    }

    @GetMapping(path="/", produces="application/json")
    public ResponseEntity<String> getDogIndex() {
        return dogResource.getDogList();
    }

    @GetMapping(path="/dogs/{breed}", produces = "application/json")
    public ResponseEntity<String> getDogsWithBreed(
        @PathVariable("breed") String breed
    ) {
        return dogResource.getDogsWithBreed(breed);
    }

    @GetMapping(path="/breeds", produces="application/json")
    public ResponseEntity<String> getBreeds() {
        return dogResource.getBreeds();
    }

    @GetMapping(path="/dogs", produces = "application/json")
    public ResponseEntity<String> getDogsByBreed() {
        return dogResource.getDogsByBreed();
    }

    /**
     * 
     * @param dogId - the id of the dog to upvote
     * @param userId - the id of the user making this request. Actually
     *               authorizing & authenticating this user id is left to another service
     * @return
     */
    @PostMapping(path="/upvote/{dogId}/user/{userId}", produces = "application/json")
    public ResponseEntity<String> postUpVote(
        @PathVariable("dogId") String dogId,
        @PathVariable("userId") String userId
    ) {
        return dogResource.postUpVote(dogId, userId);
    }

    /**
     *
     * @param dogId - the id of the dog to downvote
     * @param userId - the id of the user making this request. Actually
     *               authorizing & authenticating this user id is left to another service
     * @return
     */
    @PostMapping(path="/downvote/{dogId}/user/{userId}", produces = "application/json")
    public ResponseEntity<String> postDownVote(
        @PathVariable("dogId") String dogId,
        @PathVariable("userId") String userId
    ) {
        return dogResource.postDownVote(dogId, userId);
    }

    @GetMapping(path="/dog/{dogId}", produces = "application/json")
    public ResponseEntity<String> getDog(
        @PathVariable("dogId") String dogId
    ) {
        return dogResource.getDog(dogId);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        PetLoader petLoader = new PetLoader(DogDb());
        petLoader.initDogDb();
        petLoader.clearDogs();
        petLoader.loadBreeds(
            Arrays.asList(
                new PetLoader.Entry("labrador", labradors),
                new PetLoader.Entry("pugs", pugs),
                new PetLoader.Entry("retrievers", retrievers),
                new PetLoader.Entry("yorkies", yorkies)
            )
        );
        petLoader.initTestUser();
    }
}
