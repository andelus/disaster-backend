package com.example.backend_disaster_project.disasterbackend.entities;

public class TimerSocketMessage {

    private long timerTimeInSeconds;

    public TimerSocketMessage(){
        this.timerTimeInSeconds = 0;
    }
    public TimerSocketMessage(long time){
        this.timerTimeInSeconds = time;
    }
    public long getTimerTimeInSeconds(){
        return this.timerTimeInSeconds;
    }
    public void setTimerTimeInSeconds(long time){
        this.timerTimeInSeconds = time;
    }
}