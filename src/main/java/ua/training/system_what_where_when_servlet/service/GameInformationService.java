package ua.training.system_what_where_when_servlet.service;

import org.apache.log4j.Logger;
import ua.training.system_what_where_when_servlet.dao.DaoFactory;
import ua.training.system_what_where_when_servlet.dao.GameDao;
import ua.training.system_what_where_when_servlet.dto.GameDTO;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GameInformationService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class);
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    private final UserService userService = new UserService();
//    private final QuestionService questionService = new QuestionService();
    private final GameService gameService = new GameService();

//
//    private final UserService userService;
//    private final QuestionService questionService;
//    private final GameService gameService;

//    public GameInformationService(UserService userService, QuestionService questionService, GameService gameService) {
////        this.userService = userService;
////        this.questionService = questionService;
////        this.gameService = gameService;
////    }


//    public Page<GameDTO> getGamesStatisticsByAllGamesAndPlayers(Pageable pageable) {
//        return gameService.findAll(pageable)
//                .map(gameService::toGameDTO);
//    }

    public List<GameDTO> getGameStatisticsByAllGamesAndPlayers() {
        LOGGER.info(String.format("GameStatisticsAndDetailsService class, getGameStatisticsByAllGamesAndPlayers method"));
        GameDao gameDao = daoFactory.createGameDao();

        return gameDao.findAll().stream()
                .map(gameService::toGameDTO)
                .sorted(Comparator.comparing(GameDTO::getDate).reversed())
                .collect(Collectors.toList());
    }
}


//    public Page<GameDTO> getGamesStatisticsForLoggedInPlayer(Pageable pageable, Principal principal) throws EntityNotFoundException {
//        User player = userService.findUserByLogin(principal.getName());
//        return gameService.findAllByFirstPlayerOrSecondPlayer(player, player, pageable)
//                .map(gameService::toGameDTO);
//    }
//
//    public GameDTO getGameFullStatisticsByIdForReferee(Long id) {
//        Game game = gameService.findById(id);
//        GameDTO gameDTO = gameService.toGameDTO(game);
//        gameDTO.setQuestionDTOs(questionService.extractQuestionDTOsFromGame(game));
//
//        return gameDTO;
//    }
//
//    public GameDTO getGameFullStatisticsByIdForPlayer(Long id) {
//        Game game = gameService.findById(id);
//        GameDTO gameDTO = gameService.toGameDTO(game);
//        gameDTO.setQuestionDTOs(questionService.extractQuestionDTOsFromGame(game));
//        gameDTO.setAppealPossible(checkIfLoggedInUserCanFileAppealAgainstGame(game));
//
//        return gameDTO;
//    }
//
//    private boolean checkIfLoggedInUserCanFileAppealAgainstGame(Game game) {
//        return checkIfLoggedInUserCanFileAppealAgainstGameBecauseOfTime(game)
//                && (!checkIfLoggedInUserFiledAppealOnThisGame(game));
//    }
//
//    private boolean checkIfLoggedInUserCanFileAppealAgainstGameBecauseOfTime(Game game) {
//        return game.getDate().isEqual(LocalDate.now());
//    }
//
//    private boolean checkIfLoggedInUserFiledAppealOnThisGame(Game game) {
//        return game.getAppeals().stream()
//                .anyMatch(appeal -> appeal.getUser().equals(userService.findLoggedIndUser()));
//    }
//}


