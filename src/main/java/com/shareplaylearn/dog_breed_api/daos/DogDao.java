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
    @SqlUpdate("insert into Dog (breed) values (:breed)")
    void insertDog(@BindBean Dog dog);

    @SqlQuery("select * from Dog where breed = :breed")
    @RegisterBeanMapper(Dog.class)
    List<Dog> getDogsWithBreed(@Bind("breed") String breed);

    @SqlQuery("select * from Dog")
    @RegisterBeanMapper(Dog.class)
    List<Dog> getDogIndex();
}
