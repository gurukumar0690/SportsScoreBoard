package com.sports.scoreboard.repository;

import com.sports.scoreboard.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game,Long> {

    @Query("select g from Game g inner join g.teamA tA inner join g.teamB tB where (tA.teamName=:teamA or tB.teamName=:teamA) and (tA.teamName=:teamB or tB.teamName=:teamB) and g.status = 'LIVE'")
    List<Game> findGameByTeams(@Param("teamA") String teamA, @Param("teamB") String teamB);
}
