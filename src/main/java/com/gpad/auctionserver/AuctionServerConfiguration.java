package com.gpad.auctionserver;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AuctionServerConfiguration extends Configuration {
    private String auctionServerVersion;

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
