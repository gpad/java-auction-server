package com.gpad.auctionserver.resources;

/**
 * Created by gpad on 17/11/18.
 */

import com.codahale.metrics.annotation.Timed;
import com.gpad.auctionserver.api.Auction;
import com.gpad.auctionserver.db.AuctionsRepository;
import org.jdbi.v3.core.Jdbi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

@Path("/auctions")
@Produces(MediaType.APPLICATION_JSON)
public class AuctionResource {

    private Jdbi jdbi;

    public AuctionResource(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @GET
    @Timed
    public List<Auction> all() {
        return Arrays.asList(new Auction(1, "1"), new Auction(2, "2"));
    }

    @GET
    @Timed
    @Path("/{id}")
    public Auction get(@PathParam("id") int id) {

        AuctionsRepository repository = jdbi.onDemand(AuctionsRepository.class);
        return repository.findById(id);

//        return new Auction(id, String.format("%d", id));
    }
}