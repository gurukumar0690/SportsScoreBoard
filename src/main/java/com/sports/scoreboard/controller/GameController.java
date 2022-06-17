package com.sports.scoreboard.controller;

import com.sports.scoreboard.bean.ActionResponse;
import com.sports.scoreboard.bean.GameRequest;
import com.sports.scoreboard.exception.NoGameLiveException;
import com.sports.scoreboard.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("/create")
    public ActionResponse createNewGame(@RequestBody GameRequest gameRequest){
        return gameService.createGame(gameRequest);
    }

    @PostMapping("/end")
    public ActionResponse endTheGame(@RequestBody GameRequest gameRequest) throws NoGameLiveException {
        return gameService.endTheGame(gameRequest.getTeamAName(),gameRequest.getTeamBName());
    }




}
