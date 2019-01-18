package com.shareplaylearn.dog_breed_api.models;

public class User {
    Integer id;
    Integer hasVoted;

    public User() {
    }

    public Integer getId() {
        return id == null ? -1 : id;
    }

    public void setId(Integer id) {
        this.id = id == null ? -1 : id;
    }

    public Integer getHasVoted() {
        return hasVoted == null ? 0 : hasVoted;
    }

    public void setHasVoted(Integer hasVoted) {
        this.hasVoted = hasVoted == null ? 0 : hasVoted;
    }
}
