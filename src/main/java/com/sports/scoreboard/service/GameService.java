package com.sports.scoreboard.service;

import com.sports.scoreboard.bean.ActionResponse;
import com.sports.scoreboard.bean.GameRequest;
import com.sports.scoreboard.bean.ScoreBoard;
import com.sports.scoreboard.exception.NoGameLiveException;
import com.sports.scoreboard.model.Game;
import com.sports.scoreboard.model.GameStatus;
import com.sports.scoreboard.model.Team;
import com.sports.scoreboard.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private TeamService teamService;

    public ActionResponse createGame(GameRequest gameRequest){
        Optional<Team> teamA = teamService.fetchTeamByName(gameRequest.getTeamAName());
        Optional<Team> teamB = teamService.fetchTeamByName(gameRequest.getTeamBName());
        if(teamA.isEmpty()){
            teamA = teamService.createTeam(gameRequest.getTeamAName());
        }
        if(teamB.isEmpty()){
            teamB = teamService.createTeam(gameRequest.getTeamBName());
        }

        Game game = new Game();
        game.setDescription(gameRequest.getGameDescription());
        game.setTeamA(teamA.get());
        game.setTeamB(teamB.get());
        game.setStatus(GameStatus.LIVE);
        game = gameRepository.save(game);

        ActionResponse response = new ActionResponse();
        response.setSuccess(true);
        response.setMessage("Tournament "+gameRequest.getTeamAName()+" v/s "+gameRequest.getTeamBName() +" is LIVE now ");
        return response;
    }

    public Game fetchLiveGame(String teamAName, String teamBName) throws NoGameLiveException {

        List<Game> gameList = gameRepository.findGameByTeams(teamAName , teamBName);
        if(gameList.isEmpty()){
              
              throw  new NoGameLiveException("No Game is currently played between "+teamAName + " and "+teamBName);
        }

        return gameList.get(0);

    }

    public ActionResponse endTheGame(String teamAName, String teamBName) throws NoGameLiveException {
        List<Game> gameList = gameRepository.findGameByTeams(teamAName , teamBName);
        if(gameList.isEmpty()){

            throw  new NoGameLiveException("No Game is currently played between "+teamAName + " and "+teamBName);
        }

         Game game = gameList.get(0);
         game.setStatus(GameStatus.COMPLETED);
         gameRepository.save(game);
         ActionResponse response = new ActionResponse();
         response.setSuccess(true);
         response.setMessage(game.getDescription() + " "+teamAName+" v/s "+ teamBName+" COMPLETED");
         return  response;
    }
}
