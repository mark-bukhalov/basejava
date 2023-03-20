package com.urise.webapp.sql;

import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;
    public SqlHelper(ConnectionFactory connectionFactory) {
            this.connectionFactory = connectionFactory;
    }

    public <T> T executeRequest(String sql, SqlRequest<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return executor.run(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
