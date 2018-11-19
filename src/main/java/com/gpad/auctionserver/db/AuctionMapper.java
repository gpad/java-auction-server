package com.gpad.auctionserver.db;

import com.gpad.auctionserver.api.Auction;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by gpad on 19/11/18.
 */
public class AuctionMapper implements RowMapper<Auction> {
    @Override
    public Auction map(ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new Auction(resultSet.getInt("id"), resultSet.getString("name"));
    }
}
