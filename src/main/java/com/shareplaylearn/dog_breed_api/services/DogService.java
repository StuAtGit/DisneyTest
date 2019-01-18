package com.shareplaylearn.dog_breed_api.services;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.shareplaylearn.dog_breed_api.daos.UserDao;
import com.shareplaylearn.dog_breed_api.daos.DogDao;
import com.shareplaylearn.dog_breed_api.models.User;
import com.shareplaylearn.dog_breed_api.models.Dog;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DogService {
    private static final Logger LOG = LoggerFactory.getLogger(DogService.class);
    private final Jdbi jdbi;
    private final MetricRegistry metricRegistry;
    //public is useful for use with tests/mocking
    public static final String DOG_BREED_LOOKUP_TIMER = "dogBreedLookupTimer-service";
    private final Timer dogBreedLookupTimer;
    public static final String DOG_INDEX_LIST_TIMER = "dogIndexListTimer-service";
    private final Timer dogIndexListTimer;
    public static final String DOG_BREED_LIST_TIMER = "dogBreedListTimer-service";
    private final Timer dogBreedListTimer;

    public DogService(Jdbi jdbi, MetricRegistry metricRegistry) {
        this.jdbi = jdbi;
        this.metricRegistry = metricRegistry;
        this.dogBreedLookupTimer = this.metricRegistry.timer(
            DOG_BREED_LOOKUP_TIMER
        );
        this.dogIndexListTimer = this.metricRegistry.timer(
            DOG_INDEX_LIST_TIMER
        );
        this.dogBreedListTimer = this.metricRegistry.timer(
            DOG_BREED_LIST_TIMER
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

    public HashMap<String,List<Dog>> getDogsGroupedByBreed() {
        final List<String> breeds = new ArrayList<>();
        final HashMap<String,List<Dog>> dogsByBreed = new HashMap<>();
        this.jdbi.useHandle(
            (h) -> {
                DogDao dao = h.attach(DogDao.class);
                breeds.addAll(dao.getBreeds());
                for (String breed : breeds) {
                    dogsByBreed.put(breed, dao.getDogsWithBreed(breed));
                }
            }
        );
        return dogsByBreed;
    }

    public List<String> getBreeds() {
        return this.jdbi.withHandle(
            (h) -> {
                Timer.Context ctxt = this.dogBreedListTimer.time();
                List<String> ret;
                try {
                    ret = h.attach(DogDao.class).getBreeds();
                } finally {
                    ctxt.stop();
                }
                return ret;
            }
        );
    }

    private boolean clientHasVoted(UserDao userDao, String userId) {
        User c = userDao.getUser(Integer.parseInt(userId));
        if(c == null) {
            throw new IllegalArgumentException("Invalid user: " + userId);
        } else return c.getHasVoted().equals(1);
    }

    public Integer addVote(String dogId, String userId) {
        return this.jdbi.inTransaction(
            (h) -> {
                UserDao userDao = h.attach(UserDao.class);
                if(clientHasVoted(userDao, userId)) {
                    throw new IllegalArgumentException("User has already voted!");
                }
                DogDao dao = h.attach(DogDao.class);
                Dog dog = dao.getDog(Integer.parseInt(dogId));
                if(dog == null) {
                    return null;
                }
                LOG.debug("Adding vote to dog: " + dogId);

                dao.addVote(Integer.parseInt(dogId));
                userDao.markVote(Integer.parseInt(userId));
                Integer votes = dao.getDog(Integer.parseInt(dogId)).getVotes();
                return votes;
            }
        );
    }

    public void subtractVote(String dogId, String userId) {
        this.jdbi.inTransaction(
            (h) -> {
                UserDao userDao = h.attach(UserDao.class);
                if(clientHasVoted(userDao, userId)) {
                    throw new IllegalArgumentException("User has already voted!");
                }
                LOG.debug("Subtracting vote from dog: " + dogId);
                h.attach(DogDao.class).subtractVote(Integer.parseInt(dogId));
                userDao.markVote(Integer.parseInt(userId));
                return null;
            }
        );
    }

    public Dog getDog(String dogId) {
        return this.jdbi.withHandle(
            (h) -> {
                LOG.debug("Retrieving dog: " + dogId);
                return h.attach(DogDao.class).getDog(Integer.parseInt(dogId));
            }
        );
    }

    public void putDog(Dog dog) {
        this.jdbi.useHandle(
            (h) -> {
                h.attach(DogDao.class).insertDog(
                    dog
                );
            }
        );
    }
}