//    public List<GameDTO> getGameStatisticsByAllGamesAndPlayers() {
//        try (GameDao gameDao = daoFactory.createGameDao()) {
//            List<Game> games = gameDao.findAll();
//            LOGGER.info(String.format("GameStatisticsAndDetailsService class, getGameStatisticsByAllGamesAndPlayers method, were found %s games", games.size()));
//
//            return games.stream()
//                    .map(gameDTOService::toGameDTO)
//                    .sorted(Comparator.comparing(GameDTO::getDate).reversed())
//                    .collect(Collectors.toList());
//        } catch (Exception e) {
//            LOGGER.error("Exception in GameStatisticsAndDetailsService class, getGameStatisticsByAllGamesAndPlayers method.", e);
//        }
//        return null; //TODO correct
//    }
//
//
//    public List<GameDTO> getGamesStatisticsByLoggedInPlayer(String username) {
//        try (GameDao gameDao = daoFactory.createGameDao()) {
//            List<Game> games = gameDao.findAll().stream() //TODO improve query
//                    .filter(game -> game.getUsers().stream()
//                            .anyMatch(user -> user.getEmail().equals(username)))
//                    .collect(Collectors.toList()); // improve query
//            LOGGER.info(String.format("GameStatisticsAndDetailsService class, getGamesStatisticsByLoggedInPlayer method, were found %s games", games.size()));
//
//            return games.stream()
//                    .map(gameDTOService::toGameDTO)
//                    .sorted(Comparator.comparing(GameDTO::getDate).reversed())
//                    .collect(Collectors.toList());
//        } catch (Exception e) {
//            LOGGER.error("Exception in GameStatisticsAndDetailsService class, getGamesStatisticsByLoggedInPlayer method.", e);
//        }
//        return null; //TODO correct
//    }
//
//
//    //    TODO forbid logged user to see not his game results
//    public GameDTO getGameFullStatisticsById(int id) {
//        LOGGER.info("GameStatisticsAndDetailsService class, getGameFullStatisticsById method is executing");
//        Game game = null; //TODO
//        try (GameDao gameDao = daoFactory.createGameDao()) {
//            game = gameDao.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Game with id = %d was not found", id)));
//            LOGGER.info(String.format("GameStatisticsAndDetailsService class: getGameFullStatisticsById method: game with id = %d", game.getId()));
//        } catch (Exception e) { //TODO
//            LOGGER.error("GameStatisticsAndDetailsService class, getGameFullStatisticsById method: game was not found by id=" + id);
//        }
//        GameDTO gameDTO = gameDTOService.toGameDTO(game);
//
//        LOGGER.info(String.format("GameStatisticsAndDetailsService class, getGameFullStatisticsById method: game with id =  %d has %d answeredQuestions", id, game.getQuestions().size()));
//        List<QuestionDTO> answeredQuestions = game.getQuestions().stream()
//                .map(questionService::toAnsweredQuestionDTO)
//                .collect(Collectors.toList());
//
//        gameDTO.setQuestionDTOS(answeredQuestions);
////            gameDTO.setAppealPossible(checkIfLoggedUserCanFileAppealAgainstGame(game));
//
//        return gameDTO;
//    }
//
//
//    public GameDTO getGameFullStatisticsByIdAndUsername(int id, String username) {
//
//        LOGGER.info("GameStatisticsAndDetailsService class, getGameFullStatisticsByIdAndUsername method is executing");
//        Game game = null; //TODO
//        try (GameDao gameDao = daoFactory.createGameDao()) {
//            game = gameDao.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Game with id = %d was not found", id)));
//            LOGGER.info(String.format("GameStatisticsAndDetailsService class: getGameFullStatisticsByIdAndUsername method: game with id = %d", game.getId()));
//        } catch (Exception e) { //TODO
//            LOGGER.error("GameStatisticsAndDetailsService class, getGameFullStatisticsByIdAndUsername method: game was not found by id=" + id);
//        }
//        GameDTO gameDTO = gameDTOService.toGameDTO(game);
//
//        LOGGER.info(String.format("GameStatisticsAndDetailsService class, getGameFullStatisticsByIdAndUsername method: game with id =  %d has %d answeredQuestions", id, game.getQuestions().size()));
//        List<QuestionDTO> answeredQuestions = game.getQuestions().stream()
//                .map(questionService::toAnsweredQuestionDTO)
//                .collect(Collectors.toList());
//
//        gameDTO.setQuestionDTOS(answeredQuestions);
//        gameDTO.setAppealPossible(checkIfLoggedUserCanFileAppealAgainstGame(game, username));
//
//        return gameDTO;
//    }
//
//    //        // TODO improve this method
//    private boolean checkIfLoggedUserCanFileAppealAgainstGame(Game game, String username) {
//        if (game.getAppeals().isEmpty()) {
//            return true;
//        } else {
//            return !game.getAppeals().stream()
//                    .filter(appeal -> appeal.getUser().getEmail().equals(username))
//                    .findAny()
//                    .isPresent();
//        }
//    }
//
//
//    //TODO check/allow only user's games
//    public GameDTO getGameFullStatisticsByIdForAppealForm(int id, String username) {
//        User loggedUser = ServiceFactory.getInstance()
//                .getUserService()
//                .findByUsername(username)
//                .orElseThrow(() -> new EntityNotFoundException("Can not find user with username = " + username));
//
//
//        LOGGER.info("GameStatisticsAndDetailsService class, getGameFullStatisticsByIdForAppealForm method is executing");
//        Game game = null; //TODO
//        try (GameDao gameDao = daoFactory.createGameDao()) {
//            game = gameDao.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Game with id = %d was not found", id)));
//            LOGGER.info(String.format("GameStatisticsAndDetailsService class: getGameFullStatisticsByIdForAppealForm method: game with id = %d", game.getId()));
//        } catch (Exception e) { //TODO
//            LOGGER.error("GameStatisticsAndDetailsService class, getGameFullStatisticsByIdForAppealForm method: game was not found by id=" + id);
//        }
//        GameDTO gameDTO = gameDTOService.toGameDTO(game);
//
//        LOGGER.info(String.format("GameStatisticsAndDetailsService class, getGameFullStatisticsByIdForAppealForm method: game with id =  %d has %d answeredQuestions", id, game.getQuestions().size()));
//        List<QuestionDTO> answeredQuestions = game.getQuestions().stream()
//                .map(questionService::toAnsweredQuestionDTO)
//                .peek(aqDTO -> {
//                    if (!aqDTO.getNameWhoGotPointEn().equals(loggedUser.getNameEn())) {
//                        aqDTO.setAppealPossible(true);
//                    } else {
//                        aqDTO.setAppealPossible(false);
//                    }
//                })
//                .collect(Collectors.toList());
//
//        gameDTO.setQuestionDTOS(answeredQuestions);
//
//        return gameDTO;
//    }
//}
