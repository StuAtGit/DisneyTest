package com.shareplaylearn.dog_breed_api.daos;

import com.shareplaylearn.dog_breed_api.models.User;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface UserDao {
    @SqlUpdate("insert into User (hasVoted) " + " values (:hasVoted)")
    void insertUser(
        @BindBean User user
    );

    @SqlUpdate("Update User set hasVoted = 1 where id = :id")
    void markVote(@Bind("id") Integer id);

    @SqlQuery("select * from User where id = :id")
    @RegisterBeanMapper(User.class)
    User getUser(@Bind("id") Integer id);
}
