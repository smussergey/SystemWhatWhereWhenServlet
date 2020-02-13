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

public class AppealService {
    private static final Logger LOGGER = Logger.getLogger(GameService.class);

    private final UserService userService = new UserService();
    private final GameService gameService = new GameService();
    private final QuestionService questionService = new QuestionService();
    private final AppealedQuestionService appealedQuestionService = new AppealedQuestionService();
    private final DaoFactory daoFactory = DaoFactory.getInstance();

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

    public void save(Appeal appeal) {
        daoFactory.createAppealDao().create(appeal);
    }

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
