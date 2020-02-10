package ua.training.game.dao;


import ua.training.game.dao.impl.JDBCDaoFactory;

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
    public abstract QuestionDao createQuestionDao();
    public abstract AppealDao createAppealDao();
//    public abstract HistoryDao createHistoryDao();

}
