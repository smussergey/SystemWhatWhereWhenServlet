package ua.training.system_what_where_when_servlet.dao;


import ua.training.system_what_where_when_servlet.dao.impl.JDBCDaoFactory;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;



    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    DaoFactory temp = new JDBCDaoFactory();
                    daoFactory = temp;
                }
            }
        }
        return daoFactory;
    }

    public abstract UserDao createUserDao();
    public abstract GameDao createGameDao();
//    public abstract AnsweredQuestionDao createAnsweredQuestionDao();
//    public abstract AppealDao createAppealDao();
//    public abstract HistoryDao createHistoryDao();

}
