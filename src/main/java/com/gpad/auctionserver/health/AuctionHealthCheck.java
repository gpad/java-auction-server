package com.gpad.auctionserver.health;

import com.codahale.metrics.health.HealthCheck;

/**
 * Created by gpad on 17/11/18.
 */
public class AuctionHealthCheck extends HealthCheck {

    public AuctionHealthCheck() {

    }

    @Override
    protected Result check() throws Exception {
        if (false) {
            return Result.unhealthy("auction is unhealthy");
        }
        return Result.healthy();
    }
}
