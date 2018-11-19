package com.gpad.auctionserver;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.db.DataSourceFactory;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class AuctionServerConfiguration extends Configuration {
    @NotEmpty
    private String auctionServerVersion;

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
    }

    @JsonProperty
    public String getAuctionServerVersion() {
        return auctionServerVersion;
    }

    @JsonProperty
    public void setAuctionServerVersion(String version) {
        this.auctionServerVersion = version;
    }
    // TODO: implement service configuration
}
