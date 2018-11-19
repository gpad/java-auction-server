package com.gpad.auctionserver;

import com.gpad.auctionserver.health.AuctionHealthCheck;
import com.gpad.auctionserver.resources.AuctionResource;
import com.gpad.auctionserver.resources.VersionResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;

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
        bootstrap.addBundle(new MigrationsBundle<AuctionServerConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(AuctionServerConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });    }

    @Override
    public void run(final AuctionServerConfiguration configuration,
                    final Environment environment) {


        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "h2");


        final AuctionHealthCheck healthCheck = new AuctionHealthCheck();
        VersionResource versionResource = new VersionResource(configuration.getAuctionServerVersion());

        environment.healthChecks().register("auction", healthCheck);
        environment.jersey().register(new AuctionResource(jdbi));
        environment.jersey().register(versionResource);
    }

}
