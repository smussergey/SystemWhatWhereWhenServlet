package ua.training.game.dao.impl;

import ua.training.game.dao.*;
import ua.training.game.dao.connection.ConnectionPoolHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {
    private DataSource dataSource = ConnectionPoolHolder.getDataSource();

    @Override
    public UserDao createUserDao() {
        return new JDBCUserDao(getConnection());
    }

    @Override
    public GameDao createGameDao() {
        return new JDBCGameDao(getConnection());
    }


    @Override
    public QuestionDao createQuestionDao() {
        return new JDBCQuestionDao(getConnection());
    }

    @Override
    public AppealDao createAppealDao() {
        return new JDBCAppealDao(getConnection());
    }

    //    @Override
//    public HistoryDao createHistoryDao() {
//        return new JDBCHistoryDaoImpl(getConnection());
//    }
//
    private Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e); //TODO Correct
        }
    }
}
