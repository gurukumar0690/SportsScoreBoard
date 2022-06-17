package com.sports.scoreboard.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "GAME_DETAILS")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameId;

    @Column
    private String description;

    @OneToOne
    @JoinColumn(name = "team_a")
    private Team teamA;

    @OneToOne
    @JoinColumn(name = "team_b")
    private Team teamB;

    @OneToMany(mappedBy = "game")
    private List<Score> scores;

    @Column
    @Enumerated(EnumType.STRING)
    private GameStatus status;

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Team getTeamA() {
        return teamA;
    }

    public void setTeamA(Team teamA) {
        this.teamA = teamA;
    }

    public Team getTeamB() {
        return teamB;
    }

    public void setTeamB(Team teamB) {
        this.teamB = teamB;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
