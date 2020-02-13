package ua.training.game.service;

import org.apache.log4j.Logger;
import ua.training.game.dao.DaoFactory;
import ua.training.game.dao.GameDao;
import ua.training.game.domain.Appeal;
import ua.training.game.domain.Game;
import ua.training.game.domain.User;
import ua.training.game.enums.AppealStage;
import ua.training.game.exception.EntityNotFoundException;
import ua.training.game.util.ResourceBundleUtil;
import ua.training.game.web.dto.GameDTO;

import java.time.LocalDate;
import java.util.List;

public class GameService {
    private static final Logger LOGGER = Logger.getLogger(GameService.class);
    private static final String DELIMITER = ":";
    private DaoFactory daoFactory = DaoFactory.getInstance();

//    private final GameRepository gameRepository;

    public GameService() {
    }

//    public GameService(GameRepository gameRepository) {
//        this.gameRepository = gameRepository;
//    }

//    public Game findById(Long id) {
//        return gameRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Can not fond game with id: " + id));
//    }

    public Game findById(Integer id) {
        GameDao gameDao = daoFactory.createGameDao();
        LOGGER.info(String.format("In findById method id=%s", id));
        Game game = gameDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Can not fond game with id: " + id));
        LOGGER.info(String.format("In findById method game was found with id=%s", game.getId()));
        return game;
    }


//    public Page<Game> findAllByFirstPlayerOrSecondPlayer(User firstPlayer, User secondPlayer, Pageable pageable) {
//        return gameRepository.findAllByFirstPlayerOrSecondPlayer(firstPlayer, secondPlayer, pageable);
//    }

    public List<Game> findAllByFirstPlayerOrSecondPlayer(User firstPlayer, User secondPlayer) {
        GameDao gameDao = daoFactory.createGameDao();
        return gameDao.findAllByFirstPlayerOrSecondPlayer(firstPlayer, secondPlayer);
    }

//    public Page<Game> findAll(Pageable pageable) {
//        return gameRepository.findAll(pageable);
//    }

    public List<Game> findAll() {
        GameDao gameDao = daoFactory.createGameDao();
        return gameDao.findAll();
    }


//    public Page<Game> findAllByDateAfter(LocalDate localDate, Pageable pageable) {
//        return gameRepository.findAllByDateBefore(localDate, pageable);
//    }

        public List<Game> findAllByDateBefore(LocalDate localDate) {
        return daoFactory.createGameDao().findAllByDateBefore(localDate);
    }


//    @Transactional
//    public Game save(Game game) {
//        return gameRepository.save(game);
//    }

    public void save(Game game) {
        daoFactory.createGameDao().create(game);
    }

//    @Transactional
//    public void deleteGameById(Long id) {
//        gameRepository.deleteById(id);
//    }


    public GameDTO toGameDTO(Game game) {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(game.getId());
        gameDTO.setDate(game.getDate());//TODO LOCALE
        gameDTO.setFirstPlayerNameUa(game.getFirstPlayer().getNameUa());
        gameDTO.setFirstPlayerNameEn(game.getFirstPlayer().getNameEn());
        gameDTO.setSecondPlayerNameUa(game.getSecondPlayer().getNameUa());
        gameDTO.setSecondPlayerNameEn(game.getSecondPlayer().getNameEn());
        gameDTO.setScores(createScoresResultsForGameDTO(game));
        gameDTO.setAppealStage(ResourceBundleUtil.getBundleStringForAppealStage(createAppealStageForGameDTO(game).name()));
        return gameDTO;
    }


    public String createScoresResultsForGameDTO(Game game) {
        User firstPlayer = game.getFirstPlayer();

        long firstPlayerScores = game.getQuestions()
                .stream()
                .filter(question -> firstPlayer.equals(question.getUserWhoGotPoint()))
                .count();

        long secondPlayerScores = game.getQuestions().size() - firstPlayerScores;

        return firstPlayerScores + DELIMITER + secondPlayerScores;
    }

    public AppealStage createAppealStageForGameDTO(Game game) {
        return game.getAppeals().stream()
                .map(Appeal::getAppealStage)
                .findFirst()
                .orElse(AppealStage.NOT_FILED);
    }
}


//    private static final Logger LOGGER = Logger.getLogger(UserService.class);
//    private static final String DELIMITER = ":";
//
//    //TODO refactor this method
//    public GameDTO toGameDTO(Game game) {
//        LOGGER.info(String.format("GameDTOService class, toGameDTO is executing on a game with id = %d", game.getId()));
//        GameDTO gameDTO = new GameDTO();
//
//        gameDTO.setId(game.getId());
//        gameDTO.setDate(game.getDate());
//
//
//        gameDTO.setPlayerNameUa(game.getUsers().get(0).getNameUa());//TODO improve
//        gameDTO.setPlayerNameEn(game.getUsers().get(0).getNameEn());//TODO improve
//
//        if (game.getUsers().size() > 1) { //TODO improve
//            gameDTO.setOpponentNameUa(game.getUsers().get(1).getNameUa());
//            gameDTO.setOpponentNameEn(game.getUsers().get(1).getNameEn());
//        } else {
//            gameDTO.setOpponentNameUa(ResourceBundleUtil.getBundleString("games.game.statistics.text.audience"));
//            gameDTO.setOpponentNameEn(ResourceBundleUtil.getBundleString("games.game.statistics.text.audience"));
//        }
//
//        User firstPlayer = game.getUsers().get(0); //TODO correct
//        long firstPlayerScores = game.getQuestions()
//                .stream()
//                .filter(aq -> firstPlayer.equals(aq.getUserWhoGotPoint()))
//                .count();
//
//        long secondPlayerScores = game.getQuestions()
//                .stream()
//                .count() - firstPlayerScores;
//
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(firstPlayerScores);
//        stringBuilder.append(DELIMITER);//TODO move ":" to properties
//        stringBuilder.append(secondPlayerScores);
//        String scores = stringBuilder.toString();
//        gameDTO.setScores(scores);
//
//        if (game.getAppeals().isEmpty()) {
//            gameDTO.setAppealStage(ResourceBundleUtil.getBundleStringForAppealStage(AppealStage.NOT_FILED.name()));
//        } else {
//            game.getAppeals().stream()
//                    .forEach(appeal -> {
//                        if (appeal.getAppealStage().equals(AppealStage.FILED)) {
//                            gameDTO.setAppealStage(ResourceBundleUtil.getBundleStringForAppealStage(AppealStage.FILED.name()));
//                        } else {
//                            gameDTO.setAppealStage(ResourceBundleUtil.getBundleStringForAppealStage(AppealStage.CONSIDERED.name()));
//                        }
//
//                    });
//        }
//        return gameDTO;
//    }
//
//}
