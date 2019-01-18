package com.shareplaylearn.dog_breed_api.daos;

import com.shareplaylearn.dog_breed_api.models.Dog;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

import java.util.List;

public interface DogDao {
    /**
     */
    @SqlUpdate("insert into Dog (registeredName,petName,breed,pictureUrl,thumbnailUrl) " +
        "values (:registeredName,:petName,:breed,:pictureUrl,:thumbnailUrl)")
    void insertDog(
        @BindBean Dog dog
//        @Bind("registeredName") String registeredName,
//        @Bind("petName") String petName,
//        @Bind("breed") String breed,
//        @Bind("pictureUrl") String pictureUrl,
//        @Bind("thumbnailUrl") String thumbnailUrl
    );

    @SqlQuery("select * from Dog where breed = :breed")
    @RegisterBeanMapper(Dog.class)
    List<Dog> getDogsWithBreed(@Bind("breed") String breed);

    @SqlQuery("select * from Dog")
    @RegisterBeanMapper(Dog.class)
    List<Dog> getDogIndex();

    @SqlUpdate("delete from Dog")
    void clearData();
}
