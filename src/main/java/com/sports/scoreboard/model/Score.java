package com.sports.scoreboard.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "SCORE")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scoreId;

    @Column(name = "SCORE_DATE_TIME")
    private LocalDateTime scoreDateTime;

    @ManyToOne
    private Game game;

    @OneToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @Column
    private Integer score;

    public Long getScoreId() {
        return scoreId;
    }

    public void setScoreId(Long scoreId) {
        this.scoreId = scoreId;
    }

    public LocalDateTime getScoreDateTime() {
        return scoreDateTime;
    }

    public void setScoreDateTime(LocalDateTime scoreDateTime) {
        this.scoreDateTime = scoreDateTime;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
