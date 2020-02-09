package ua.training.system_what_where_when_servlet.dao.impl;

import ua.training.system_what_where_when_servlet.dao.DaoFactory;
import ua.training.system_what_where_when_servlet.dao.GameDao;
import ua.training.system_what_where_when_servlet.dao.UserDao;
import ua.training.system_what_where_when_servlet.dao.connection.ConnectionPoolHolder;
import ua.training.system_what_where_when_servlet.entity.exception.DAOException;

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

    //    @Override
//    public GameDao createGameDao() {
//        return new JDBCGameDaoImpl(getConnection());
//    }
//
//    @Override
//    public AnsweredQuestionDao createQuestionDao() {
//        return new JDBCAnsweredQuestionDaoImpl(getConnection());
//    }
//
//    @Override
//    public AppealDao createAppealDao() {
//        return new AppealDaoImpl(getConnection());
//    }
//
//    @Override
//    public HistoryDao createHistoryDao() {
//        return new JDBCHistoryDaoImpl(getConnection());
//    }
//
    private Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new DAOException("Can not get Connection"); //TODO Correct
        }
    }
}
