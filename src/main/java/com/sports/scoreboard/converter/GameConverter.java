package com.sports.scoreboard.converter;

import com.sports.scoreboard.bean.GameRequest;
import com.sports.scoreboard.model.Game;
import com.sports.scoreboard.model.GameStatus;

public class GameConverter {

    public Game convertBeanToModel(GameRequest  gameRequest){
        Game game = new Game();
        game.setStatus(GameStatus.LIVE);
        game.setDescription(gameRequest.getGameDescription());
        return game;
    }

}
