package ua.training.game.service;

import org.apache.log4j.Logger;
import ua.training.game.dao.DaoFactory;
import ua.training.game.domain.*;
import ua.training.game.enums.AppealStage;
import ua.training.game.exception.EntityNotFoundException;
import ua.training.game.web.dto.GameDTO;
import ua.training.game.web.dto.QuestionDTO;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//package ua.training.system_what_where_when_servlet.service;
//
//
//import org.apache.log4j.Logger;
//import ua.training.system_what_where_when_servlet.dao.DaoFactory;
//import ua.training.system_what_where_when_servlet.entity.Question;
//import ua.training.system_what_where_when_servlet.entity.Appeal;
//import ua.training.system_what_where_when_servlet.entity.AppealStage;
//import ua.training.system_what_where_when_servlet.entity.Game;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
public class AppealService {
    private static final Logger LOGGER = Logger.getLogger(GameService.class);

    private final UserService userService = new UserService();
    private final GameService gameService = new GameService();
    private final QuestionService questionService = new QuestionService();
    private final AppealedQuestionService appealedQuestionService = new AppealedQuestionService();
    DaoFactory daoFactory = DaoFactory.getInstance();

    //    private final UserService userService;
//    private final AppealRepository appealRepository;
//    private final GameService gameService;
//    private final QuestionService questionService;
//    private final AppealedQuestionService appealedQuestionService;
//
//    public AppealService(UserService userService, AppealRepository appealRepository, GameService gameService, QuestionService questionService, AppealedQuestionService appealedQuestionService) {
//        this.userService = userService;
//        this.appealRepository = appealRepository;
//        this.gameService = gameService;
//        this.questionService = questionService;
//        this.appealedQuestionService = appealedQuestionService;
//    }
//
    public GameDTO getGameInformationByIdForFileAppealForm(Integer gameId, String username) {
        User loggedInUser = userService.findByUsername(username);
        Game appealedGame = gameService.findById(gameId);
        GameDTO gameDTO = gameService.toGameDTO(appealedGame);
        gameDTO.setQuestionDTOs(getQuestionDTOsForAppealForm(loggedInUser, appealedGame));

        return gameDTO;

    }

    private List<QuestionDTO> getQuestionDTOsForAppealForm(User loggedInUser, Game appealedGame) {
        return questionService.extractQuestionDTOsFromGame(appealedGame).stream()
                .peek(questionDTO -> {
                    if (!questionDTO.getNameWhoGotPointEn().equals(loggedInUser.getNameEn())) {
                        questionDTO.setAppealPossible(true);
                    } else {
                        questionDTO.setAppealPossible(false);
                    }
                })
                .collect(Collectors.toList());
    }


    //
//
    public void fileAppealAgainstGameQuestions(int[] questionsThatWereAppealedIds, String username) {
        LOGGER.info("in fileAppealAgainstGameQuestions() -qustions ids  successfully were got");
        List<Question> questionsThatWereAppealed = Arrays.stream(questionsThatWereAppealedIds)
                .peek(id -> System.out.println("----------------------id=" + id))
                .mapToObj(questionService::findById) // TODO improve this method: too many calls to db (use "IN")
                .peek(question -> System.out.println("----------------------question.id=" + question.getId()))
                .collect(Collectors.toList());

        Game appealedGame = getAppealedGame(questionsThatWereAppealed);

//        log.info("fileAppealAgainstGameAnsweredQuestions() - appealedGame: {} successfully was find", appealedGame.getId());

        Appeal appeal = new Appeal();
        appeal.setDate(LocalDate.now());
        appeal.setGame(appealedGame);
        appeal.setUser(userService.findByUsername(username));
        appeal.setAppealStage(AppealStage.FILED);

        appeal.setAppealedQuestions(getAppealedQuestions(questionsThatWereAppealed, appeal));

        save(appeal);
    }

