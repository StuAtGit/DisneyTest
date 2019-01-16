package com.shareplaylearn.dog_breed_api.daos;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface DogDao {
    @SqlUpdate("insert into Dog (breed) values (@breed)")
    void insertDog(@Bind("breed") String breed);
}
