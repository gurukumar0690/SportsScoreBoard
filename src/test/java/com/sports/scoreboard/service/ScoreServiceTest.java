package com.sports.scoreboard.service;

import com.sports.scoreboard.bean.ScoreBoard;
import com.sports.scoreboard.exception.NoGameLiveException;
import com.sports.scoreboard.model.Game;
import com.sports.scoreboard.model.GameStatus;
import com.sports.scoreboard.model.Score;
import com.sports.scoreboard.model.Team;
import com.sports.scoreboard.repository.ScoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ScoreServiceTest {

    @InjectMocks
    private ScoreService scoreService;

    @Mock
    private TeamService teamService;

    @Mock
    private GameService gameService;

    @Mock
    private ScoreRepository scoreRepository;


    @BeforeAll
    public static void init(){
        MockitoAnnotations.openMocks(ScoreServiceTest.class);
    }

    @Test
    public void test_updateScore() throws NoGameLiveException {
        Mockito.when(gameService.fetchLiveGame(Mockito.anyString(),Mockito.anyString())).thenReturn(getGame());
        Mockito.when(scoreRepository.findAll(Mockito.any(org.springframework.data.domain.Example.class))).thenReturn(getScores());
        ScoreBoard request = getScoreBoard();
        ScoreBoard response = scoreService.updateScore(request);
        Assertions.assertEquals(response.getTeamAName(),request.getTeamAName());
        Assertions.assertEquals(response.getTeamBName(),request.getTeamBName());
    }

    @Test
    public void test_updateScore_newScoreBoard() throws NoGameLiveException {
        Mockito.when(teamService.fetchTeamByName(Mockito.anyString())).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return getTeam(invocationOnMock.getArgument(0));
            }
        });
        Mockito.when(gameService.fetchLiveGame(Mockito.anyString(),Mockito.anyString())).thenReturn(getGame());
        Mockito.when(scoreRepository.findAll(Mockito.any(org.springframework.data.domain.Example.class))).thenReturn(new ArrayList());
        ScoreBoard request = getScoreBoard();
        ScoreBoard response = scoreService.updateScore(request);
        Assertions.assertEquals(response.getTeamAName(),request.getTeamAName());
        Assertions.assertEquals(response.getTeamBName(),request.getTeamBName());
    }

    private List<Score> getScores(){
        List<Score> scores = new ArrayList<>();
        Score score = new Score();
        score.setTeam(new Team());
        score.getTeam().setTeamName("TEAM-A");
        score.setScore(1);
        scores.add(score);

        score = new Score();
        score.setTeam(new Team());
        score.getTeam().setTeamName("TEAM-B");
        score.setScore(2);
        scores.add(score);
        return  scores;
    }
    private Game getGame(){
        Game game = new Game();
        game.setGameId(1L);
        game.setTeamA(new Team());
        game.setTeamB(new Team());
        game.getTeamA().setTeamName("TEAM-A");
        game.getTeamB().setTeamName("TEAM-B");
        game.setStatus(GameStatus.LIVE);
        return game;
    }
    private ScoreBoard getScoreBoard(){
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.setTeamAScore(1);
        scoreBoard.setTeamAName("TEAM-A");
        scoreBoard.setTeamBName("TEAM-B");
        scoreBoard.setTeamBScore(0);
        return scoreBoard;
    }
    private Optional<Team> getTeam(String teamName){
        Team team = new Team();
        team.setTeamId(null);
        team.setTeamName(teamName);
        return Optional.of(team);
    }

}
