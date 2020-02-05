package ua.training.system_what_where_when_servlet.dao.impl;

import ua.training.system_what_where_when_servlet.dao.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DaoFactoryImpl extends DaoFactory {
    private DataSource dataSource = ConnectionPoolHolder.getDataSource();

    @Override
    public UserDao createUserDao() {
        return new UserDaoImpl(getConnection());
    }

    @Override
    public GameDao createGameDao() {
        return new GameDaoImpl(getConnection());
    }

    @Override
    public AnsweredQuestionDao createAnsweredQuestionDao() {
        return new AnsweredQuestionDaoImpl(getConnection());
    }

    @Override
    public AppealDao createAppealDao() {
        return new AppealDaoImpl(getConnection());
    }

    @Override
    public HistoryDao createHistoryDao() {
        return new HistoryDaoImpl(getConnection());
    }

    private Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e); //TODO Correct
        }
    }
}
