package com.sports.scoreboard.service;

import com.sports.scoreboard.model.Team;
import com.sports.scoreboard.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public Optional<Team> fetchTeamByName(String teamName){
        Team teamExample = new Team();
        teamExample.setTeamName(teamName);
        return teamRepository.findOne(Example.of(teamExample));
    }

    public Optional<Team> createTeam(String teamName){
        Team newTeam = new Team();
        newTeam.setTeamName(teamName);
        return Optional.of(teamRepository.save(newTeam));
    }
}
