package com.gpad.auctionserver.api;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.Objects;


/**
 * Created by gpad on 18/11/18.
 */
public class Auction {


    private long id;
    private String name;


    public Auction() {
        // Jackson deserialization
    }

    public Auction(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auction auction = (Auction) o;
        return id == auction.id &&
                Objects.equals(name, auction.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .toString();
    }
}