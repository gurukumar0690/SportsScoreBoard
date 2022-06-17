package com.sports.scoreboard.exception;

public class NoGameLiveException extends Exception{

    private String message;

    public NoGameLiveException(String message){
        this.message = message;
    }


}
