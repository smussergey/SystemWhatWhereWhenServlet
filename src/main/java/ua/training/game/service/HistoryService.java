package ua.training.game.service;

import org.apache.log4j.Logger;
import ua.training.game.dao.DaoFactory;
import ua.training.game.domain.Game;
import ua.training.game.domain.History;
import ua.training.game.util.ResourceBundleUtil;
import ua.training.game.web.dto.GameDTO;
import ua.training.game.web.pagination.Page;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HistoryService {
    private static final Logger LOGGER = Logger.getLogger(HistoryService.class);
    private final GameService gameService = new GameService();
    private DaoFactory daoFactory = DaoFactory.getInstance();


//
//    public HistoryService(HistoryRepository historyRepository, GameService gameService) {
//        this.historyRepository = historyRepository;
//        this.gameService = gameService;
//    }

    //TODO improve query to DB
    //TODO improve to get games when appeal was not filed and 1 (or 10) date passed
    public List<GameDTO> getGamesWhichCanBeMovedToHistory() {
        return gameService.findAllByDateBefore(LocalDate.now()).stream()
                .map(gameService::toGameDTO)
                .sorted(Comparator.comparing(GameDTO::getDate).reversed())
                .collect(Collectors.toList());
    }


//    public boolean moveGameToHistory(Long id) {
//        Game game = gameService.findById(id);
//        History history = toHistory(game);
//
//        return saveToHistoryAndDeleteGameRecord(history, id);
//    }


    public void moveGameToHistory(Integer id) {
        Game game = gameService.findById(id);
        History history = toHistory(game);
        saveToHistoryAndDeleteGameRecord(history, game);
    }


    private History toHistory(Game game) {
        History history = new History();
        history.setDate(game.getDate());
        history.setFirstPlayerNameUa(game.getFirstPlayer().getNameUa());
        history.setFirstPlayerNameEn(game.getFirstPlayer().getNameEn());
        history.setSecondPlayerNameUa(game.getSecondPlayer().getNameUa());
        history.setSecondPlayerNameEn(game.getSecondPlayer().getNameEn());
        history.setScores(gameService.createScoresResultsForGameDTO(game));
        history.setAppealStage(gameService.createAppealStageForGameDTO(game).name());
        return history;
    }

//    @Transactional
//    public boolean saveToHistoryAndDeleteGameRecord(History history, Long id) {
//        try {
//            save(history);
//            gameService.deleteGameById(id);
//            return true;
//        } catch (Exception ex) {
//            //TODO implement if needed
//        }
//        return false;
//    }


//    @Transactional
//    public History save(History history) {
//        return historyRepository.save(history);
//    }


    public void saveToHistoryAndDeleteGameRecord(History history, Game game) {
        daoFactory.createHistoryDao().createAndDeleteGameRecord(history, game);
    }
//
//    public Page<History> findAll(Pageable pageable) {
//        return historyRepository.findAll(pageable);
//    }

    public List<History> findAll() {
        return daoFactory.createHistoryDao().findAll();
    }


//
//    public Page<GameDTO> getHistoryGameStatisticsByAllGamesAndPlayers(Pageable pageable) {
//        return findAll(pageable)
//                .map(this::historyToGameDTO);
//    }


//    public List<GameDTO> getHistoryGameStatisticsByAllGamesAndPlayers() {
//        return findAll().stream()
//                .map(this::historyToGameDTO)
//                .sorted(Comparator.comparing(GameDTO::getDate).reversed())
//                .collect(Collectors.toList());
//    }

    public Page<GameDTO> getHistoryGameStatisticsByAllGamesAndPlayers(int currentPage, int recordsPerPage) {
        Page<History> historyPage = daoFactory.createHistoryDao()
                .getHistoryGameStatisticsByAllGamesAndPlayers(currentPage, recordsPerPage);

        List<GameDTO> gameDTOs = historyPage.getEntries()
                .stream()
                .map(this::historyToGameDTO)
                .sorted(Comparator.comparing(GameDTO::getDate).reversed())
                .collect(Collectors.toList());

        Page<GameDTO> gameDTOPage = new Page<>();
        gameDTOPage.setPageSize(historyPage.getPageSize());
        gameDTOPage.setNumberOfEntries(historyPage.getNumberOfEntries());
        gameDTOPage.setCurrentPage(historyPage.getCurrentPage());
        gameDTOPage.setEntries(gameDTOs);
        int numberOfPages = calculateNumberOfPages(historyPage.getNumberOfEntries(),
                historyPage.getPageSize());

        gameDTOPage.setNumberOfPages(numberOfPages);
        return gameDTOPage;
    }

    private int calculateNumberOfPages(int numberOfEntries, int pageSize) {
        int numberOfPages = numberOfEntries / pageSize;
        if (numberOfEntries % pageSize > 0) {
            numberOfPages++;
        }
        return numberOfPages;

    }


    private GameDTO historyToGameDTO(History history) {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setDate((history.getDate()));//TODO LOCALE
        gameDTO.setFirstPlayerNameUa(history.getFirstPlayerNameUa());
        gameDTO.setFirstPlayerNameEn(history.getFirstPlayerNameEn());
        gameDTO.setSecondPlayerNameUa(history.getSecondPlayerNameUa());
        gameDTO.setSecondPlayerNameEn(history.getSecondPlayerNameEn());
        gameDTO.setScores(history.getScores());
        gameDTO.setAppealStage(ResourceBundleUtil.getBundleStringForAppealStage(history.getAppealStage()));
        return gameDTO;
    }


}