//package ua.training.system_what_where_when_servlet.service;//package ua.training.system_what_where_when_servlet.service;
//
//import org.apache.log4j.Logger;
//import ua.training.system_what_where_when_servlet.dao.DaoFactory;
//import ua.training.system_what_where_when_servlet.dto.GameDTO;
//import ua.training.system_what_where_when_servlet.entity.AppealStage;
//import ua.training.system_what_where_when_servlet.entity.Game;
//import ua.training.system_what_where_when_servlet.entity.History;
//import ua.training.system_what_where_when_servlet.entity.User;
//import ua.training.system_what_where_when_servlet.entity.exception.EntityNotFoundException;
//import ua.training.system_what_where_when_servlet.util.ResourceBundleUtil;
//
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.util.Comparator;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class HistoryService {
//    private static final Logger LOGGER = Logger.getLogger(NewGameService.class);
//    private static final String DELIMITER = ":";
//
//    //    private final AppealService appealService;
////    private final GameRepository gameRepository;
////    private final HistoryRepository historyRepository;
////    private final GameService gameService;
////    private final GameDTOService gameDTOService;
//
////    public HistoryService(AppealService appealService, GameRepository gameRepository, HistoryRepository historyRepository, GameService gameService, GameDTOService gameDTOService) {
////        this.appealService = appealService;
////        this.gameRepository = gameRepository;
////        this.historyRepository = historyRepository;
////        this.gameService = gameService;
////        this.gameDTOService = gameDTOService;
////    }
//
//    private final DaoFactory daoFactory;
//
//    public HistoryService() {
//        this.daoFactory = DaoFactory.getInstance();
//    }
//
//    //    //TODO improve query to DB
////    //TODO improve to get games when appeal was not filed and 1 (or 10) date passed
////    public Set<GameDTO> getGamesWhichCanBeMovedToHistory() {
////        return appealService.findAllByAppealStage(AppealStage.CONSIDERED)
////                .stream()
////                .map(Appeal::getGame)
////                .map(gameDTOService::toGameDTO)
////                .collect(Collectors.toSet());
////    }
////TODO use specific db query!!!
//    //TODO improve query to DB
//    //TODO improve to get games when appeal was not filed and 1 (or 10) date passed
//    public List<GameDTO> getGamesWhichCanBeMovedToHistory() {
//        return DaoFactory.getInstance().createGameDao()
////                .findAllByAppealStageAndDateLaterThan(AppealStage.CONSIDERED, LocalDate.now())
//                .findAll()// TODO correct
//                .stream()
//                .peek(game -> LOGGER.info("getGamesWhichCanBeMovedToHistory method: before date filter game with id = " + game.getId()))
//                .filter(game -> game.getDate().isBefore(LocalDate.now()))
//                .peek(game -> LOGGER.info("getGamesWhichCanBeMovedToHistory method: after date filter game with id = " + game.getId()))
//                .map(ServiceFactory.getInstance().getGameDTOService()::toGameDTO)
//                .sorted(Comparator.comparing(GameDTO::getDate).reversed())
//                .collect(Collectors.toList());
//    }
//
//
//    public void moveGameToHistoryAndDeleteGameRecords(int id) {
//        Game game = null;
//        try {
//            game = DaoFactory.getInstance().createGameDao().findById(id)
//                    .orElseThrow(() -> new EntityNotFoundException("Can not fond games with id: " + id));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        History history = toHistory(game);
//
//        saveToHistoryAndDeleteGameRecords(history, game); //TODO correct in spring project
//    }
//
//
//    private void saveToHistoryAndDeleteGameRecords(History history, Game game) {
//        DaoFactory.getInstance().createHistoryDao().createAndDeleteGameRecords(history, game);
//    }
//
//    //TODO refactor this method
//    private History toHistory(Game game) {
//
//        History history = new History();
//
//        history.setDate(game.getDate());
//        history.setPlayerNameUa(game.getUsers().get(0).getNameUa());//TODO improve
//        history.setPlayerNameEn(game.getUsers().get(0).getNameEn());//TODO improve
//
//        if (game.getUsers().size() > 1) { //TODO improve
//            history.setOpponentNameUa(game.getUsers().get(1).getNameUa());
//            history.setOpponentNameEn(game.getUsers().get(1).getNameEn());
//        } else {
//            //TODO correct for both languages
//            history.setOpponentNameUa(ResourceBundleUtil.getBundleString("games.game.statistics.text.audience"));
//            history.setOpponentNameEn(ResourceBundleUtil.getBundleString("games.game.statistics.text.audience"));
//        }
//
//        User firstPlayer = game.getUsers().get(0); //TODO correct
//        long firstPlayerScores = game.getQuestions()
//                .stream()
//                .filter(aq -> firstPlayer.equals(aq.getUserWhoGotPoint()))
//                .count();
//
//        long secondPlayerScores = (long) game.getQuestions()
//                .size() - firstPlayerScores;
//
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(firstPlayerScores);
//        stringBuilder.append(DELIMITER);//TODO move ":" to properties
//        stringBuilder.append(secondPlayerScores);
//        String scores = stringBuilder.toString();
//        history.setScores(scores);
//        //TODO correct for both languages
//
//        if (game.getAppeals().isEmpty()) {
//            history.setAppealStage(AppealStage.NOT_FILED.name());
//        } else {
//            history.setAppealStage(AppealStage.CONSIDERED.name());
//        }
////        game.getAppeals().stream()
////                .forEach(appeal -> {
////                    if (appeal.getAppealStage().equals(AppealStage.CONSIDERED)) {
////                        history.setAppealStage(ResourceBundleUtil.getBundleStringForAppealStage(AppealStage.CONSIDERED.name()));
////                    } else {
////                        history.setAppealStage(ResourceBundleUtil.getBundleStringForAppealStage(AppealStage.NOT_FILED.name()));
////                    }
////
////                });
////        }
//        return history;
//
//    }
//
////    //TODO refactor this method
////    @Transactional
////    public History save(History history) {
////        return historyRepository.save(history);
////    }
//
////    public Page<History> findAll(Pageable pageable) {
////        return historyRepository.findAll(pageable);
////    }
////
//
//    // TODO use GameDTO to display languages correctly for AppealStage
//    public List<History> findAll() {
//        return daoFactory.createHistoryDao().findAll();
//    }
//
////    public Page<GameDTO> getHistoryGameStatisticsByAllGamesAndPlayers(Pageable pageable) {
////        return findAll(pageable)
////                .map(this::historyToGameDTO);
////    }
//
////        public List<GameDTO> getHistoryGameStatisticsByAllGamesAndPlayers(Pageable pageable) {
////        return findAll(pageable)
////                .map(this::historyToGameDTO);
////    }
//
////    private GameDTO historyToGameDTO(History history) {
////        return GameDTO.builder()
////                .date(history.getDate())
////                .playerNameUa(history.getPlayerNameUa())
////                .playerNameEn(history.getPlayerNameEn())
////                .opponentNameUa(history.getOpponentNameUa())
////                .opponentNameEn(history.getOpponentNameEn())
////                .scores(history.getScores())
////                .appealStage(history.getAppealStage())
////                .build();
////    }
//
//
//}