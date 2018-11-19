package com.gpad.auctionserver.db;

import com.gpad.auctionserver.api.Auction;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.BindMethods;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

/**
 * Created by gpad on 19/11/18.
 */
public interface AuctionsRepository {

    @SqlQuery("select * from auctions where id = :id")
    @RegisterRowMapper(AuctionMapper.class)
    Auction findById(@Bind("id") int id);

    @SqlUpdate("insert into auctions (id, name) values (:getId, :getName)")
    void insert(@BindMethods Auction auction);

}
