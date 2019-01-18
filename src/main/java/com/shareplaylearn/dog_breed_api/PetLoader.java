package com.shareplaylearn.dog_breed_api;

import com.shareplaylearn.dog_breed_api.daos.DogDao;
import com.shareplaylearn.dog_breed_api.models.Dog;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.List;

public class PetLoader {
    private final Jdbi jdbi;
    private static final Logger LOG = LoggerFactory.getLogger(PetLoader.class);

    public static class Entry {
        private final String breed;
        private final Resource resource;

        public Entry(String breed, Resource resource) {
            this.breed = breed;
            this.resource = resource;
        }

        public String getBreed() {
            return breed;
        }

        public Resource getResource() {
            return resource;
        }
    }

    public PetLoader(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public void loadBreeds(List<Entry> breedDataList) throws IOException {
        for(Entry b : breedDataList) {
            loadBreed(b.getBreed(), b.getResource());
        }
    }

    /**
     * Reads the list of dogs in a category and (eventually) add
     * them to the data source.
     * @param breed The breed that we are loading.
     * @param source The file holding the breeds.
     * @throws IOException In case things go horribly, horribly wrong.
     */
    public void loadBreed(String breed, Resource source) throws IOException {
        if(breed == null || source == null) {
            String message = "Passed a null breed or resource to loadBreed.";
            LOG.error(message);
            throw new NullPointerException(message);
        }
        try (
            BufferedReader br = new BufferedReader(
                new InputStreamReader(source.getInputStream())
            )
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                String pictureUrl = line.trim();
                jdbi.useHandle(
                    handle -> {
                        DogDao dogDao = handle.attach(DogDao.class);
                        try {
                            Dog dog = new Dog(breed, pictureUrl);
                            dogDao.insertDog(
                                dog
//                                dog.getRegisteredName(),
//                                dog.getPetName(),
//                                dog.getBreed(),
//                                dog.getPictureUrl(),
//                                dog.getThumbnailUrl()
                            );
                        } catch(MalformedURLException e) {
                            LOG.info("Encountered invalid URL when loading dogs: " + e.getMessage());
                            LOG.info("Skipping entry.");
                        }
                    }
                );
            }
        }
    }

    public void clearDogs() {
        jdbi.useHandle(
            h -> {
                DogDao dogDao = h.attach(DogDao.class);
                dogDao.clearData();
            }
        );
    }

    public void initDogDb() {
        jdbi.useHandle(
            handle -> {
                handle.execute(
                    "create table if not exists Dog (" +
                        "id integer primary key auto_increment," +
                        "registeredName varchar(50)," +
                        "petName varchar(256)," +
                        "breed varchar(256)," +
                        "pictureUrl varchar(256)," +
                        "thumbnailUrl varchar(256)" +
                        ");");
            }
        );
    }
}
