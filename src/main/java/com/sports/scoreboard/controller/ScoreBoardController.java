package com.sports.scoreboard.controller;

import com.sports.scoreboard.bean.ScoreBoard;
import com.sports.scoreboard.exception.NoGameLiveException;
import com.sports.scoreboard.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/score")
public class ScoreBoardController {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/update")
    public ScoreBoard updateScoreBoard(@RequestBody ScoreBoard scoreBoard) throws NoGameLiveException {
        ScoreBoard response = scoreService.updateScore(scoreBoard);
        simpMessagingTemplate.convertAndSend("/topic/score",response);
        return response;
    }

}
