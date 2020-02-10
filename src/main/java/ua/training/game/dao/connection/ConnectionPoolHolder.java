package ua.training.game.dao.connection;

import org.apache.commons.dbcp.BasicDataSource;
import ua.training.game.DBConstants;

import javax.sql.DataSource;
import java.util.ResourceBundle;

public class ConnectionPoolHolder {
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

}
