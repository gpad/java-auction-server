package com.gpad.auctionserver.resources;

/**
 * Created by gpad on 17/11/18.
 */

import com.codahale.metrics.annotation.Timed;
import com.gpad.auctionserver.api.Auction;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

@Path("/version")
@Produces(MediaType.APPLICATION_JSON)
public class VersionResource {

    private String version;

    public VersionResource(String version) {
        this.version = version;
    }

    @GET
    @Timed
    public String version() {
        return version;
    }

}