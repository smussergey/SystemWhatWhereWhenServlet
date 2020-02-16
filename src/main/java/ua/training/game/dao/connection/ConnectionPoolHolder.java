package ua.training.game.dao.connection;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import ua.training.game.dao.DBConstants;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionPoolHolder {
    private static final Logger LOGGER = Logger.getLogger(ConnectionPoolHolder.class);
    private static volatile DataSource dataSource;
    private static ResourceBundle bundle = ResourceBundle.getBundle("db");

    public static DataSource getDataSource() {

        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    BasicDataSource ds = new BasicDataSource();
                    ds.setUrl(bundle.getString(DBConstants.DB_URL));
                    ds.setUsername(bundle.getString(DBConstants.DB_USERNAME));
                    ds.setPassword(bundle.getString(DBConstants.DB_PASSWORD));
                    ds.setMinIdle(Integer.valueOf(bundle.getString(DBConstants.DB_MIN_IDLE))); // TODO CHECK Integer
                    ds.setMaxIdle(Integer.valueOf(bundle.getString(DBConstants.DB_MAX_IDLE)));
                    ds.setMaxOpenPreparedStatements(Integer.valueOf(bundle.getString(DBConstants.DB_MAX_OPEN_PREPARED_STATEMENTS)));
                    dataSource = ds;
                }
            }
        }
        return dataSource;
    }


    public static Connection getConnection() {
        try {
            Connection connection = dataSource.getConnection();
            return connection;
        } catch (SQLException ex) {
            LOGGER.error("Unable to get connection to DB " + ex.getMessage());
            throw new RuntimeException(ex); //TODO check
        }
    }

}
