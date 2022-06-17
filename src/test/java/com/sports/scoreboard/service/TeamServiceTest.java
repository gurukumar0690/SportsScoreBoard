package com.sports.scoreboard.service;


import com.sports.scoreboard.model.Team;
import com.sports.scoreboard.repository.TeamRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class TeamServiceTest {

    @InjectMocks
    private TeamService teamService;

    @Mock
    private TeamRepository teamRepository;

    @BeforeAll
    public static void init(){
        MockitoAnnotations.openMocks(TeamServiceTest.class);
    }

    @Test
    public void test_fetchTeamByName(){
         String teamName = "TEAM-A";
         Mockito.when(teamRepository.findOne(Mockito.any(org.springframework.data.domain.Example.class))).thenReturn(getTeam(teamName));
         Optional<Team> team = teamService.fetchTeamByName(teamName);
        Assertions.assertTrue(team.isPresent());
        Assertions.assertEquals(team.get().getTeamName(),teamName);
    }

    @Test
    public void test_createTeam(){
        String teamName = "TEAM-A";
        Mockito.when(teamRepository.save(Mockito.any(Team.class))).thenReturn(getTeam(teamName).get());
        Optional<Team> team = teamService.createTeam(teamName);
        Assertions.assertTrue(team.isPresent());
        Assertions.assertEquals(team.get().getTeamName(),teamName);
    }

    private Optional<Team> getTeam(String teamName){
        Team team = new Team();
        team.setTeamId(null);
        team.setTeamName(teamName);
        return Optional.of(team);
    }

}
