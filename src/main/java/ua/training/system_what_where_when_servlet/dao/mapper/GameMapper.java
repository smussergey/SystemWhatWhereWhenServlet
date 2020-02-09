package ua.training.system_what_where_when_servlet.dao.mapper;

import org.apache.log4j.Logger;
import ua.training.system_what_where_when_servlet.entity.Game;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class GameMapper implements ObjectMapper<Game> {
    private static final Logger LOGGER = Logger.getLogger(GameMapper.class);

    @Override
    public Game extractFromResultSet(ResultSet rs) throws SQLException {
        Game game = new Game();
        game.setId(rs.getInt("game.game_id"));
        LOGGER.info("GameMapper class, extractFromResultSet method: game date: " + rs.getDate("game.date"));
        game.setDate(rs.getDate("game.date").toLocalDate());

        return game;
    }

    @Override
    public Game makeUnique(Map<Integer, Game> cache,
                           Game game) {
        cache.putIfAbsent(game.getId(), game);

        LOGGER.info("GameMapper class, makeUnique method: were found games: " + cache.size());
        return cache.get(game.getId());
    }
}
