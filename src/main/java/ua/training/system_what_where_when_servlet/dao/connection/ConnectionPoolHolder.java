package ua.training.system_what_where_when_servlet.dao.connection;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import ua.training.system_what_where_when_servlet.entity.exception.DAOException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPoolHolder {
    private static final Logger LOGGER = Logger.getLogger(ConnectionPoolHolder.class);
    private static volatile DataSource dataSource; //TODO check whether to use volatile

    public static Connection getConnection() {
        Connection connection = null;
        LOGGER.info("getConnection() is executing");
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error("Exception in getting connection");
            throw new DAOException("Can not create connection ");
        }
        return connection;
    }

    public static DataSource getDataSource() {

        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    BasicDataSource ds = new BasicDataSource();
                    ds.setUrl("jdbc:mysql:// localhost:3306/system_what_where_when_db");
                    ds.setUsername("root");
                    ds.setPassword("12345");
                    ds.setMinIdle(5); // TODO move to properties
                    ds.setMaxIdle(10);
                    ds.setMaxOpenPreparedStatements(100);
                    dataSource = ds;
                }
            }
        }
        return dataSource;
    }

}
