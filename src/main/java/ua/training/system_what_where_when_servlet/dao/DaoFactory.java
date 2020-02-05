package ua.training.system_what_where_when_servlet.dao;


import ua.training.system_what_where_when_servlet.dao.impl.AnsweredQuestionDaoImpl;
import ua.training.system_what_where_when_servlet.dao.impl.AppealDaoImpl;
import ua.training.system_what_where_when_servlet.dao.impl.DaoFactoryImpl;
import ua.training.system_what_where_when_servlet.dao.impl.GameDaoImpl;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract UserDao createUserDao();
    public abstract GameDao createGameDao();
    public abstract AnsweredQuestionDao createAnsweredQuestionDao();
    public abstract AppealDao createAppealDao();
    public abstract HistoryDao createHistoryDao();

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    DaoFactory temp = new DaoFactoryImpl();
                    daoFactory = temp;
                }
            }
        }
        return daoFactory;
    }
}
