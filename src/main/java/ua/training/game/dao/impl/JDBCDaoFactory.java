package ua.training.game.dao.impl;

import ua.training.game.dao.*;
import ua.training.game.dao.connection.ConnectionPoolHolder;

import javax.sql.DataSource;

public class JDBCDaoFactory extends DaoFactory {
    private DataSource dataSource = ConnectionPoolHolder.getDataSource();

    @Override
    public UserDao createUserDao() {
        return new JDBCUserDao();
    }

    @Override
    public GameDao createGameDao() {
        return new JDBCGameDao();
    }


    @Override
    public QuestionDao createQuestionDao() {
        return new JDBCQuestionDao();
    }

    @Override
    public AppealDao createAppealDao() {
        return new JDBCAppealDao();
    }

    //    @Override
//    public HistoryDao createHistoryDao() {
//        return new JDBCHistoryDaoImpl(getConnection());
//    }
//

}
