package com.sports.scoreboard.service;

import com.sports.scoreboard.bean.ActionResponse;
import com.sports.scoreboard.bean.GameRequest;
import com.sports.scoreboard.exception.NoGameLiveException;
import com.sports.scoreboard.model.Game;
import com.sports.scoreboard.model.Team;
import com.sports.scoreboard.repository.GameRepository;
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
public class GameServiceTest {

    @InjectMocks
    private GameService gameService;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private TeamService teamService;

    @BeforeAll
    public static void init(){
        MockitoAnnotations.openMocks(GameServiceTest.class);
    }

    @Test
    public void test_CreateGame(){
        Mockito.when(teamService.createTeam(Mockito.anyString())).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return getTeam(invocationOnMock.getArgument(0));
            }
        });
        ActionResponse response = gameService.createGame(gameRequest()) ;
        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    public void test_fetchLiveGame() throws NoGameLiveException {
        Mockito.when(gameRepository.findGameByTeams(Mockito.anyString(),Mockito.anyString())).thenReturn(getGameList());
        Game response = gameService.fetchLiveGame("TEAM-A","TEAM-B") ;
        Assertions.assertEquals(response.getGameId(),1L);
    }

    @Test
    public void test_fetchLiveGame_NoLiveGameFound() throws NoGameLiveException {

        Assertions.assertThrows(NoGameLiveException.class,() -> {gameService.fetchLiveGame("TEAM-A","TEAM-B");});

    }



    @Test
    public void test_endTheGame() throws NoGameLiveException {
        Mockito.when(gameRepository.findGameByTeams(Mockito.anyString(),Mockito.anyString())).thenReturn(getGameList());
        ActionResponse response = gameService.endTheGame("TEAM-A","TEAM-B") ;
        Assertions.assertTrue(response.isSuccess());
    }


    @Test
    public void test_endTheGame_NoLiveGameFound() throws NoGameLiveException {

        Assertions.assertThrows(NoGameLiveException.class,() -> {gameService.endTheGame("TEAM-A","TEAM-B");});

    }

    private GameRequest gameRequest(){
        GameRequest request = new GameRequest();
        request.setGameDescription("Football Tournament");
        request.setTeamAName("TEAM-A");
        request.setTeamBName("TEAM-B");
        return request;
    }

    private List<Game> getGameList(){
        Game game = new Game();
        game.setTeamA(new Team());
        game.setGameId(1L);
        game.getTeamA().setTeamName("TEAM-A");
        game.setTeamB(new Team());
        game.getTeamB().setTeamName("TEAM-B");
        List<Game> games = new ArrayList<>();
        games.add(game);
        return games;
    }

    private Optional<Team> getTeam(String teamName){
        Team team = new Team();
        team.setTeamId(null);
        team.setTeamName(teamName);
        return Optional.of(team);
    }
}
