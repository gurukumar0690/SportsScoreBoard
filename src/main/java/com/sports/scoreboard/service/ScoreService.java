package com.sports.scoreboard.service;

import com.sports.scoreboard.bean.ScoreBoard;
import com.sports.scoreboard.exception.NoGameLiveException;
import com.sports.scoreboard.model.Game;
import com.sports.scoreboard.model.Score;
import com.sports.scoreboard.model.Team;
import com.sports.scoreboard.repository.ScoreRepository;
import com.sports.scoreboard.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScoreService {

    @Autowired
    private TeamService teamService;

    @Autowired
    private GameService gameService;

    @Autowired
    private ScoreRepository scoreRepository;


    public ScoreBoard updateScore(ScoreBoard scoreBoard) throws NoGameLiveException {
        Optional<Team> teamA = teamService.fetchTeamByName(scoreBoard.getTeamAName());
        Optional<Team> teamB = teamService.fetchTeamByName(scoreBoard.getTeamBName());

        Game game = gameService.fetchLiveGame(scoreBoard.getTeamAName(), scoreBoard.getTeamBName());
        scoreBoard.setGameDescription(game.getDescription());
        Score scoreExample =  new Score();
        scoreExample.setGame(game);
        List<Score> currentScore = scoreRepository.findAll(Example.of(scoreExample));
        List<Score> scores = new ArrayList<>();
        if(!currentScore.isEmpty()){
                scores = currentScore.stream().map(s->{
                s.setScore(scoreBoard.getTeamBName().equalsIgnoreCase(s.getTeam().getTeamName())?scoreBoard.getTeamAScore():scoreBoard.getTeamBScore());
                return s;
            }).collect(Collectors.toList());

        }else{
            Score teamAScore = new Score();
            teamAScore.setTeam(teamA.get());
            teamAScore.setScore(scoreBoard.getTeamAScore());

            Score teamBScore = new Score();
            teamBScore.setTeam(teamB.get());
            teamBScore.setScore(scoreBoard.getTeamBScore());

            scores.add(teamAScore);
            scores.add(teamBScore);
        }

        scoreRepository.saveAll(scores);

        return scoreBoard;
    }

}