    private Game getAppealedGame(List<Question> appealedQuestions) {
        Integer appealedGameId = appealedQuestions.stream()
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException("In getAppealedGame: Can not fond game with any appealedQuestions"))
                .getGame().getId();
        return gameService.findById(appealedGameId);
    }

    private List<AppealedQuestion> getAppealedQuestions(List<Question> questions, Appeal appeal) {
        return questions.stream()
                .map(question -> appealedQuestionService.toAppealedQuestion(question, appeal))
                .collect(Collectors.toList());
    }
//
//
//    @Transactional
//    public Appeal save(Appeal appeal) {
//        return appealRepository.save(appeal);
//    }

    public void save(Appeal appeal) {
        daoFactory.createAppealDao().create(appeal);
    }


//        public GameDTO getGameInformationByIdForApprovalToAppealForm(Long gameId) {
//        Game appealedGame = gameService.findById(gameId);
//        GameDTO gameDTO = gameService.toGameDTO(appealedGame);
//        gameDTO.setQuestionDTOs(questionService.extractQuestionDTOsFromGame(appealedGame));
//
//        return gameDTO;
//
//    }

    // TODO add validation
    public GameDTO getGameInformationByIdForApprovalToAppealForm(Integer gameId) {
        Game appealedGame = gameService.findById(gameId);
        GameDTO gameDTO = gameService.toGameDTO(appealedGame);
        gameDTO.setQuestionDTOs(questionService.extractQuestionDTOsFromGame(appealedGame));

        return gameDTO;
    }


    public void approveAppealsAgainstGameAppealedQuestions(int[] approvedQuestionIds) {
        LOGGER.info("in approveAppealsAgainstGameAppealedQuestions()  successfully was got");

        List<Question> approvedQuestions = Arrays.stream(approvedQuestionIds)
                .mapToObj(questionService::findById) // TODO improve this method: too many calls to db (use "IN")
                .collect(Collectors.toList());

        Game appealedGame = getAppealedGame(approvedQuestions);
        List<Appeal> consideredAppeals = appealedGame.getAppeals();

        changeUserWhoGotPointInApprovedQuestions(approvedQuestions, appealedGame);
        // TODO maybe move to separate method and update field through dirty checking
        changeAppealStageInConsideredAppeals(consideredAppeals);

        saveApprovedAppealsAgainstGameQuestions(approvedQuestions, consideredAppeals);
    }

    private void changeUserWhoGotPointInApprovedQuestions(List<Question> approvedQuestions, Game appealedGame) {
        approvedQuestions.stream().forEach(ap -> System.out.println("----------------approvedQuestion: " + ap.getId()));

        System.out.println("appealedGame has appeals:" + appealedGame.getAppeals().size());
        System.out.println("appealedGame has appeals(0) whichhas appealedQuestions:" + appealedGame.getAppeals().get(0).getAppealedQuestions().size());


        List<AppealedQuestion> appealedQuestionsWhichWereApproved = appealedGame.getAppeals().stream()
                .flatMap(appeal -> appeal.getAppealedQuestions().stream())
                .filter(appealedQuestion -> approvedQuestions.contains(appealedQuestion.getQuestion()))
                .collect(Collectors.toList());

        approvedQuestions.stream()
                .forEach(approvedQuestion -> approvedQuestion.setUserWhoGotPoint(appealedQuestionsWhichWereApproved.stream()
                        .filter(appealedQuestionWhichWereApproved -> appealedQuestionWhichWereApproved.getQuestion().equals(approvedQuestion))
                        .findAny()
                        .orElseThrow(() -> new EntityNotFoundException("There is no any AppealedQuestion which was approved"))
                        .getAppeal()
                        .getUser()));
    }

    private void changeAppealStageInConsideredAppeals(List<Appeal> consideredAppeals) {
        consideredAppeals.stream()
                .forEach(appeal -> appeal.setAppealStage(AppealStage.CONSIDERED));
    }


    public void saveApprovedAppealsAgainstGameQuestions(List<Question> approvedQuestions, List<Appeal> consideredAppeals) {
        questionService.update(approvedQuestions);
        updateAll(consideredAppeals);
    }


    public void updateAll(List<Appeal> appeals) {
        DaoFactory.getInstance().createAppealDao().update(appeals);
    }
}
//}


