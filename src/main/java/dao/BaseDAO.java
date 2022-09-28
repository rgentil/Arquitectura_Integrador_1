package dao;

import factory.DAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDAO<T> {

    protected void closeConnection(Connection connection,
                                   PreparedStatement st,
                                   ResultSet rs) throws SQLException {
        if (st != null && !st.isClosed()) {
            st.close();
        }
        if (rs != null && !rs.isClosed()) {
            rs.close();
        }
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    protected void runStatement(String statement, DAOFactory factory) throws SQLException{
        Connection connection = null;
        try {
            connection = factory.createConnection();
            connection.prepareStatement(statement).execute();
            connection.commit();
        } finally {
            closeConnection(connection,null, null);
        }
    }
}
