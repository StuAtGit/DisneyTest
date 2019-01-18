package com.shareplaylearn.dog_breed_api;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jmx.JmxReporter;
import com.shareplaylearn.dog_breed_api.daos.DogDao;
import com.shareplaylearn.dog_breed_api.models.Dog;
import com.shareplaylearn.dog_breed_api.resources.DogResource;
import com.shareplaylearn.dog_breed_api.services.DogService;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DogBreedApiController {

    private DogResource dogResource;

    public DogBreedApiController() {
        this.dogResource = dogResource();
    }

    @Bean
    public DogResource dogResource() {
        return new DogResource(dogService(), metricRegistry());
    }

    @Bean
    public DogService dogService() {
        return new DogService(DogDb(), metricRegistry());
    }

    @Bean
    public Jdbi DogDb() {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:DogDb;db_close_delay=-1");
        jdbi.installPlugin(new SqlObjectPlugin());
        initDogDb(jdbi);
        return jdbi;
    }

    @Bean
    public MetricRegistry metricRegistry() {
        MetricRegistry metricRegistry = new MetricRegistry();
        JmxReporter reporter = JmxReporter.forRegistry(metricRegistry).build();
        reporter.start();
        return metricRegistry;
    }

    private void initDogDb(Jdbi jdbi) {
        jdbi.useHandle(
            handle -> {
                handle.execute(
                    "create table if not exists Dog (" +
                        "id integer primary key auto_increment," +
                        "breed varchar(256)," +
                        ");");
                DogDao dogDao = handle.attach(DogDao.class);
                dogDao.insertDog(new Dog("French Bulldog"));
                dogDao.insertDog(new Dog("Schnauzer"));
                System.out.println(dogDao.getDogIndex());
            }
        );
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
}