//    }
//    private static final Logger LOGGER = Logger.getLogger(UserService.class);
//    private final QuestionService questionService;
//    private final UserService userService;
//    private final DaoFactory daoFactory;
//
//
//    public AppealService() {
//        ServiceFactory serviceFactory = ServiceFactory.getInstance();
//        this.questionService = serviceFactory.getAnsweredQuestionService();
//        this.userService = serviceFactory.getUserService();
//        this.daoFactory = DaoFactory.getInstance();
//    }
//
//
//    public void fileAppealAgainstGameAnsweredQuestions(int[] appealedQuestionsIds, String username) {
//
//        LOGGER.info("AppealServise class: fileAppealAgainstGameAnsweredQuestions method: successfully were got in amount:" + appealedQuestionsIds.length);
//        List<Question> appealedQuestions = Arrays.stream(appealedQuestionsIds)
//                .mapToObj(questionService::findAnsweredQuestionById) // TODO improve this method: too many calls to db (use "IN")
//                .collect(Collectors.toList());
//
//        // TODO check if null is impossible
//        Game appealedGame = appealedQuestions.stream()
//                .findAny()
//                .get()
//                .getGame();
//
//        LOGGER.info(String.format("in AppealService: fileAppealAgainstGameAnsweredQuestions() - appealedGame: %s successfully was find", appealedGame.getId()));
//
//        Appeal appeal = new Appeal();
//        appeal.setDate(LocalDate.now());
//        appeal.setUser(userService.findByUsername(username).get()); //TODO check get
//        appeal.setGame(appealedGame);
//        appeal.getAppealedQuestions().addAll(appealedQuestions);
//        appeal.setAppealStage(AppealStage.FILED);
//        save(appeal);
//    }
//
//
//    public void save(Appeal appeal) {
//        daoFactory.createAppealDao().create(appeal);
//    }
//
//    public void approveAppealsAgainstGameAnsweredQuestions(int[] aprovedQuestionsIds) {
//        LOGGER.info("AppealServise class: fileAppealAgainstGameAnsweredQuestions method: successfully were got in amount:" + aprovedQuestionsIds.length);
//        List<Question> questionsWithApprovedAppeal = Arrays.stream(aprovedQuestionsIds)
//                .mapToObj(questionService::findAnsweredQuestionById)// TODO improve this method: too many calls to db (use "IN")
//                .collect(Collectors.toList());
//
////        answeredQuestionService.saveAll(answeredQuestionsWithApprovedAppeal.stream()
////                .peek(aq -> aq.setUserWhoGotPoint(aq.getAppeal().getUser()))
////                .collect(Collectors.toList()));
//
//        List<Question> approvedQuestions = questionsWithApprovedAppeal.stream()
//                .peek(aq -> aq.setUserWhoGotPoint(aq.getAppeal().getUser()))
//                .collect(Collectors.toList());
//
//        approvedQuestions.stream() //TODO redo in transaction and batch
//                .forEach(aq -> DaoFactory.getInstance().createAnsweredQuestionDao().update(aq));
//
//
//        Game appealedGame = questionsWithApprovedAppeal.stream()
//                .findAny()
//                .get()
//                .getGame();
//
//        // maybe move to separate method and update field through dirty checking
//
//        List<Appeal> consideredAppeals = (appealedGame.getAppeals().stream()
//                .peek(appeal -> appeal.setAppealStage(AppealStage.CONSIDERED))
//                .collect(Collectors.toList()));
//
//
//        //TODO redo in transaction and batch
//        consideredAppeals.stream()
//                .forEach(appeal -> DaoFactory.getInstance().createAppealDao().update(appeal));
//    }
//}
//
//
//
//
//}
//}