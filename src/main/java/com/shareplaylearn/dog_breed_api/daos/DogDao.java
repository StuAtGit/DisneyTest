package com.shareplaylearn.dog_breed_api.daos;

import com.shareplaylearn.dog_breed_api.models.Dog;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface DogDao {
    /**
     */
    @SqlUpdate("insert into Dog (registeredName,petName,breed,pictureUrl,thumbnailUrl) " +
        "values (:registeredName,:petName,:breed,:pictureUrl,:thumbnailUrl)")
    void insertDog(
        @BindBean Dog dog
    );

    @SqlQuery("select * from Dog where id = :id")
    @RegisterBeanMapper(Dog.class)
    Dog getDog(@Bind("id") Integer id);

    @SqlQuery("select * from Dog where breed = :breed")
    @RegisterBeanMapper(Dog.class)
    List<Dog> getDogsWithBreed(@Bind("breed") String breed);

    @SqlQuery("select distinct breed from Dog")
    List<String> getBreeds();

    @SqlQuery("select * from Dog")
    @RegisterBeanMapper(Dog.class)
    List<Dog> getDogIndex();

    @SqlUpdate("delete from Dog")
    void clearData();

    @SqlUpdate("update Dog set votes = votes + 1 where id=:id")
    void addVote(@Bind("id") Integer id);

    @SqlUpdate("update Dog set votes = votes - 1 where id=:id")
    void subtractVote(@Bind("id") Integer id);
}
