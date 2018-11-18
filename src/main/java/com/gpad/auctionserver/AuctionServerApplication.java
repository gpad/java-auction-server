package com.gpad.auctionserver;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class AuctionServerApplication extends Application<AuctionServerConfiguration> {

    public static void main(final String[] args) throws Exception {
        new AuctionServerApplication().run(args);
    }

    @Override
    public String getName() {
        return "AuctionServer";
    }

    @Override
    public void initialize(final Bootstrap<AuctionServerConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final AuctionServerConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
