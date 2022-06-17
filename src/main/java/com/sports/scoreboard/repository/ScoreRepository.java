package com.sports.scoreboard.repository;

import com.sports.scoreboard.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score,Long> {
}
